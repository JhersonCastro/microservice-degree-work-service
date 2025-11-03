package co.edu.unicauca.degreework.DTO;

import co.edu.unicauca.degreework.Enum.Status;
import co.edu.unicauca.degreework.Enum.Process;

public class ResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String status;
    private String process;

    public ResponseDTO(Long id, String title, String description, Status status, Process process) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = adaptStatus(status);
        this.process = prettyProcess(process);
    }
    public ResponseDTO() {}


    // Getters
    public String getDescription(){return description;}
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getStatus() { return status; }
    public String getProcess() { return process; }

    private String adaptStatus(Status status) {
        return switch (status) {
            case CREATED -> " ";
            case FORMAT_A, DRAFT -> "EN PROCESO";
            case FORMAT_A_ACCEPTED, APROVED -> "APROBADO";
            case INACTIVE -> "DESAPROBADO";
        };
    }

    private String prettyProcess(Process process) {
        return switch (process){
            case FORMATO_A -> "FORMATO A";
            case ANTEPROYECTO -> "ANTEPROYECTO";
            case INACTIVO -> " ";
        };
    }
}

