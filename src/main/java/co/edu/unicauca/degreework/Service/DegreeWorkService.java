package co.edu.unicauca.degreework.Service;

import co.edu.unicauca.degreework.Model.DegreeWork;
import co.edu.unicauca.degreework.Repository.DegreeWorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DegreeWorkService {

    private final DegreeWorkRepository degreeWorkRepository;

    @Autowired
    public DegreeWorkService(DegreeWorkRepository degreeWorkRepository) {
        this.degreeWorkRepository = degreeWorkRepository;
    }

    @Transactional
    public DegreeWork createDegreeWork(DegreeWork degreeWork) {
        return degreeWorkRepository.save(degreeWork);
    }

    @Transactional(readOnly = true)
    public DegreeWork getDegreeWorkById(Long id) {
        return degreeWorkRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new RuntimeException("DegreeWork not found with id: " + id));
    }
}