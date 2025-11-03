package co.edu.unicauca.degreework.States;

import co.edu.unicauca.degreework.Model.DegreeWork;

public class DegreeWorkDraft extends DegreeWorkState{
    public DegreeWorkDraft(DegreeWork degreeWork) {
        super(degreeWork);
    }

    @Override
    public void uploadFormatA() {
        // no hace nada
    }

    @Override
    public void formatARejected() {
        // no hace nada
    }

    @Override
    public void formatAAccepted() {
        // no hace nada
    }

    @Override
    public void uploadDraft() {
        // no hace nada
    }

    @Override
    public void draftTimeExpired() {
        // no hace nada
    }

    @Override
    public void draftAproved() {
        this.degreeWork.changeState(new DegreeWorkDraftAproved(degreeWork));
    }
}
