package co.edu.unicauca.degreework.States;

import co.edu.unicauca.degreework.Model.DegreeWork;

/**
 * Abstract base class representing the general state of a {@link DegreeWork}.
 * <p>
 * This class defines the contract for all possible actions that can occur
 * during the lifecycle of a degree work, such as uploading Format A, handling
 * approvals or rejections, uploading the draft, and managing time expiration.
 * </p>
 * <p>
 * Concrete subclasses implement specific behaviors for each action depending
 * on the current state of the degree work.
 * </p>
 */
public abstract class DegreeWorkState {

    /**
     * The {@link DegreeWork} instance associated with this state.
     */
    protected DegreeWork degreeWork;

    /**
     * Constructs a new {@code DegreeWorkState} for the specified degree work.
     *
     * @param degreeWork the {@link DegreeWork} instance associated with this state
     */
    public DegreeWorkState(DegreeWork degreeWork) {
        this.degreeWork = degreeWork;
    }

    /**
     * Handles the action of uploading Format A.
     * Implementations define whether this action is valid or ignored in the current state.
     */
    public abstract void uploadFormatA();

    /**
     * Handles the event when Format A is rejected.
     * Implementations define the resulting state transition, if any.
     */
    public abstract void formatARejected();

    /**
     * Handles the event when Format A is accepted.
     * Implementations define the resulting state transition, if any.
     */
    public abstract void formatAAccepted();

    /**
     * Handles the action of uploading the draft document.
     * Implementations define whether this action is valid or ignored in the current state.
     */
    public abstract void uploadDraft();

    /**
     * Handles the event when the time limit for draft submission expires.
     * Implementations define the resulting state transition, if any.
     */
    public abstract void draftTimeExpired();

    /**
     * Handles the event when the draft is approved.
     * Implementations define the resulting state transition, if any.
     */
    public abstract void draftAproved();
}
