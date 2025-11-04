package co.edu.unicauca.degreework.Model;

import co.edu.unicauca.degreework.Enum.Modality;
import co.edu.unicauca.degreework.Enum.Process;
import co.edu.unicauca.degreework.Enum.Status;
import co.edu.unicauca.degreework.States.DegreeWorkState;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a degree work
 */
@Entity
@Table(name = "degree_work")
public class DegreeWork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Column(name = "id_director")
    private Long idDirector;

    @Column(name = "id_coordinator")
    private Long idCoordinator;

    @ElementCollection
    @CollectionTable(
            name = "degree_work_students",
            joinColumns = @JoinColumn(name = "id_degree_work")
    )
    @Column(name = "id_student")
    private Set<Long> studentIds = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Modality modality;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "process")
    private Process process;

    @Transient
    @JsonIgnore
    private DegreeWorkState state;

    /**
     * Default constructor
     */
    public DegreeWork() {
    }

    /**
     * Constructs DegreeWork with all fields
     * @param id Degree work ID
     * @param title Degree work title
     * @param description Degree work description
     * @param idDirector Director ID
     * @param idCoordinator Coordinator ID
     * @param studentIds Set of student IDs
     * @param status Current status
     * @param modality Work modality
     * @param createdAt Creation timestamp
     * @param process Current process
     */
    public DegreeWork(Long id, String title, String description, Long idDirector,
                      Long idCoordinator, Set<Long> studentIds, Status status,
                      Modality modality, LocalDateTime createdAt, Process process) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.idDirector = idDirector;
        this.idCoordinator = idCoordinator;
        this.studentIds = studentIds != null ? studentIds : new HashSet<>();
        this.status = status;
        this.modality = modality;
        this.createdAt = createdAt;
        this.process = process;
    }

    /**
     * Sets creation timestamp before persisting
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    /**
     * Adds student ID to degree work
     * @param studentId Student identifier
     */
    public void addStudentId(Long studentId) {
        if (studentId == null) {
            throw new IllegalArgumentException("El ID del estudiante no puede ser nulo.");
        }
        studentIds.add(studentId);
    }

    /**
     * Changes degree work state and updates status/process
     * @param newState New state to set
     */
    public void changeState(DegreeWorkState newState) {
        this.state = newState;
        this.status = determineStatusFromState(newState);
        this.process = convertStateToProcess(newState);
    }

    /**
     * Determines status based on state class
     * @param state Degree work state
     * @return Corresponding status
     */
    private Status determineStatusFromState(DegreeWorkState state) {
        return switch (state.getClass().getSimpleName()) {
            case "DegreeWorkCreated" -> Status.CREATED;
            case "DegreeWorkFormatA" -> Status.FORMAT_A;
            case "DegreeWorkFormatAAccepted" -> Status.FORMAT_A_ACCEPTED;
            case "DegreeWorkDraft" -> Status.DRAFT;
            case "DegreeWorkInactive" -> Status.INACTIVE;
            default -> this.status;
        };
    }

    /**
     * Converts state to process enum
     * @param state Degree work state
     * @return Corresponding process
     */
    private Process convertStateToProcess(DegreeWorkState state) {
        return switch (state.getClass().getSimpleName()) {
            case "DegreeWorkCreated", "DegreeWorkInactive" -> Process.INACTIVO;
            case "DegreeWorkFormatA", "DegreeWorkFormatAAccepted" -> Process.FORMATO_A;
            case "DegreeWorkDraft" -> Process.ANTEPROYECTO;
            default -> this.process;
        };
    }

    // Getters and Setters
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

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public DegreeWorkState getState() {
        return state;
    }

    public void setState(DegreeWorkState state) {
        this.state = state;
    }
}