package co.edu.unicauca.degreework.Service;

import co.edu.unicauca.degreework.Model.DegreeWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public void loggerNotification(String idDegreeWork) {
        logger.info("=== NEW DEGREE WORK CREATED ===");
        logger.info(idDegreeWork);
    }
}