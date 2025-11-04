package co.edu.unicauca.degreework.States;

import co.edu.unicauca.degreework.Model.DegreeWork;

/**
 * Represents the state of a {@link DegreeWork} when it has just been created.
 * <p>
 * In this state, only the action to upload Format A is allowed.
 * Other actions are ignored since they are not applicable yet.
 * </p>
 */
public class DegreeWorkCreated extends DegreeWorkState {

    /**
     * Constructs a new {@code DegreeWorkCreated} state for the specified degree work.
     *
     * @param degreeWork the {@link DegreeWork} instance associated with this state
     */
    public DegreeWorkCreated(DegreeWork degreeWork) {
        super(degreeWork);
    }

    /**
     * Handles the action of uploading Format A.
     * <p>
     * When this occurs, the degree work transitions to the {@link DegreeWorkFormatA} state.
     * </p>
     */
    @Override
    public void uploadFormatA() {
        degreeWork.changeState(new DegreeWorkFormatA(degreeWork));
    }

    /**
     * This action is ignored because Format A has not been uploaded yet.
     */
    @Override
    public void formatARejected() {
        // Does nothing
    }

    /**
     * This action is ignored because Format A has not been uploaded yet.
     */
    @Override
    public void formatAAccepted() {
        // Does nothing
    }

    /**
     * This action is ignored because Format A must be uploaded before uploading a draft.
     */
    @Override
    public void uploadDraft() {
        // Does nothing
    }

    /**
     * This action is ignored because the draft stage has not been reached.
     */
    @Override
    public void draftTimeExpired() {
        // Does nothing
    }

    /**
     * This action is ignored because no draft has been uploaded yet.
     */
    @Override
    public void draftAproved() {
        // Does nothing
    }
}
