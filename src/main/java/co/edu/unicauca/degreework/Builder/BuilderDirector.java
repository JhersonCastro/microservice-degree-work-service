package co.edu.unicauca.degreework.Builder;

import co.edu.unicauca.degreework.Model.DegreeWork;

public class BuilderDirector {

    DegreeWorkBuilder builder;

    public DegreeWork make(){
        builder.reset();
        return builder.getDegreeWork();
    };

    public BuilderDirector(DegreeWorkBuilder builder) {
        this.builder = builder;
    }
}
