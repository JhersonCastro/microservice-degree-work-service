package co.edu.unicauca.degreework.Service;

import co.edu.unicauca.degreework.DTO.CreateDegreeWorkDTO;
import co.edu.unicauca.degreework.DTO.DegreeWorkResponseDTO;
import co.edu.unicauca.degreework.DTO.ResponseDTO;
import co.edu.unicauca.degreework.Enum.Process;
import co.edu.unicauca.degreework.Enum.Status;
import co.edu.unicauca.degreework.Exception.StudentAlreadyHasDegreeWorkException;
import co.edu.unicauca.degreework.Factory.DegreeWorkStateFactory;
import co.edu.unicauca.degreework.Model.DegreeWork;
import co.edu.unicauca.degreework.Observer.EventManager;
import co.edu.unicauca.degreework.Observer.NewDegreeWorkListener;
import co.edu.unicauca.degreework.Repository.DegreeWorkRepository;
import co.edu.unicauca.degreework.States.DegreeWorkState;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Service class that manages the lifecycle and business logic of {@link DegreeWork} entities.
 * Handles creation, validation, state transitions, and event notifications.
 */
@Service
public class DegreeWorkService {

    private final DegreeWorkRepository degreeWorkRepository;
    private final DegreeWorkStateFactory stateFactory;
    private final EventManager eventManager;
    private final NewDegreeWorkListener newDegreeWorkListener;

    /**
     * Constructs the service with required dependencies.
     *
     * @param degreeWorkRepository repository for {@link DegreeWork} persistence
     * @param stateFactory         factory to create degree work state instances
     * @param eventManager         event manager for notifications
     * @param newDegreeWorkListener listener for creation events
     */
    @Autowired
    public DegreeWorkService(DegreeWorkRepository degreeWorkRepository,
                             DegreeWorkStateFactory stateFactory,
                             EventManager eventManager,
                             NewDegreeWorkListener newDegreeWorkListener) {
        this.degreeWorkRepository = degreeWorkRepository;
        this.stateFactory = stateFactory;
        this.eventManager = eventManager;
        this.newDegreeWorkListener = newDegreeWorkListener;
    }

    /**
     * Subscribes listeners after service initialization.
     */
    @PostConstruct
    public void init() {
        eventManager.subscribe("DEGREE_WORK_CREATED", newDegreeWorkListener);
    }

    /**
     * Creates a new {@link DegreeWork} entity.
     * Validates that students do not already have an active project and triggers a creation event.
     *
     * @param dto data transfer object containing the degree work details
     * @return the saved {@link DegreeWork}
     */
    @Transactional
    public DegreeWork createDegreeWork(CreateDegreeWorkDTO dto) {
        validateStudentsAvailability(dto.getStudentIds());

        DegreeWork degreeWork = new DegreeWork();
        degreeWork.setTitle(dto.getTitle());
        degreeWork.setDescription(dto.getDescription());
        degreeWork.setIdDirector(dto.getIdDirector());
        degreeWork.setIdCoordinator(dto.getIdCoordinator());
        degreeWork.setStudentIds(dto.getStudentIds());
        degreeWork.setModality(dto.getModality());
        degreeWork.setStatus(Status.CREATED);
        degreeWork.setProcess(Process.FORMATO_A);

        DegreeWork savedDegreeWork = degreeWorkRepository.save(degreeWork);
        eventManager.notify("DEGREE_WORK_CREATED", savedDegreeWork);

        return savedDegreeWork;
    }

    /**
     * Converts a {@link DegreeWork} entity into a {@link DegreeWorkResponseDTO}.
     *
     * @param entity the degree work entity
     * @return a response DTO with degree work details
     */
    public DegreeWorkResponseDTO toResponseDTO(DegreeWork entity) {
        DegreeWorkResponseDTO dto = new DegreeWorkResponseDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setIdDirector(entity.getIdDirector());
        dto.setIdCoordinator(entity.getIdCoordinator());
        dto.setStudentIds(entity.getStudentIds());
        dto.setStatus(entity.getStatus());
        dto.setModality(entity.getModality());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    /**
     * Validates that the provided students are not already assigned to active degree works.
     *
     * @param studentIds set of student IDs to check
     * @throws StudentAlreadyHasDegreeWorkException if a student already has an active degree work
     */
    private void validateStudentsAvailability(Set<Long> studentIds) {
        if (studentIds == null || studentIds.isEmpty()) {
            return;
        }

        List<DegreeWork> activeDegreeWorks = degreeWorkRepository
                .findByStudentIdsAndStatusNot(new ArrayList<>(studentIds), Status.INACTIVE);

        if (!activeDegreeWorks.isEmpty()) {
            for (DegreeWork dw : activeDegreeWorks) {
                for (Long studentId : studentIds) {
                    if (dw.getStudentIds().contains(studentId)) {
                        throw new StudentAlreadyHasDegreeWorkException(
                                studentId,
                                "El estudiante con ID " + studentId +
                                        " ya tiene un trabajo de grado activo (ID: " + dw.getId() +
                                        ", Estado: " + dw.getStatus() + ")"
                        );
                    }
                }
            }
        }
    }

    /**
     * Retrieves a {@link DegreeWork} by its ID and assigns the correct state.
     *
     * @param id degree work ID
     * @return the found {@link DegreeWork}
     */
    public DegreeWork getDegreeWorkById(Long id) {
        DegreeWork degreeWork = degreeWorkRepository.findDegreeWorkById(id);
        DegreeWorkState state = stateFactory.createState(degreeWork);
        degreeWork.setState(state);
        return degreeWork;
    }

    /**
     * Uploads Format A and updates the degree work's state.
     *
     * @param id degree work ID
     * @return updated {@link DegreeWork}
     */
    @Transactional
    public DegreeWork uploadFormatA(Long id) {
        DegreeWork degreeWork = getDegreeWorkById(id);
        degreeWork.getState().uploadFormatA();
        return degreeWorkRepository.save(degreeWork);
    }

    /**
     * Marks Format A as rejected.
     *
     * @param id degree work ID
     * @return updated {@link DegreeWork}
     */
    @Transactional
    public DegreeWork rejectFormatA(Long id) {
        DegreeWork degreeWork = getDegreeWorkById(id);
        degreeWork.getState().formatARejected();
        return degreeWorkRepository.save(degreeWork);
    }

    /**
     * Marks Format A as accepted.
     *
     * @param id degree work ID
     * @return updated {@link DegreeWork}
     */
    @Transactional
    public DegreeWork acceptFormatA(Long id) {
        DegreeWork degreeWork = getDegreeWorkById(id);
        degreeWork.getState().formatAAccepted();
        return degreeWorkRepository.save(degreeWork);
    }

    /**
     * Uploads the draft version of the degree work.
     *
     * @param id degree work ID
     * @return updated {@link DegreeWork}
     */
    @Transactional
    public DegreeWork uploadDraft(Long id) {
        DegreeWork degreeWork = getDegreeWorkById(id);
        degreeWork.getState().uploadDraft();
        return degreeWorkRepository.save(degreeWork);
    }

    /**
     * Expires the draft submission time for the given degree work.
     *
     * @param id degree work ID
     * @return updated {@link DegreeWork}
     */
    @Transactional
    public DegreeWork expireDraftTime(Long id) {
        DegreeWork degreeWork = getDegreeWorkById(id);
        degreeWork.getState().draftTimeExpired();
        return degreeWorkRepository.save(degreeWork);
    }

    /**
     * Approves the draft version of the degree work.
     *
     * @param id degree work ID
     * @return updated {@link DegreeWork}
     */
    @Transactional
    public DegreeWork aproveDraft(Long id) {
        DegreeWork degreeWork = getDegreeWorkById(id);
        degreeWork.getState().draftAproved();
        return degreeWorkRepository.save(degreeWork);
    }

    /**
     * Retrieves all degree works associated with a specific director.
     *
     * @param directorId director's ID
     * @return list of {@link ResponseDTO} objects
     */
    public List<ResponseDTO> getDegreeWorksByDirector(Long directorId) {
        return degreeWorkRepository.findByDirectorId(directorId);
    }

    /**
     * Retrieves all degree works associated with a specific coordinator.
     *
     * @param coordinatorId coordinator's ID
     * @return list of {@link ResponseDTO} objects
     */
    public List<ResponseDTO> getDegreeWorksByCoordinator(Long coordinatorId) {
        return degreeWorkRepository.findByCoordinatorId(coordinatorId);
    }
}
