package co.edu.unicauca.degreework.Observer;

import co.edu.unicauca.degreework.Model.DegreeWork;
import co.edu.unicauca.degreework.Service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NewDegreeWorkListener implements EventListener {

    private static final Logger logger = LoggerFactory.getLogger(NewDegreeWorkListener.class);

    private final NotificationService notificationService;

    @Autowired
    public NewDegreeWorkListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public void update(String eventType, Object data) {
        if (!"DEGREE_WORK_CREATED".equals(eventType)) {
            return; // Solo procesar eventos de creación de DegreeWork
        }

        if (!(data instanceof DegreeWork)) {
            logger.error("Error: Se esperaba un objeto DegreeWork pero se recibió: {}",
                    data.getClass().getSimpleName());
            return;
        }

        DegreeWork degreeWork = (DegreeWork) data;

        // Usar el NotificationService para enviar notificaciones
        notificationService.loggerNotification(degreeWork.getId().toString());
    }
}