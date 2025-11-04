package co.edu.unicauca.degreework.Factory;

import co.edu.unicauca.degreework.Enum.Status;
import co.edu.unicauca.degreework.Model.DegreeWork;
import co.edu.unicauca.degreework.States.*;
import org.springframework.stereotype.Component;

/**
 * Factory for creating degree work states based on status
 */
@Component
public class DegreeWorkStateFactory {

    /**
     * Creates state instance based on degree work status
     * @param degreeWork Degree work entity
     * @return Corresponding state instance
     */
    public DegreeWorkState createState(DegreeWork degreeWork) {
        Status status = degreeWork.getStatus();

        return switch (status) {
            case CREATED -> new DegreeWorkCreated(degreeWork);
            case FORMAT_A -> new DegreeWorkFormatA(degreeWork);
            case FORMAT_A_ACCEPTED -> new DegreeWorkFormatAAccepted(degreeWork);
            case DRAFT -> new DegreeWorkDraft(degreeWork);
            case INACTIVE -> new DegreeWorkInactive(degreeWork);
            default -> throw new IllegalStateException("Estado no reconocido: " + status);
        };
    }
}