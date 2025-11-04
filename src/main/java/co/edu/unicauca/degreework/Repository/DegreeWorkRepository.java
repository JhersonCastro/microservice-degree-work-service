package co.edu.unicauca.degreework.Repository;

import co.edu.unicauca.degreework.DTO.ResponseDTO;
import co.edu.unicauca.degreework.Enum.Status;
import co.edu.unicauca.degreework.Model.DegreeWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link DegreeWork} entities.
 * Provides custom query methods to retrieve degree works based on students, directors, and coordinators.
 */
@Repository
public interface DegreeWorkRepository extends JpaRepository<DegreeWork, Long> {

    /**
     * Finds degree works by student ID where the status is not the specified one.
     *
     * @param studentId the ID of the student
     * @param status    the excluded {@link Status}
     * @return list of matching {@link DegreeWork} entities
     */
    @Query("SELECT dw FROM DegreeWork dw JOIN dw.studentIds s WHERE s = :studentId AND dw.status != :status")
    List<DegreeWork> findByStudentIdAndStatusNot(@Param("studentId") Long studentId,
                                                 @Param("status") Status status);

    /**
     * Finds degree works by multiple student IDs where the status is not the specified one.
     *
     * @param studentIds list of student IDs
     * @param status     the excluded {@link Status}
     * @return list of matching {@link DegreeWork} entities
     */
    @Query("SELECT dw FROM DegreeWork dw JOIN dw.studentIds s WHERE s IN :studentIds AND dw.status != :status")
    List<DegreeWork> findByStudentIdsAndStatusNot(@Param("studentIds") List<Long> studentIds,
                                                  @Param("status") Status status);

    /**
     * Retrieves degree works directed by a specific director, returning lightweight {@link ResponseDTO} objects.
     *
     * @param directorId the director's ID
     * @return list of {@link ResponseDTO} representing degree works
     */
    @Query("SELECT new co.edu.unicauca.degreework.DTO.ResponseDTO(dw.id, dw.title, dw.description, dw.status, dw.process) " +
            "FROM DegreeWork dw WHERE dw.idDirector = :directorId")
    List<ResponseDTO> findByDirectorId(@Param("directorId") Long directorId);

    /**
     * Retrieves degree works coordinated by a specific coordinator, returning lightweight {@link ResponseDTO} objects.
     *
     * @param coordinatorId the coordinator's ID
     * @return list of {@link ResponseDTO} representing degree works
     */
    @Query("SELECT new co.edu.unicauca.degreework.DTO.ResponseDTO(dw.id, dw.title, dw.description, dw.status, dw.process) " +
            "FROM DegreeWork dw WHERE dw.idCoordinator = :coordinatorId")
    List<ResponseDTO> findByCoordinatorId(@Param("coordinatorId") Long coordinatorId);

    /**
     * Finds a degree work by its unique ID.
     *
     * @param dw_id the degree work ID
     * @return the corresponding {@link DegreeWork} or {@code null} if not found
     */
    @Query("SELECT dw from DegreeWork dw WHERE dw.id = :dw_id")
    DegreeWork findDegreeWorkById(@Param("dw_id") Long dw_id);
}
