package co.edu.unicauca.degreework.States;

import co.edu.unicauca.degreework.Model.DegreeWork;

/**
 * Represents the state of a {@link DegreeWork} when the draft has been uploaded
 * and is pending approval or expiration.
 * <p>
 * In this state, most actions are ignored except for approving the draft,
 * which triggers a transition to the {@link DegreeWorkDraftAproved} state.
 * </p>
 */
public class DegreeWorkDraft extends DegreeWorkState {

    /**
     * Constructs a new {@code DegreeWorkDraft} state for the specified degree work.
     *
     * @param degreeWork the {@link DegreeWork} instance associated with this state
     */
    public DegreeWorkDraft(DegreeWork degreeWork) {
        super(degreeWork);
    }

    /**
     * This action is ignored because Format A cannot be uploaded in the draft state.
     */
    @Override
    public void uploadFormatA() {
        // Does nothing
    }

    /**
     * This action is ignored because Format A has already been accepted.
     */
    @Override
    public void formatARejected() {
        // Does nothing
    }

    /**
     * This action is ignored because Format A has already been accepted.
     */
    @Override
    public void formatAAccepted() {
        // Does nothing
    }

    /**
     * This action is ignored because the draft has already been uploaded.
     */
    @Override
    public void uploadDraft() {
        // Does nothing
    }

    /**
     * This action is ignored because draft expiration is not handled here.
     */
    @Override
    public void draftTimeExpired() {
        // Does nothing
    }

    /**
     * Handles the approval of the draft.
     * <p>
     * When the draft is approved, the degree work transitions to
     * the {@link DegreeWorkDraftAproved} state.
     * </p>
     */
    @Override
    public void draftAproved() {
        this.degreeWork.changeState(new DegreeWorkDraftAproved(degreeWork));
    }
}
