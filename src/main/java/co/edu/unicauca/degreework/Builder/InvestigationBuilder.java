package co.edu.unicauca.degreework.Builder;

import co.edu.unicauca.degreework.Enum.Modality;
import co.edu.unicauca.degreework.Enum.Process;
import co.edu.unicauca.degreework.Model.DegreeWork;

public class InvestigationBuilder implements DegreeWorkBuilder{

    private DegreeWork degreeWork;

    @Override
    public void reset() {
        this.degreeWork = new DegreeWork();
    }

    @Override
    public void buildStudent(Long id) {
        degreeWork.addStudentId(id);
    }

    @Override
    public void buildDirector(Long id) {
        degreeWork.setIdDirector(id);
    }

    @Override
    public void buildModality(Modality modality) {
        degreeWork.setModality(modality);
    }

    @Override
    public void buildTitle(String title) {
        degreeWork.setTitle(title);
    }

    @Override
    public void buildDescription(String description) {
        degreeWork.setDescription(description);
    }

    @Override
    public void buildProcess(Process process) {
        degreeWork.setProcess(process);
    }

    @Override
    public DegreeWork getDegreeWork() {
        return degreeWork;
    }
}
