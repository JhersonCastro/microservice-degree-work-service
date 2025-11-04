package co.edu.unicauca.degreework.Observer;

import co.edu.unicauca.degreework.Model.DegreeWork;
import co.edu.unicauca.degreework.Service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Event listener that reacts to {@code "DEGREE_WORK_CREATED"} events.
 * When a new {@link DegreeWork} is created, it logs and sends a notification.
 */
@Component
public class NewDegreeWorkListener implements EventListener {

    private static final Logger logger = LoggerFactory.getLogger(NewDegreeWorkListener.class);

    private final NotificationService notificationService;

    /**
     * Constructs the listener with the required {@link NotificationService}.
     *
     * @param notificationService the service responsible for sending notifications
     */
    @Autowired
    public NewDegreeWorkListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Processes events and triggers notifications only for {@code "DEGREE_WORK_CREATED"}.
     * Logs an error if the provided data is not a {@link DegreeWork} instance.
     *
     * @param eventType the type of event received
     * @param data      the event payload (expected to be a {@link DegreeWork})
     */
    @Override
    public void update(String eventType, Object data) {
        if (!"DEGREE_WORK_CREATED".equals(eventType)) {
            return; // Ignore other event types
        }

        if (!(data instanceof DegreeWork)) {
            logger.error("Error: Expected DegreeWork object but received: {}",
                    data.getClass().getSimpleName());
            return;
        }

        DegreeWork degreeWork = (DegreeWork) data;

        // Trigger notification through the service
        notificationService.loggerNotification(degreeWork.getId().toString());
    }
}
