package co.edu.unicauca.degreework;

import co.edu.unicauca.degreework.Enum.Modality;
import co.edu.unicauca.degreework.Enum.Process;
import co.edu.unicauca.degreework.Enum.Status;
import co.edu.unicauca.degreework.Factory.DegreeWorkStateFactory;
import co.edu.unicauca.degreework.Model.DegreeWork;
import co.edu.unicauca.degreework.States.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DegreeWorkStateFactory Tests")
class DegreeWorkStateFactoryTest {

    private DegreeWorkStateFactory factory;
    private DegreeWork degreeWork;
    private Set<Long> studentIds;

    @BeforeEach
    void setUp() {
        factory = new DegreeWorkStateFactory();

        studentIds = new HashSet<>();
        studentIds.add(1001L);

        degreeWork = new DegreeWork(
                1L,
                "Test Project",
                "Test Description",
                12345L,
                67890L,  // ← NUEVO idCoordinator
                studentIds,
                Status.CREATED,
                Modality.INVESTIGATION,
                LocalDateTime.now(),
                Process.FORMAT_A
        );
    }

    @Test
    @DisplayName("Factory crea DegreeWorkCreated para status CREATED")
    void testCreateState_Created() {
        // Arrange
        degreeWork.setStatus(Status.CREATED);

        // Act
        DegreeWorkState state = factory.createState(degreeWork);

        // Assert
        assertNotNull(state);
        assertInstanceOf(DegreeWorkCreated.class, state);
    }

    @Test
    @DisplayName("Factory crea DegreeWorkFormatA para status FORMAT_A")
    void testCreateState_FormatA() {
        // Arrange
        degreeWork.setStatus(Status.FORMAT_A);

        // Act
        DegreeWorkState state = factory.createState(degreeWork);

        // Assert
        assertNotNull(state);
        assertInstanceOf(DegreeWorkFormatA.class, state);
    }

    @Test
    @DisplayName("Factory crea DegreeWorkFormatAAccepted para status FORMAT_A_ACCEPTED")
    void testCreateState_FormatAAccepted() {
        // Arrange
        degreeWork.setStatus(Status.FORMAT_A_ACCEPTED);

        // Act
        DegreeWorkState state = factory.createState(degreeWork);

        // Assert
        assertNotNull(state);
        assertInstanceOf(DegreeWorkFormatAAccepted.class, state);
    }

    @Test
    @DisplayName("Factory crea DegreeWorkDraft para status DRAFT")
    void testCreateState_Draft() {
        // Arrange
        degreeWork.setStatus(Status.DRAFT);

        // Act
        DegreeWorkState state = factory.createState(degreeWork);

        // Assert
        assertNotNull(state);
        assertInstanceOf(DegreeWorkDraft.class, state);
    }

    @Test
    @DisplayName("Factory crea DegreeWorkInactive para status INACTIVE")
    void testCreateState_Inactive() {
        // Arrange
        degreeWork.setStatus(Status.INACTIVE);

        // Act
        DegreeWorkState state = factory.createState(degreeWork);

        // Assert
        assertNotNull(state);
        assertInstanceOf(DegreeWorkInactive.class, state);
    }
}