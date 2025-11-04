package co.edu.unicauca.degreework.Service;

import co.edu.unicauca.degreework.DTO.ComunDTO;
import co.edu.unicauca.degreework.Model.DegreeWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for handling and sending different types of notifications
 * related to degree work events in the system.
 * <p>
 * This service provides methods to log new degree work events and manage
 * communications via {@link ComunDTO}.
 * </p>
 */
@Service
public class NotificationService {

    /**
     * Logger instance used to record notification-related events.
     */
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    /**
     * Logs a notification when a new degree work is created.
     *
     * @param idDegreeWork the unique identifier of the created {@link DegreeWork}
     */
    public void loggerNotification(String idDegreeWork) {
        logger.info("=== NEW DEGREE WORK CREATED ===");
        logger.info(idDegreeWork);
    }

    /**
     * Sends or processes a communication notification based on the provided
     * {@link ComunDTO} object.
     *
     * @param comunDTO the data transfer object containing communication details
     */
    public void comunNotification(ComunDTO comunDTO) {

    }
}
