package co.edu.unicauca.degreework.States;

import co.edu.unicauca.degreework.Model.DegreeWork;

public class DegreeWorkFormatAAccepted extends DegreeWorkState{
    public DegreeWorkFormatAAccepted(DegreeWork degreeWork) {
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
        degreeWork.changeState(new DegreeWorkDraft(degreeWork));
    }

    @Override
    public void draftTimeExpired() {
        degreeWork.changeState(new DegreeWorkInactive(degreeWork));
    }

    @Override
    public void draftAproved() {
        // no hace nada
    }
}
