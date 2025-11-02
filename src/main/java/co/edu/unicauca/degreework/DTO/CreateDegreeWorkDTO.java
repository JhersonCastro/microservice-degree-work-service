package co.edu.unicauca.degreework.DTO;

import co.edu.unicauca.degreework.Enum.Modality;
import java.util.Set;

public class CreateDegreeWorkDTO {

    private String title;
    private String description;
    private Long idDirector;
    private Set<Long> studentIds;
    private Modality modality;

    // Constructores
    public CreateDegreeWorkDTO() {
    }

    public CreateDegreeWorkDTO(String title, String description, Long idDirector,
                               Set<Long> studentIds, Modality modality) {
        this.title = title;
        this.description = description;
        this.idDirector = idDirector;
        this.studentIds = studentIds;
        this.modality = modality;
    }

    // Getters y Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getIdDirector() {
        return idDirector;
    }

    public void setIdDirector(Long idDirector) {
        this.idDirector = idDirector;
    }

    public Set<Long> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(Set<Long> studentIds) {
        this.studentIds = studentIds;
    }

    public Modality getModality() {
        return modality;
    }

    public void setModality(Modality modality) {
        this.modality = modality;
    }
}
