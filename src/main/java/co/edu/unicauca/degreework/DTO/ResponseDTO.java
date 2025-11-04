package co.edu.unicauca.degreework.DTO;

import co.edu.unicauca.degreework.Enum.Status;
import co.edu.unicauca.degreework.Enum.Process;

/**
 * Simplified DTO for degree work responses
 */
public class ResponseDTO {
    private Long id;
    private String title;
    private String description;
    private Status status;
    private Process process;

    /**
     * Constructs ResponseDTO with all fields
     * @param id Degree work ID
     * @param title Degree work title
     * @param description Degree work description
     * @param status Current status
     * @param process Current process
     */
    public ResponseDTO(Long id, String title, String description, Status status, Process process) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.process = process;
        this.status = status;
    }

    /**
     * Default constructor
     */
    public ResponseDTO() {}

    // Getters
    public String getDescription(){return description;}
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public Status getStatus() { return status; }
    public Process getProcess() { return process; }
}


