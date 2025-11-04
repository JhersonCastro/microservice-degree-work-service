package co.edu.unicauca.degreework.DTO;

import java.io.Serializable;

/**
 * DTO for communication between services
 */
public class ComunDTO implements Serializable {
    private Long id;
    private String action;

    /**
     * Constructs ComunDTO with ID and action
     * @param id Entity identifier
     * @param action Action to perform
     */
    public ComunDTO(Long id, String action) {
        this.id = id;
        this.action = action;
    }

    /**
     * Gets entity ID
     * @return Entity identifier
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets action to perform
     * @return Action command
     */
    public String getAction() {
        return action;
    }
}


