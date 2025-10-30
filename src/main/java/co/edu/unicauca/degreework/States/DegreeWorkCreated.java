package co.edu.unicauca.degreework.States;

import co.edu.unicauca.degreework.Model.DegreeWork;

public class DegreeWorkCreated extends DegreeWorkState{
    public DegreeWorkCreated(DegreeWork degreeWork) {
        super(degreeWork);
    }

    @Override
    public void uploadFormatA() {
        degreeWork.changeState(new DegreeWorkFormatA(degreeWork));
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
}
