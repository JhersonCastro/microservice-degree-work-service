package co.edu.unicauca.degreework.States;

import co.edu.unicauca.degreework.Model.DegreeWork;

/**
 * Represents the state of a {@link DegreeWork} after its draft has been approved.
 * <p>
 * In this state, the degree work has successfully passed the draft review process,
 * and no further state transitions are allowed from here.
 * All actions are intentionally ignored since the process is considered complete.
 * </p>
 */
public class DegreeWorkDraftAproved extends DegreeWorkState {

    /**
     * Constructs a new {@code DegreeWorkDraftAproved} state for the specified degree work.
     *
     * @param degreeWork the {@link DegreeWork} instance associated with this state
     */
    public DegreeWorkDraftAproved(DegreeWork degreeWork) {
        super(degreeWork);
    }

    /**
     * This action is ignored because Format A cannot be uploaded in this final state.
     */
    @Override
    public void uploadFormatA() {
        // Does nothing
    }

    /**
     * This action is ignored because Format A cannot be rejected in this state.
     */
    @Override
    public void formatARejected() {
        // Does nothing
    }

    /**
     * This action is ignored because Format A has already been processed.
     */
    @Override
    public void formatAAccepted() {
        // Does nothing
    }

    /**
     * This action is ignored because the draft cannot be uploaded again after approval.
     */
    @Override
    public void uploadDraft() {
        // Does nothing
    }

    /**
     * This action is ignored because the draft approval process is already completed.
     */
    @Override
    public void draftTimeExpired() {
        // Does nothing
    }

    /**
     * This action is ignored because the draft is already approved.
     */
    @Override
    public void draftAproved() {
        // Does nothing
    }
}
