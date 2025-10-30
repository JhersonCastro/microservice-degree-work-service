package co.edu.unicauca.degreework.Controller;

import co.edu.unicauca.degreework.Model.DegreeWork;
import co.edu.unicauca.degreework.Service.DegreeWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/degreework")
public class DegreeWorkController {

    private final DegreeWorkService degreeWorkService;

    @Autowired
    public DegreeWorkController(DegreeWorkService degreeWorkService) {
        this.degreeWorkService = degreeWorkService;
    }

    @PostMapping("/create")
    public ResponseEntity<DegreeWork> createDegreeWork(@RequestBody DegreeWork degreeWork) {
        DegreeWork created = degreeWorkService.createDegreeWork(degreeWork);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DegreeWork> getDegreeWorkById(@PathVariable Long id) {
        DegreeWork degreeWork = degreeWorkService.getDegreeWorkById(id);
        return ResponseEntity.ok(degreeWork);
    }
}