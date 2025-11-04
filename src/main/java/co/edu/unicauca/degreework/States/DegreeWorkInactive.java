package co.edu.unicauca.degreework.States;

import co.edu.unicauca.degreework.Model.DegreeWork;

/**
 * Represents the state of a {@link DegreeWork} that has become inactive.
 * <p>
 * A degree work enters this state when a key step in the process fails
 * or when the allowed time for an action (such as draft submission) expires.
 * </p>
 * <p>
 * In this state, no further actions are allowed, and all operations are ignored.
 * </p>
 */
public class DegreeWorkInactive extends DegreeWorkState {

    /**
     * Constructs a new {@code DegreeWorkInactive} state for the specified degree work.
     *
     * @param degreeWork the {@link DegreeWork} instance associated with this state
     */
    public DegreeWorkInactive(DegreeWork degreeWork) {
        super(degreeWork);
    }

    /**
     * This action is ignored because the degree work is inactive.
     */
    @Override
    public void uploadFormatA() {
        // Does nothing
    }

    /**
     * This action is ignored because the degree work is inactive.
     */
    @Override
    public void formatARejected() {
        // Does nothing
    }

    /**
     * This action is ignored because the degree work is inactive.
     */
    @Override
    public void formatAAccepted() {
        // Does nothing
    }

    /**
     * This action is ignored because the degree work is inactive.
     */
    @Override
    public void uploadDraft() {
        // Does nothing
    }

    /**
     * This action is ignored because the degree work is inactive.
     */
    @Override
    public void draftTimeExpired() {
        // Does nothing
    }

    /**
     * This action is ignored because the degree work is inactive.
     */
    @Override
    public void draftAproved() {
        // Does nothing
    }
}
