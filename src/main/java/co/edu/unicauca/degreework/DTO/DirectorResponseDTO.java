package co.edu.unicauca.degreework.DTO;

import co.edu.unicauca.degreework.Enum.Status;

public class DirectorResponseDTO {
    private Long id;
    private String title;
    private String description;
    private Status status;

    public DirectorResponseDTO(Long id, String title,String description, Status status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
    }
    public DirectorResponseDTO() {}


    // Getters
    public String getDescription(){return description;}
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public Status getStatus() { return status; }
}

