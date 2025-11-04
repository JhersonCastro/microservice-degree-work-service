package co.edu.unicauca.degreework.States;

import co.edu.unicauca.degreework.Model.DegreeWork;

/**
 * Represents the state of a {@link DegreeWork} after Format A has been accepted.
 * <p>
 * In this state, the degree work is ready for the draft upload phase.
 * The allowed transitions are:
 * <ul>
 *     <li>{@link DegreeWorkDraft} – when the draft is uploaded.</li>
 *     <li>{@link DegreeWorkInactive} – when the draft submission period expires.</li>
 * </ul>
 * All other actions are ignored.
 * </p>
 */
public class DegreeWorkFormatAAccepted extends DegreeWorkState {

    /**
     * Constructs a new {@code DegreeWorkFormatAAccepted} state for the specified degree work.
     *
     * @param degreeWork the {@link DegreeWork} instance associated with this state
     */
    public DegreeWorkFormatAAccepted(DegreeWork degreeWork) {
        super(degreeWork);
    }

    /**
     * This action is ignored because Format A has already been uploaded.
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
     * This action is ignored because Format A is already in an accepted state.
     */
    @Override
    public void formatAAccepted() {
        // Does nothing
    }

    /**
     * Handles the action of uploading the draft.
     * <p>
     * When this occurs, the degree work transitions to the {@link DegreeWorkDraft} state.
     * </p>
     */
    @Override
    public void uploadDraft() {
        degreeWork.changeState(new DegreeWorkDraft(degreeWork));
    }

    /**
     * Handles the expiration of the draft submission period.
     * <p>
     * When the time to submit the draft expires, the degree work transitions
     * to the {@link DegreeWorkInactive} state.
     * </p>
     */
    @Override
    public void draftTimeExpired() {
        degreeWork.changeState(new DegreeWorkInactive(degreeWork));
    }

    /**
     * This action is ignored because the draft has not been uploaded yet.
     */
    @Override
    public void draftAproved() {
        // Does nothing
    }
}
