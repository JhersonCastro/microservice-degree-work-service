package co.edu.unicauca.degreework.DTO;

import co.edu.unicauca.degreework.Enum.Modality;
import java.util.Set;

/**
 * DTO for creating degree work
 */
public class CreateDegreeWorkDTO {

    private String title;
    private String description;
    private Long idDirector;
    private Long idCoordinator;
    private Set<Long> studentIds;
    private Modality modality;

    /**
     * Default constructor
     */
    public CreateDegreeWorkDTO() {
    }

    /**
     * Constructs CreateDegreeWorkDTO with all fields
     * @param title Degree work title
     * @param description Degree work description
     * @param idDirector Director ID
     * @param idCoordinator Coordinator ID
     * @param studentIds Set of student IDs
     * @param modality Work modality
     */
    public CreateDegreeWorkDTO(String title, String description, Long idDirector,
                               Long idCoordinator, Set<Long> studentIds, Modality modality) {
        this.title = title;
        this.description = description;
        this.idDirector = idDirector;
        this.idCoordinator = idCoordinator;
        this.studentIds = studentIds;
        this.modality = modality;
    }

    // Getters and Setters
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

    public Long getIdCoordinator() {
        return idCoordinator;
    }

    public void setIdCoordinator(Long idCoordinator) {
        this.idCoordinator = idCoordinator;
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
