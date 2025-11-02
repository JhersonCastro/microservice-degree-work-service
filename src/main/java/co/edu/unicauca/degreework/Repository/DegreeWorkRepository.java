package co.edu.unicauca.degreework.Repository;

import co.edu.unicauca.degreework.Enum.Status;
import co.edu.unicauca.degreework.Model.DegreeWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DegreeWorkRepository extends JpaRepository<DegreeWork, Long> {

    @Query("SELECT dw FROM DegreeWork dw JOIN dw.studentIds s WHERE s = :studentId AND dw.status != :status")
    List<DegreeWork> findByStudentIdAndStatusNot(@Param("studentId") Long studentId,
                                                 @Param("status") Status status);

    @Query("SELECT dw FROM DegreeWork dw JOIN dw.studentIds s WHERE s IN :studentIds AND dw.status != :status")
    List<DegreeWork> findByStudentIdsAndStatusNot(@Param("studentIds") List<Long> studentIds,
                                                  @Param("status") Status status);
}
