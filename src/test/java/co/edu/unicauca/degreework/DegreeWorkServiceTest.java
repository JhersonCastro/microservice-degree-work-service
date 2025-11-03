package co.edu.unicauca.degreework;

import co.edu.unicauca.degreework.DTO.CreateDegreeWorkDTO;
import co.edu.unicauca.degreework.Enum.Modality;
import co.edu.unicauca.degreework.Enum.Process;
import co.edu.unicauca.degreework.Enum.Status;
import co.edu.unicauca.degreework.Exception.StudentAlreadyHasDegreeWorkException;
import co.edu.unicauca.degreework.Factory.DegreeWorkStateFactory;
import co.edu.unicauca.degreework.Model.DegreeWork;
import co.edu.unicauca.degreework.Repository.DegreeWorkRepository;
import co.edu.unicauca.degreework.Service.DegreeWorkService;
import co.edu.unicauca.degreework.States.DegreeWorkCreated;
import co.edu.unicauca.degreework.States.DegreeWorkFormatA;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("DegreeWork Service Tests")
class DegreeWorkServiceTest {

    @Mock
    private DegreeWorkRepository degreeWorkRepository;

    @Mock
    private DegreeWorkStateFactory stateFactory;

    @InjectMocks
    private DegreeWorkService degreeWorkService;

    private CreateDegreeWorkDTO validDTO;
    private DegreeWork mockDegreeWork;

    @BeforeEach
    void setUp() {
        Set<Long> studentIds = new HashSet<>(Arrays.asList(1001L, 1002L));

        validDTO = new CreateDegreeWorkDTO(
                "Sistema de gestión de proyectos",
                "Plataforma web para gestionar trabajos de grado",
                12345L,
                67890L,  // ← NUEVO idCoordinator
                studentIds,
                Modality.INVESTIGACION
        );

        mockDegreeWork = new DegreeWork(
                1L,
                "Sistema de gestión de proyectos",
                "Plataforma web para gestionar trabajos de grado",
                12345L,
                67890L,  // ← NUEVO idCoordinator
                studentIds,
                Status.CREATED,
                Modality.INVESTIGACION,
                LocalDateTime.now(),
                Process.FORMAT_A
        );
    }

    @Test
    @DisplayName("Crear DegreeWork exitosamente cuando estudiantes no tienen trabajos activos")
    void testCreateDegreeWork_Success() {
        // Arrange
        when(degreeWorkRepository.findByStudentIdsAndStatusNot(anyList(), eq(Status.INACTIVE)))
                .thenReturn(Collections.emptyList());
        when(degreeWorkRepository.save(any(DegreeWork.class)))
                .thenReturn(mockDegreeWork);

        // Act
        DegreeWork result = degreeWorkService.createDegreeWork(validDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Sistema de gestión de proyectos", result.getTitle());
        assertEquals(Status.CREATED, result.getStatus());
        assertEquals(Process.FORMAT_A, result.getProcess());
        assertEquals(2, result.getStudentIds().size());

        verify(degreeWorkRepository, times(1))
                .findByStudentIdsAndStatusNot(anyList(), eq(Status.INACTIVE));
        verify(degreeWorkRepository, times(1)).save(any(DegreeWork.class));
    }

    @Test
    @DisplayName("Lanzar excepción cuando estudiante ya tiene un trabajo de grado activo")
    void testCreateDegreeWork_StudentAlreadyHasActiveDegreeWork() {
        // Arrange
        DegreeWork existingDegreeWork = new DegreeWork(
                2L,
                "Trabajo existente",
                "Descripción",
                12345L,
                67890L,
                new HashSet<>(Collections.singletonList(1001L)),
                Status.FORMAT_A,
                Modality.INVESTIGACION,
                LocalDateTime.now(),
                Process.FORMAT_A
        );

        when(degreeWorkRepository.findByStudentIdsAndStatusNot(anyList(), eq(Status.INACTIVE)))
                .thenReturn(Collections.singletonList(existingDegreeWork));

        // Act & Assert
        StudentAlreadyHasDegreeWorkException exception = assertThrows(
                StudentAlreadyHasDegreeWorkException.class,
                () -> degreeWorkService.createDegreeWork(validDTO)
        );

        assertTrue(exception.getMessage().contains("1001"));
        assertEquals(1001L, exception.getStudentId());

        verify(degreeWorkRepository, times(1))
                .findByStudentIdsAndStatusNot(anyList(), eq(Status.INACTIVE));
        verify(degreeWorkRepository, never()).save(any(DegreeWork.class));
    }

    @Test
    @DisplayName("Permitir crear DegreeWork cuando estudiante tiene uno INACTIVO")
    void testCreateDegreeWork_StudentHasInactiveDegreeWork() {
        // Arrange
        DegreeWork inactiveDegreeWork = new DegreeWork(
                2L,
                "Trabajo inactivo",
                "Descripción",
                12345L,
                67890L,
                new HashSet<>(Collections.singletonList(1001L)),
                Status.INACTIVE,
                Modality.INVESTIGACION,
                LocalDateTime.now(),
                Process.FORMAT_A
        );

        // Solo devolvemos trabajos NO inactivos, por lo que esta lista está vacía
        when(degreeWorkRepository.findByStudentIdsAndStatusNot(anyList(), eq(Status.INACTIVE)))
                .thenReturn(Collections.emptyList());
        when(degreeWorkRepository.save(any(DegreeWork.class)))
                .thenReturn(mockDegreeWork);

        // Act
        DegreeWork result = degreeWorkService.createDegreeWork(validDTO);

        // Assert
        assertNotNull(result);
        assertEquals(Status.CREATED, result.getStatus());

        verify(degreeWorkRepository, times(1)).save(any(DegreeWork.class));
    }

    @Test
    @DisplayName("Obtener DegreeWork por ID exitosamente")
    void testGetDegreeWorkById_Success() {
        // Arrange
        when(degreeWorkRepository.findById(1L))
                .thenReturn(Optional.of(mockDegreeWork));
        when(stateFactory.createState(any(DegreeWork.class)))
                .thenReturn(new DegreeWorkCreated(mockDegreeWork));

        // Act
        DegreeWork result = degreeWorkService.getDegreeWorkById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertNotNull(result.getState());
        assertTrue(result.getState() instanceof DegreeWorkCreated);

        verify(degreeWorkRepository, times(1)).findById(1L);
        verify(stateFactory, times(1)).createState(any(DegreeWork.class));
    }

    @Test
    @DisplayName("Lanzar excepción cuando DegreeWork no existe")
    void testGetDegreeWorkById_NotFound() {
        // Arrange
        when(degreeWorkRepository.findById(999L))
                .thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> degreeWorkService.getDegreeWorkById(999L)
        );

        assertTrue(exception.getMessage().contains("no encontrado"));
        assertTrue(exception.getMessage().contains("999"));

        verify(degreeWorkRepository, times(1)).findById(999L);
        verify(stateFactory, never()).createState(any(DegreeWork.class));
    }

    @Test
    @DisplayName("Subir Formato A cambia estado correctamente")
    void testUploadFormatA_Success() {
        // Arrange
        when(degreeWorkRepository.findById(1L))
                .thenReturn(Optional.of(mockDegreeWork));

        DegreeWorkCreated createdState = new DegreeWorkCreated(mockDegreeWork);
        when(stateFactory.createState(any(DegreeWork.class)))
                .thenReturn(createdState);

        DegreeWork updatedDegreeWork = new DegreeWork(
                1L,
                mockDegreeWork.getTitle(),
                mockDegreeWork.getDescription(),
                mockDegreeWork.getIdDirector(),
                mockDegreeWork.getIdCoordinator(),
                mockDegreeWork.getStudentIds(),
                Status.FORMAT_A,
                mockDegreeWork.getModality(),
                mockDegreeWork.getCreatedAt(),
                mockDegreeWork.getProcess()
        );

        when(degreeWorkRepository.save(any(DegreeWork.class)))
                .thenReturn(updatedDegreeWork);

        // Act
        DegreeWork result = degreeWorkService.uploadFormatA(1L);

        // Assert
        assertNotNull(result);
        assertEquals(Status.FORMAT_A, result.getStatus());

        verify(degreeWorkRepository, times(1)).findById(1L);
        verify(degreeWorkRepository, times(1)).save(any(DegreeWork.class));
    }

    @Test
    @DisplayName("Rechazar Formato A cambia estado a INACTIVE")
    void testRejectFormatA_Success() {
        // Arrange
        mockDegreeWork.setStatus(Status.FORMAT_A);

        when(degreeWorkRepository.findById(1L))
                .thenReturn(Optional.of(mockDegreeWork));

        DegreeWorkFormatA formatAState = new DegreeWorkFormatA(mockDegreeWork);
        when(stateFactory.createState(any(DegreeWork.class)))
                .thenReturn(formatAState);

        DegreeWork updatedDegreeWork = new DegreeWork(
                1L,
                mockDegreeWork.getTitle(),
                mockDegreeWork.getDescription(),
                mockDegreeWork.getIdDirector(),
                mockDegreeWork.getIdCoordinator(),
                mockDegreeWork.getStudentIds(),
                Status.INACTIVE,
                mockDegreeWork.getModality(),
                mockDegreeWork.getCreatedAt(),
                mockDegreeWork.getProcess()
        );

        when(degreeWorkRepository.save(any(DegreeWork.class)))
                .thenReturn(updatedDegreeWork);

        // Act
        DegreeWork result = degreeWorkService.rejectFormatA(1L);

        // Assert
        assertNotNull(result);
        assertEquals(Status.INACTIVE, result.getStatus());

        verify(degreeWorkRepository, times(1)).findById(1L);
        verify(degreeWorkRepository, times(1)).save(any(DegreeWork.class));
    }

    @Test
    @DisplayName("Aceptar Formato A cambia estado a FORMAT_A_ACCEPTED")
    void testAcceptFormatA_Success() {
        // Arrange
        mockDegreeWork.setStatus(Status.FORMAT_A);

        when(degreeWorkRepository.findById(1L))
                .thenReturn(Optional.of(mockDegreeWork));

        DegreeWorkFormatA formatAState = new DegreeWorkFormatA(mockDegreeWork);
        when(stateFactory.createState(any(DegreeWork.class)))
                .thenReturn(formatAState);

        DegreeWork updatedDegreeWork = new DegreeWork(
                1L,
                mockDegreeWork.getTitle(),
                mockDegreeWork.getDescription(),
                mockDegreeWork.getIdDirector(),
                mockDegreeWork.getIdCoordinator(),
                mockDegreeWork.getStudentIds(),
                Status.FORMAT_A_ACCEPTED,
                mockDegreeWork.getModality(),
                mockDegreeWork.getCreatedAt(),
                mockDegreeWork.getProcess()
        );

        when(degreeWorkRepository.save(any(DegreeWork.class)))
                .thenReturn(updatedDegreeWork);

        // Act
        DegreeWork result = degreeWorkService.acceptFormatA(1L);

        // Assert
        assertNotNull(result);
        assertEquals(Status.FORMAT_A_ACCEPTED, result.getStatus());

        verify(degreeWorkRepository, times(1)).findById(1L);
        verify(degreeWorkRepository, times(1)).save(any(DegreeWork.class));
    }

    @Test
    @DisplayName("Crear DegreeWork con studentIds vacío o nulo no lanza excepción")
    void testCreateDegreeWork_EmptyStudentIds() {
        // Arrange
        CreateDegreeWorkDTO dtoWithoutStudents = new CreateDegreeWorkDTO(
                "Proyecto sin estudiantes",
                "Descripción",
                12345L,
                67890L,
                new HashSet<>(),
                Modality.INVESTIGACION
        );

        DegreeWork mockResult = new DegreeWork(
                1L,
                dtoWithoutStudents.getTitle(),
                dtoWithoutStudents.getDescription(),
                dtoWithoutStudents.getIdDirector(),
                dtoWithoutStudents.getIdCoordinator(),
                new HashSet<>(),
                Status.CREATED,
                Modality.INVESTIGACION,
                LocalDateTime.now(),
                Process.FORMAT_A
        );

        when(degreeWorkRepository.save(any(DegreeWork.class)))
                .thenReturn(mockResult);

        // Act
        DegreeWork result = degreeWorkService.createDegreeWork(dtoWithoutStudents);

        // Assert
        assertNotNull(result);
        assertEquals(Status.CREATED, result.getStatus());

        // No debería intentar buscar trabajos existentes si no hay estudiantes
        verify(degreeWorkRepository, never())
                .findByStudentIdsAndStatusNot(anyList(), any(Status.class));
        verify(degreeWorkRepository, times(1)).save(any(DegreeWork.class));
    }
}