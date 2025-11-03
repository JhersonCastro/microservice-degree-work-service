package co.edu.unicauca.degreework.States;

import co.edu.unicauca.degreework.Model.DegreeWork;

public class DegreeWorkFormatA extends DegreeWorkState{

    public DegreeWorkFormatA(DegreeWork degreeWork) {
        super(degreeWork);
    }

    @Override
    public void uploadFormatA() {
        // no hace nada
    }

    @Override
    public void formatARejected() {
        degreeWork.changeState(new DegreeWorkInactive(degreeWork));
    }

    @Override
    public void formatAAccepted() {
        degreeWork.changeState(new DegreeWorkFormatAAccepted(degreeWork));
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
        // no hace nada
    }
}
