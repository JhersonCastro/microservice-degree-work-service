package co.edu.unicauca.degreework;

import co.edu.unicauca.degreework.Enum.Modality;
import co.edu.unicauca.degreework.Enum.Process;
import co.edu.unicauca.degreework.Enum.Status;
import co.edu.unicauca.degreework.Model.DegreeWork;
import co.edu.unicauca.degreework.States.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("State Pattern Tests")
class DegreeWorkStateTest {

    private DegreeWork degreeWork;
    private Set<Long> studentIds;

    @BeforeEach
    void setUp() {
        studentIds = new HashSet<>();
        studentIds.add(1001L);
        studentIds.add(1002L);

        degreeWork = new DegreeWork(
                1L,
                "Test Project",
                "Test Description",
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
    @DisplayName("Estado CREATED permite subir Formato A")
    void testCreatedState_CanUploadFormatA() {
        // Arrange
        DegreeWorkState state = new DegreeWorkCreated(degreeWork);
        degreeWork.setState(state);

        // Act
        degreeWork.getState().uploadFormatA();

        // Assert
        assertInstanceOf(DegreeWorkFormatA.class, degreeWork.getState());
        assertEquals(Status.FORMAT_A, degreeWork.getStatus());
    }

    @Test
    @DisplayName("Estado CREATED no permite otras transiciones")
    void testCreatedState_DoesNotAllowOtherTransitions() {
        // Arrange
        DegreeWorkState state = new DegreeWorkCreated(degreeWork);
        degreeWork.setState(state);
        Status initialStatus = degreeWork.getStatus();

        // Act
        degreeWork.getState().formatARejected();
        degreeWork.getState().formatAAccepted();
        degreeWork.getState().uploadDraft();
        degreeWork.getState().draftTimeExpired();

        // Assert
        assertInstanceOf(DegreeWorkCreated.class, degreeWork.getState());
        assertEquals(initialStatus, degreeWork.getStatus());
    }

    @Test
    @DisplayName("Estado FORMAT_A permite aceptar formato")
    void testFormatAState_CanAcceptFormat() {
        // Arrange
        degreeWork.setStatus(Status.FORMAT_A);
        DegreeWorkState state = new DegreeWorkFormatA(degreeWork);
        degreeWork.setState(state);

        // Act
        degreeWork.getState().formatAAccepted();

        // Assert
        assertInstanceOf(DegreeWorkFormatAAccepted.class, degreeWork.getState());
        assertEquals(Status.FORMAT_A_ACCEPTED, degreeWork.getStatus());
    }

    @Test
    @DisplayName("Estado FORMAT_A permite rechazar formato")
    void testFormatAState_CanRejectFormat() {
        // Arrange
        degreeWork.setStatus(Status.FORMAT_A);
        DegreeWorkState state = new DegreeWorkFormatA(degreeWork);
        degreeWork.setState(state);

        // Act
        degreeWork.getState().formatARejected();

        // Assert
        assertInstanceOf(DegreeWorkInactive.class, degreeWork.getState());
        assertEquals(Status.INACTIVE, degreeWork.getStatus());
    }

    @Test
    @DisplayName("Estado FORMAT_A_ACCEPTED permite subir borrador")
    void testFormatAAcceptedState_CanUploadDraft() {
        // Arrange
        degreeWork.setStatus(Status.FORMAT_A_ACCEPTED);
        DegreeWorkState state = new DegreeWorkFormatAAccepted(degreeWork);
        degreeWork.setState(state);

        // Act
        degreeWork.getState().uploadDraft();

        // Assert
        assertInstanceOf(DegreeWorkDraft.class, degreeWork.getState());
        assertEquals(Status.DRAFT, degreeWork.getStatus());
    }

    @Test
    @DisplayName("Estado FORMAT_A_ACCEPTED permite expirar tiempo")
    void testFormatAAcceptedState_CanExpireTime() {
        // Arrange
        degreeWork.setStatus(Status.FORMAT_A_ACCEPTED);
        DegreeWorkState state = new DegreeWorkFormatAAccepted(degreeWork);
        degreeWork.setState(state);

        // Act
        degreeWork.getState().draftTimeExpired();

        // Assert
        assertInstanceOf(DegreeWorkInactive.class, degreeWork.getState());
        assertEquals(Status.INACTIVE, degreeWork.getStatus());
    }

    @Test
    @DisplayName("Estado INACTIVE no permite ninguna transición")
    void testInactiveState_DoesNotAllowAnyTransition() {
        // Arrange
        degreeWork.setStatus(Status.INACTIVE);
        DegreeWorkState state = new DegreeWorkInactive(degreeWork);
        degreeWork.setState(state);
        Status initialStatus = degreeWork.getStatus();

        // Act
        degreeWork.getState().uploadFormatA();
        degreeWork.getState().formatARejected();
        degreeWork.getState().formatAAccepted();
        degreeWork.getState().uploadDraft();
        degreeWork.getState().draftTimeExpired();

        // Assert
        assertInstanceOf(DegreeWorkInactive.class, degreeWork.getState());
        assertEquals(initialStatus, degreeWork.getStatus());
    }

    @Test
    @DisplayName("Estado DRAFT no permite ninguna transición")
    void testDraftState_DoesNotAllowAnyTransition() {
        // Arrange
        degreeWork.setStatus(Status.DRAFT);
        DegreeWorkState state = new DegreeWorkDraft(degreeWork);
        degreeWork.setState(state);
        Status initialStatus = degreeWork.getStatus();

        // Act
        degreeWork.getState().uploadFormatA();
        degreeWork.getState().formatARejected();
        degreeWork.getState().formatAAccepted();
        degreeWork.getState().uploadDraft();
        degreeWork.getState().draftTimeExpired();

        // Assert
        assertInstanceOf(DegreeWorkDraft.class, degreeWork.getState());
        assertEquals(initialStatus, degreeWork.getStatus());
    }
}