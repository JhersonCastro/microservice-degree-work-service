package co.edu.unicauca.degreework.Builder;

import co.edu.unicauca.degreework.Enum.Modality;
import co.edu.unicauca.degreework.Enum.Process;
import co.edu.unicauca.degreework.Model.DegreeWork;

public interface DegreeWorkBuilder {

    public void reset();
    public void buildStudent(Long id);
    public void buildDirector(Long id);
    public void buildModality(Modality modality);
    public void buildTitle(String title);
    public void buildDescription(String description);
    public void buildProcess(Process process);
    public DegreeWork getDegreeWork();

}
