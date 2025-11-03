package co.edu.unicauca.degreework.Controller;

import co.edu.unicauca.degreework.Authentication.JwtRequestFilter;
import co.edu.unicauca.degreework.DTO.CreateDegreeWorkDTO;
import co.edu.unicauca.degreework.DTO.DirectorResponseDTO;
import co.edu.unicauca.degreework.DTO.DegreeWorkResponseDTO;
import co.edu.unicauca.degreework.Model.DegreeWork;
import co.edu.unicauca.degreework.Service.DegreeWorkService;
import co.edu.unicauca.degreework.Utilities.Logger;
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
    public ResponseEntity<DegreeWorkResponseDTO> createDegreeWork(@RequestBody CreateDegreeWorkDTO dto) {
        String roles = JwtRequestFilter.getCurrentRoles();

        if (roles == null || !roles.contains("DIRECTOR")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Long accountId = JwtRequestFilter.getCurrentAccountId();
        dto.setIdDirector(accountId);

        DegreeWork created = degreeWorkService.createDegreeWork(dto);
        DegreeWorkResponseDTO response = degreeWorkService.toResponseDTO(created);
        Logger.success(getClass(), "Creado degreework -> Dto response -> " + response.toString());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<DegreeWork> getDegreeWorkById(@PathVariable Long id) {
        DegreeWork degreeWork = degreeWorkService.getDegreeWorkById(id);
        return ResponseEntity.ok(degreeWork);
    }

    @GetMapping("/director/getAll")
    public ResponseEntity<List<DirectorResponseDTO>> getDegreeWorksByDirector() {
        Long accountId = JwtRequestFilter.getCurrentAccountId();
        List<DirectorResponseDTO> degreeWorks = degreeWorkService.getDegreeWorksByDirector(accountId);
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

    @PostMapping("/{id}/aprove-draft")
    public ResponseEntity<DegreeWork> approveDraft(@PathVariable Long id) {
        DegreeWork updated = degreeWorkService.aproveDraft(id);
        return ResponseEntity.ok(updated);
    }
}