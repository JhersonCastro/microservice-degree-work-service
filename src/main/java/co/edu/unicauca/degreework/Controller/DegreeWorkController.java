package co.edu.unicauca.degreework.Controller;

import co.edu.unicauca.degreework.DTO.CreateDegreeWorkDTO;
import co.edu.unicauca.degreework.DTO.DirectorResponseDTO;
import co.edu.unicauca.degreework.Model.DegreeWork;
import co.edu.unicauca.degreework.Service.DegreeWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/degreework")
public class DegreeWorkController {

    private final DegreeWorkService degreeWorkService;

    @Autowired
    public DegreeWorkController(DegreeWorkService degreeWorkService) {
        this.degreeWorkService = degreeWorkService;
    }

    @PostMapping("/create")
    public ResponseEntity<DegreeWork> createDegreeWork(@RequestBody CreateDegreeWorkDTO dto) {
        DegreeWork created = degreeWorkService.createDegreeWork(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DegreeWork> getDegreeWorkById(@PathVariable Long id) {
        DegreeWork degreeWork = degreeWorkService.getDegreeWorkById(id);
        return ResponseEntity.ok(degreeWork);
    }

    @GetMapping("/director/{directorId}")
    public ResponseEntity<List<DirectorResponseDTO>> getDegreeWorksByDirector(@PathVariable Long directorId) {
        List<DirectorResponseDTO> degreeWorks = degreeWorkService.getDegreeWorksByDirector(directorId);
        return ResponseEntity.ok(degreeWorks); // Devuelve lista vacía si no hay resultados
    }

    // Endpoints para transiciones de estado
    @PostMapping("/{id}/upload-format-a")
    public ResponseEntity<DegreeWork> uploadFormatA(@PathVariable Long id) {
        DegreeWork updated = degreeWorkService.uploadFormatA(id);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/{id}/reject-format-a")
    public ResponseEntity<DegreeWork> rejectFormatA(@PathVariable Long id) {
        DegreeWork updated = degreeWorkService.rejectFormatA(id);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/{id}/accept-format-a")
    public ResponseEntity<DegreeWork> acceptFormatA(@PathVariable Long id) {
        DegreeWork updated = degreeWorkService.acceptFormatA(id);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/{id}/upload-draft")
    public ResponseEntity<DegreeWork> uploadDraft(@PathVariable Long id) {
        DegreeWork updated = degreeWorkService.uploadDraft(id);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/{id}/expire-draft")
    public ResponseEntity<DegreeWork> expireDraftTime(@PathVariable Long id) {
        DegreeWork updated = degreeWorkService.expireDraftTime(id);
        return ResponseEntity.ok(updated);
    }
}