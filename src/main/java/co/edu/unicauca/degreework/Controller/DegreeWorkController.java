package co.edu.unicauca.degreework.Controller;

import co.edu.unicauca.degreework.Authentication.JwtRequestFilter;
import co.edu.unicauca.degreework.Comunication.Publisher;
import co.edu.unicauca.degreework.DTO.CreateDegreeWorkDTO;
import co.edu.unicauca.degreework.DTO.ResponseDTO;
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
    private final Publisher publisher;

    /**
     * Constructor for DegreeWorkController
     * @param degreeWorkService Service for degree work operations
     */
    @Autowired
    public DegreeWorkController(DegreeWorkService degreeWorkService, Publisher publisher) {
        this.degreeWorkService = degreeWorkService;
        this.publisher = publisher;
    }

    /**
     * Creates a new degree work
     * @param dto Degree work data transfer object
     * @return ResponseEntity with created degree work
     */
    @PostMapping("/create")
    public ResponseEntity<DegreeWorkResponseDTO> createDegreeWork(@RequestBody CreateDegreeWorkDTO dto) {
        String roles = JwtRequestFilter.getCurrentRoles();

        /*
        if (roles == null || !roles.contains("DIRECTOR")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        */

        Long accountId = JwtRequestFilter.getCurrentAccountId();
        dto.setIdDirector(accountId);

        if(accountId == null){
            dto.setIdDirector(1L);
        }

        DegreeWork created = degreeWorkService.createDegreeWork(dto);
        DegreeWorkResponseDTO response = degreeWorkService.toResponseDTO(created);
        PostComunQueue("DegreeWork creado con el id:"+created.getId()+"notificando a los interesados...");
        Logger.success(getClass(), "Creado degreework -> Dto response -> " + response.toString());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Retrieves degree work by ID
     * @param id Degree work identifier
     * @return ResponseEntity with degree work data
     */
    @GetMapping("/{id}")
    public ResponseEntity<DegreeWork> getDegreeWorkById(@PathVariable Long id) {
        DegreeWork degreeWork = degreeWorkService.getDegreeWorkById(id);
        return ResponseEntity.ok(degreeWork);
    }

    /**
     * Gets all degree works for current director
     * @return List of degree works for director
     */
    @GetMapping("/director/getAll")
    public ResponseEntity<List<ResponseDTO>> getDegreeWorksByDirector() {
        Long accountId = JwtRequestFilter.getCurrentAccountId();
        List<ResponseDTO> degreeWorks = degreeWorkService.getDegreeWorksByDirector(accountId);
        return ResponseEntity.ok(degreeWorks); // Returns empty list if no results
    }

    /**
     * Gets all degree works for current coordinator
     * @return List of degree works for coordinator
     */
    @GetMapping("/coordinator/getAll")
    public ResponseEntity<List<ResponseDTO>> getDegreeWorksByCoordinator() {
        Long accountId = JwtRequestFilter.getCurrentAccountId();
        List<ResponseDTO> degreeWorks = degreeWorkService.getDegreeWorksByCoordinator(accountId);
        return ResponseEntity.ok(degreeWorks);
    }

    /**
     * Uploads format A for degree work
     * @param id Degree work identifier
     * @return Updated degree work
     */
    @PostMapping("/{id}/upload-format-a")
    public ResponseEntity<DegreeWork> uploadFormatA(@PathVariable Long id) {
        DegreeWork updated = degreeWorkService.uploadFormatA(id);
        PostComunQueue("FormatoA diligenciado en el DegreeWork con id:"+updated.getId()+"notificando a los interesados...");
        return ResponseEntity.ok(updated);
    }

    /**
     * Accepts format A for degree work
     * @param id Degree work identifier
     * @return Success message
     */
    @PostMapping("/{id}/accept-format-a")
    public ResponseEntity<String> acceptFormatA(@PathVariable Long id) {
        degreeWorkService.acceptFormatA(id);
        PostComunQueue("formatoA aceptado en el DegreeWork con id:"+id+"notificando a los interesados...");
        return ResponseEntity.ok("Format A aceptado correctamente");
    }

    /**
     * Rejects format A for degree work
     * @param id Degree work identifier
     * @return Success message
     */
    @PostMapping("/{id}/reject-format-a")
    public ResponseEntity<String> rejectFormatA(@PathVariable Long id) {
        degreeWorkService.rejectFormatA(id);
        PostComunQueue("formatoA rechazado en el DegreeWork con id:"+id+"notificando a los interesados...");
        return ResponseEntity.ok("Format A rechazado");
    }

    /**
     * Approves draft for degree work
     * @param id Degree work identifier
     * @return Success message
     */
    @PostMapping("/{id}/aprove-draft")
    public ResponseEntity<String> approveDraft(@PathVariable Long id) {
        degreeWorkService.aproveDraft(id);
        PostComunQueue("Draft rechazado en el DegreeWork con id:"+id+"notificando a los interesados...");
        return ResponseEntity.ok("Draft aprobado correctamente");
    }

    /**
     * Uploads draft for degree work
     * @param id Degree work identifier
     * @return Updated degree work
     */
    @PostMapping("/{id}/upload-draft")
    public ResponseEntity<DegreeWork> uploadDraft(@PathVariable Long id) {
        DegreeWork updated = degreeWorkService.uploadDraft(id);
        PostComunQueue("Draft diligenciado en el DegreeWork con id:"+id+"notificando a los interesados...");
        return ResponseEntity.ok(updated);
    }

    /**
     * Expires draft time for degree work
     * @param id Degree work identifier
     * @return Updated degree work
     */
    @PostMapping("/{id}/expire-draft")
    public ResponseEntity<DegreeWork> expireDraftTime(@PathVariable Long id) {
        DegreeWork updated = degreeWorkService.expireDraftTime(id);
        PostComunQueue("Tiempo limite alcanzado para en Draft en el DegreeWork con id:"+id+"notificando a los interesados...");
        return ResponseEntity.ok(updated);
    }

    /**
     * Sends message to notification queue
     * @param message Message to send
     * @return Success response
     */
    @PostMapping("/notificationQueue")
    public ResponseEntity<String> PostComunQueue(@RequestBody String message) {
        publisher.sendMessageNotificationQueue(message);
        return ResponseEntity.ok("Message sent");
    }
}