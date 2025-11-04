package co.edu.unicauca.degreework.States;

import co.edu.unicauca.degreework.Model.DegreeWork;

/**
 * Represents the state of a {@link DegreeWork} after Format A has been uploaded
 * and is pending approval or rejection.
 * <p>
 * In this state, the degree work can transition either to:
 * <ul>
 *     <li>{@link DegreeWorkInactive} – if Format A is rejected.</li>
 *     <li>{@link DegreeWorkFormatAAccepted} – if Format A is accepted.</li>
 * </ul>
 * Other actions are ignored until a decision is made.
 * </p>
 */
public class DegreeWorkFormatA extends DegreeWorkState {

    /**
     * Constructs a new {@code DegreeWorkFormatA} state for the specified degree work.
     *
     * @param degreeWork the {@link DegreeWork} instance associated with this state
     */
    public DegreeWorkFormatA(DegreeWork degreeWork) {
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
     * Handles the rejection of Format A.
     * <p>
     * When Format A is rejected, the degree work transitions to the
     * {@link DegreeWorkInactive} state.
     * </p>
     */
    @Override
    public void formatARejected() {
        degreeWork.changeState(new DegreeWorkInactive(degreeWork));
    }

    /**
     * Handles the approval of Format A.
     * <p>
     * When Format A is accepted, the degree work transitions to the
     * {@link DegreeWorkFormatAAccepted} state.
     * </p>
     */
    @Override
    public void formatAAccepted() {
        degreeWork.changeState(new DegreeWorkFormatAAccepted(degreeWork));
    }

    /**
     * This action is ignored because a draft cannot be uploaded
     * before Format A is approved.
     */
    @Override
    public void uploadDraft() {
        // Does nothing
    }

    /**
     * This action is ignored because the draft stage has not been reached yet.
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
