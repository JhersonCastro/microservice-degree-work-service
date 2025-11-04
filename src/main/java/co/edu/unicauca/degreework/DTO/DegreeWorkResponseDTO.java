package co.edu.unicauca.degreework.DTO;

import co.edu.unicauca.degreework.Enum.Modality;
import co.edu.unicauca.degreework.Enum.Process;
import co.edu.unicauca.degreework.Enum.Status;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for degree work response
 */
public class DegreeWorkResponseDTO {

    private Long id;
    private String title;
    private String description;
    private Long idDirector;
    private Long idCoordinator;
    private Set<Long> studentIds;
    private Status status;
    private Modality modality;
    private LocalDateTime createdAt;

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Modality getModality() {
        return modality;
    }

    public void setModality(Modality modality) {
        this.modality = modality;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "DegreeWorkResponseDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", idDirector=" + idDirector +
                ", idCoordinator=" + idCoordinator +
                ", studentIds=" + studentIds +
                ", status=" + status +
                ", modality=" + modality +
                ", createdAt=" + createdAt +
                '}';
    }
}
