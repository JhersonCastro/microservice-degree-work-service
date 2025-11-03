package co.edu.unicauca.degreework;

import co.edu.unicauca.degreework.Controller.DegreeWorkController;
import co.edu.unicauca.degreework.DTO.CreateDegreeWorkDTO;
import co.edu.unicauca.degreework.Enum.Modality;
import co.edu.unicauca.degreework.Enum.Process;
import co.edu.unicauca.degreework.Enum.Status;
import co.edu.unicauca.degreework.Exception.StudentAlreadyHasDegreeWorkException;
import co.edu.unicauca.degreework.Model.DegreeWork;
import co.edu.unicauca.degreework.Service.DegreeWorkService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DegreeWorkController.class)
@DisplayName("DegreeWork Controller Tests")
class DegreeWorkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean  // ← Cambio aquí
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
                67890L,
                studentIds,
                Modality.INVESTIGACION
        );

        mockDegreeWork = new DegreeWork(
                1L,
                "Sistema de gestión de proyectos",
                "Plataforma web para gestionar trabajos de grado",
                12345L,
                67890L,
                studentIds,
                Status.CREATED,
                Modality.INVESTIGACION,
                LocalDateTime.now(),
                Process.FORMATO_A
        );
    }

    @Test
    @DisplayName("POST /api/degreework/create - Success")
    void testCreateDegreeWork_Success() throws Exception {
        // Arrange
        when(degreeWorkService.createDegreeWork(any(CreateDegreeWorkDTO.class)))
                .thenReturn(mockDegreeWork);

        // Act & Assert
        mockMvc.perform(post("/api/degreework/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Sistema de gestión de proyectos"))
                .andExpect(jsonPath("$.status").value("CREATED"))
                .andExpect(jsonPath("$.process").value("FORMAT_A"))
                .andExpect(jsonPath("$.modality").value("INVESTIGATION"))
                .andExpect(jsonPath("$.studentIds").isArray())
                .andExpect(jsonPath("$.state").doesNotExist());

        verify(degreeWorkService, times(1)).createDegreeWork(any(CreateDegreeWorkDTO.class));
    }

    @Test
    @DisplayName("POST /api/degreework/create - Student Already Has Active DegreeWork")
    void testCreateDegreeWork_StudentAlreadyHasActiveDegreeWork() throws Exception {
        // Arrange
        when(degreeWorkService.createDegreeWork(any(CreateDegreeWorkDTO.class)))
                .thenThrow(new StudentAlreadyHasDegreeWorkException(1001L));

        // Act & Assert
        mockMvc.perform(post("/api/degreework/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDTO)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.error").value("Conflict"))
                .andExpect(jsonPath("$.studentId").value(1001))
                .andExpect(jsonPath("$.message").exists());

        verify(degreeWorkService, times(1)).createDegreeWork(any(CreateDegreeWorkDTO.class));
    }

    @Test
    @DisplayName("GET /api/degreework/{id} - Success")
    void testGetDegreeWorkById_Success() throws Exception {
        // Arrange
        when(degreeWorkService.getDegreeWorkById(1L))
                .thenReturn(mockDegreeWork);

        // Act & Assert
        mockMvc.perform(get("/api/degreework/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Sistema de gestión de proyectos"))
                .andExpect(jsonPath("$.status").value("CREATED"));

        verify(degreeWorkService, times(1)).getDegreeWorkById(1L);
    }

    @Test
    @DisplayName("GET /api/degreework/{id} - Not Found")
    void testGetDegreeWorkById_NotFound() throws Exception {
        // Arrange
        when(degreeWorkService.getDegreeWorkById(999L))
                .thenThrow(new RuntimeException("DegreeWork no encontrado con id: 999"));

        // Act & Assert
        mockMvc.perform(get("/api/degreework/999"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value("DegreeWork no encontrado con id: 999"));

        verify(degreeWorkService, times(1)).getDegreeWorkById(999L);
    }
}