package co.edu.unicauca.degreework.DTO;

import co.edu.unicauca.degreework.Enum.Process;
import co.edu.unicauca.degreework.Enum.Status;

public class DirectorResponseDTO {
    private Long id;
    private String title;
    private String description;
    private Status status;
    private Process process;

    public DirectorResponseDTO(Long id, String title, String description, Status status, Process process) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.process = process;
    }

    // Getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Status getStatus() { return status; }
    public Process getProcess() { return process; }
}

