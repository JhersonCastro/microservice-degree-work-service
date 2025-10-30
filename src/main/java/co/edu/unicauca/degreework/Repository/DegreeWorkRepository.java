package co.edu.unicauca.degreework.Repository;

import co.edu.unicauca.degreework.Model.DegreeWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DegreeWorkRepository extends JpaRepository<DegreeWork, Integer> {
}

