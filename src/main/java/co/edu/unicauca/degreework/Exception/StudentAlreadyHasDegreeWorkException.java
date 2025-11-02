package co.edu.unicauca.degreework.Exception;

public class StudentAlreadyHasDegreeWorkException extends RuntimeException {

    private final Long studentId;

    public StudentAlreadyHasDegreeWorkException(Long studentId) {
        super("El estudiante con ID " + studentId + " ya tiene un trabajo de grado activo");
        this.studentId = studentId;
    }

    public StudentAlreadyHasDegreeWorkException(Long studentId, String message) {
        super(message);
        this.studentId = studentId;
    }

    public Long getStudentId() {
        return studentId;
    }
}