package co.edu.unicauca.degreework.States;

import co.edu.unicauca.degreework.Model.DegreeWork;

public abstract class DegreeWorkState {

    protected DegreeWork degreeWork;

    public DegreeWorkState(DegreeWork degreeWork) {
        this.degreeWork = degreeWork;
    }

    public abstract void uploadFormatA();
    public abstract void formatARejected();
    public abstract void formatAAccepted();
    public abstract void uploadDraft();
    public abstract void draftTimeExpired();
}
