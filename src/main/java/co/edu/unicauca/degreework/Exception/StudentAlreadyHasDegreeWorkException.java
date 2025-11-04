package co.edu.unicauca.degreework.Exception;

/**
 * Exception for when a student already has an active degree work
 */
public class StudentAlreadyHasDegreeWorkException extends RuntimeException {

    private final Long studentId;

    /**
     * Constructs exception with student ID
     * @param studentId ID of the student with existing degree work
     */
    public StudentAlreadyHasDegreeWorkException(Long studentId) {
        super("El estudiante con ID " + studentId + " ya tiene un trabajo de grado activo");
        this.studentId = studentId;
    }

    /**
     * Constructs exception with student ID and custom message
     * @param studentId ID of the student with existing degree work
     * @param message Custom error message
     */
    public StudentAlreadyHasDegreeWorkException(Long studentId, String message) {
        super(message);
        this.studentId = studentId;
    }

    /**
     * Gets the student ID
     * @return Student identifier
     */
    public Long getStudentId() {
        return studentId;
    }
}