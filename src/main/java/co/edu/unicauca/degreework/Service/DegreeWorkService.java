package co.edu.unicauca.degreework.Service;

import co.edu.unicauca.degreework.DTO.CreateDegreeWorkDTO;
import co.edu.unicauca.degreework.DTO.DegreeWorkResponseDTO;
import co.edu.unicauca.degreework.Enum.Process;
import co.edu.unicauca.degreework.Enum.Status;
import co.edu.unicauca.degreework.Exception.StudentAlreadyHasDegreeWorkException;
import co.edu.unicauca.degreework.Factory.DegreeWorkStateFactory;
import co.edu.unicauca.degreework.Model.DegreeWork;
import co.edu.unicauca.degreework.Repository.DegreeWorkRepository;
import co.edu.unicauca.degreework.States.DegreeWorkState;
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

    @Autowired
    public DegreeWorkService(DegreeWorkRepository degreeWorkRepository,
                             DegreeWorkStateFactory stateFactory) {
        this.degreeWorkRepository = degreeWorkRepository;
        this.stateFactory = stateFactory;
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
        degreeWork.setIdCoordinator(dto.getIdCoordinator());  // ← NUEVO
        degreeWork.setStudentIds(dto.getStudentIds());
        degreeWork.setModality(dto.getModality());

        // Valores por defecto
        degreeWork.setStatus(Status.CREATED);
        degreeWork.setProcess(Process.FORMAT_A);

        return degreeWorkRepository.save(degreeWork);
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
        dto.setProcess(entity.getProcess());
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
        DegreeWork degreeWork = degreeWorkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DegreeWork no encontrado con id: " + id));

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
}