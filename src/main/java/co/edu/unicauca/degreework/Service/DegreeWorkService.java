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

@Service
public class DegreeWorkService {

    private final DegreeWorkRepository degreeWorkRepository;
    private final DegreeWorkStateFactory stateFactory;
    private final EventManager eventManager;
    private final NewDegreeWorkListener newDegreeWorkListener;

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

    // Suscribir listeners después de la construcción
    @PostConstruct
    public void init() {
        eventManager.subscribe("DEGREE_WORK_CREATED", newDegreeWorkListener);
    }

    @Transactional
    public DegreeWork createDegreeWork(CreateDegreeWorkDTO dto) {
        // Validar que ningún estudiante tenga un trabajo de grado activo
        validateStudentsAvailability(dto.getStudentIds());

        DegreeWork degreeWork = new DegreeWork();

        // Datos del DTO
        degreeWork.setTitle(dto.getTitle());
        degreeWork.setDescription(dto.getDescription());
        degreeWork.setIdDirector(dto.getIdDirector());
        degreeWork.setIdCoordinator(dto.getIdCoordinator());
        degreeWork.setStudentIds(dto.getStudentIds());
        degreeWork.setModality(dto.getModality());

        // Valores por defecto
        degreeWork.setStatus(Status.CREATED);
        degreeWork.setProcess(Process.FORMATO_A);

        DegreeWork savedDegreeWork = degreeWorkRepository.save(degreeWork);

        // Notificar a todos los listeners suscritos al evento
        eventManager.notify("DEGREE_WORK_CREATED", savedDegreeWork);

        return savedDegreeWork;

    }
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

    public DegreeWork getDegreeWorkById(Long id) {
        DegreeWork degreeWork = degreeWorkRepository.findDegreeWorkById(id);
        //DegreeWork degreeWork = new DegreeWork();

        DegreeWorkState state = stateFactory.createState(degreeWork);
        degreeWork.setState(state);

        return degreeWork;
    }

    @Transactional
    public DegreeWork uploadFormatA(Long id) {
        DegreeWork degreeWork = getDegreeWorkById(id);
        degreeWork.getState().uploadFormatA();
        return degreeWorkRepository.save(degreeWork);
    }

    @Transactional
    public DegreeWork rejectFormatA(Long id) {
        DegreeWork degreeWork = getDegreeWorkById(id);
        degreeWork.getState().formatARejected();
        return degreeWorkRepository.save(degreeWork);
    }

    @Transactional
    public DegreeWork acceptFormatA(Long id) {
        DegreeWork degreeWork = getDegreeWorkById(id);
        degreeWork.getState().formatAAccepted();
        return degreeWorkRepository.save(degreeWork);
    }

    @Transactional
    public DegreeWork uploadDraft(Long id) {
        DegreeWork degreeWork = getDegreeWorkById(id);
        degreeWork.getState().uploadDraft();
        return degreeWorkRepository.save(degreeWork);
    }

    @Transactional
    public DegreeWork expireDraftTime(Long id) {
        DegreeWork degreeWork = getDegreeWorkById(id);
        degreeWork.getState().draftTimeExpired();
        return degreeWorkRepository.save(degreeWork);
    }

    @Transactional
    public DegreeWork aproveDraft(Long id){
        DegreeWork degreeWork = getDegreeWorkById(id);
        degreeWork.getState().draftAproved();
        return degreeWorkRepository.save(degreeWork);
    }

    public List<ResponseDTO> getDegreeWorksByDirector(Long directorId) {
        return degreeWorkRepository.findByDirectorId(directorId);
    }

    public List<ResponseDTO> getDegreeWorksByCoordinator(Long coordinatorId) {
        return degreeWorkRepository.findByCoordinatorId(coordinatorId);
    }
}