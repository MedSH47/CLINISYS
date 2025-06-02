package com.csys.template.web.rest.ressource;

import com.csys.template.domain.EquipePosteutilisateurPK;
import com.csys.template.dto.EquipePosteutilisateurDTO;
import com.csys.template.service.EquipePosteutilisateurService;
import com.csys.template.util.RestPreconditions;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List; // Changed from Collection for consistency
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing EquipePosteutilisateur.
 */
@RestController
@RequestMapping("/api")
public class EquipePosteutilisateurResource {
    // private static final String ENTITY_NAME = "equipeposteutilisateur"; // Not used, can be removed

    private final EquipePosteutilisateurService equipePosteutilisateurService;
    private final Logger log = LoggerFactory.getLogger(EquipePosteutilisateurResource.class); // Corrected logger class

    public EquipePosteutilisateurResource(EquipePosteutilisateurService equipePosteutilisateurService) {
        this.equipePosteutilisateurService = equipePosteutilisateurService;
    }

    /**
     * POST /equipeposteutilisateurs : Create a new equipeposteutilisateur.
     *
     * @param equipePosteutilisateurDTO the DTO to create
     * @param bindingResult for validation
     * @return the ResponseEntity with status 201 (Created) and with body the new equipePosteutilisateurDTO, or with status 400 (Bad Request) if the equipePosteutilisateurDTO is not valid
     * @throws URISyntaxException if the Location URI syntax is incorrect
     * @throws MethodArgumentNotValidException if validation fails
     */
    @PostMapping("/equipeposteutilisateurs")
    public ResponseEntity<EquipePosteutilisateurDTO> createEquipePosteutilisateur(
            @Valid @RequestBody EquipePosteutilisateurDTO equipePosteutilisateurDTO, BindingResult bindingResult)
            throws URISyntaxException, MethodArgumentNotValidException {
        log.debug("REST request to save EquipePosteutilisateur : {}", equipePosteutilisateurDTO);

        // CORRECTION: Simplified PK validation. The DTO's internal setters for individual IDs
        // should ensure the PK object is constructed. We just need to check the individual IDs.
        if (equipePosteutilisateurDTO.getIdPoste() == null ||
            equipePosteutilisateurDTO.getIdUtilisateur() == null ||
            equipePosteutilisateurDTO.getIdEquipe() == null) {
            bindingResult.addError(new FieldError("EquipePosteutilisateurDTO", "idPoste", // More specific field
                    "POST method requires idPoste, idUtilisateur, and idEquipe to be present in the request body."));
            // It's good practice to also add errors for idUtilisateur and idEquipe if they are null
            if (equipePosteutilisateurDTO.getIdUtilisateur() == null) {
                 bindingResult.addError(new FieldError("EquipePosteutilisateurDTO", "idUtilisateur", "idUtilisateur cannot be null."));
            }
             if (equipePosteutilisateurDTO.getIdEquipe() == null) {
                 bindingResult.addError(new FieldError("EquipePosteutilisateurDTO", "idEquipe", "idEquipe cannot be null."));
            }
            throw new MethodArgumentNotValidException(null, bindingResult); // Parameter must be non-null
        }

        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(null, bindingResult); // Parameter must be non-null
        }

        EquipePosteutilisateurDTO result = equipePosteutilisateurService.save(equipePosteutilisateurDTO);
        
        // Construct the URI for the newly created resource
        String locationUri = String.format("/api/equipeposteutilisateurs/%d/%d/%d",
                result.getIdPoste(), result.getIdUtilisateur(), result.getIdEquipe());
        return ResponseEntity.created(new URI(locationUri)).body(result);
    }

    /**
     * PUT /equipeposteutilisateurs/{idPoste}/{idUtilisateur}/{idEquipe} : Updates an existing equipeposteutilisateur.
     * This effectively acts as a create or update (UPSERT) because the service's save method handles both.
     *
     * @param idPoste the ID of the poste
     * @param idUtilisateur the ID of the utilisateur
     * @param idEquipe the ID of the equipe
     * @param equipePosteutilisateurDTO the DTO to update with
     * @return the ResponseEntity with status 200 (OK) and with body the updated equipePosteutilisateurDTO,
     * or with status 400 (Bad Request) if the equipePosteutilisateurDTO is not valid,
     * or with status 500 (Internal Server Error) if the equipePosteutilisateurDTO couldn't be updated
     * @throws MethodArgumentNotValidException if validation fails
     */
    @PutMapping("/equipeposteutilisateurs/{idPoste}/{idUtilisateur}/{idEquipe}")
    public ResponseEntity<EquipePosteutilisateurDTO> updateEquipePosteutilisateur(
            @PathVariable Integer idPoste,
            @PathVariable Integer idUtilisateur,
            @PathVariable Integer idEquipe,
            @Valid @RequestBody EquipePosteutilisateurDTO equipeposteutilisateurDTO)
            throws MethodArgumentNotValidException { // Removed URISyntaxException as it's not thrown here
        log.debug("REST request to update EquipePosteutilisateur: idPoste={}, idUtilisateur={}, idEquipe={}",
                idPoste, idUtilisateur, idEquipe);

        // Set the PK from path variables to ensure consistency
        equipeposteutilisateurDTO.setIdPoste(idPoste);
        equipeposteutilisateurDTO.setIdUtilisateur(idUtilisateur);
        equipeposteutilisateurDTO.setIdEquipe(idEquipe);
        
        // If you want to ensure that the DTO in the body also has these IDs, you can add checks here.
        // However, @Valid should handle basic validation if annotations are present in the DTO.

        // CORRECTION: The service layer now only has a 'save' method which handles both create and update.
        EquipePosteutilisateurDTO result = equipePosteutilisateurService.save(equipeposteutilisateurDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * GET /equipeposteutilisateurs/{idPoste}/{idUtilisateur}/{idEquipe} : get the equipeposteutilisateur by composite key.
     *
     * @param idPoste the ID of the poste
     * @param idUtilisateur the ID of the utilisateur
     * @param idEquipe the ID of the equipe
     * @return the ResponseEntity with status 200 (OK) and with body the equipePosteutilisateurDTO, or with status 404 (Not Found)
     */
    @GetMapping("/equipeposteutilisateurs/{idPoste}/{idUtilisateur}/{idEquipe}")
    public ResponseEntity<EquipePosteutilisateurDTO> getEquipePosteutilisateur(
            @PathVariable Integer idPoste,
            @PathVariable Integer idUtilisateur,
            @PathVariable Integer idEquipe) {
        log.debug("Request to get EquipePosteutilisateur: idPoste={}, idUtilisateur={}, idEquipe={}",
                idPoste, idUtilisateur, idEquipe);
        EquipePosteutilisateurPK id = new EquipePosteutilisateurPK(idPoste, idUtilisateur, idEquipe);
        EquipePosteutilisateurDTO dto = equipePosteutilisateurService.findOne(id);
        RestPreconditions.checkFound(dto, "EquipePosteutilisateur with id " + id + " not found."); // More descriptive message
        return ResponseEntity.ok().body(dto);
    }

    /**
     * GET /equipeposteutilisateurs : get all the equipeposteutilisateurs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of equipeposteutilisateurs in body
     */
    @GetMapping("/equipeposteutilisateurs")
    public List<EquipePosteutilisateurDTO> getAllEquipePosteutilisateurs() { // Return type changed to List
        log.debug("Request to get all EquipePosteutilisateurs");
        // CORRECTION: The service's findAll method no longer takes a boolean.
        return equipePosteutilisateurService.findAll();
    }

    /**
     * DELETE /equipeposteutilisateurs/{idPoste}/{idUtilisateur}/{idEquipe} : delete the equipeposteutilisateur by composite key.
     *
     * @param idPoste the ID of the poste
     * @param idUtilisateur the ID of the utilisateur
     * @param idEquipe the ID of the equipe
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/equipeposteutilisateurs/{idPoste}/{idUtilisateur}/{idEquipe}")
    public ResponseEntity<Void> deleteEquipePosteutilisateur(
            @PathVariable Integer idPoste,
            @PathVariable Integer idUtilisateur,
            @PathVariable Integer idEquipe) {
        log.debug("Request to delete EquipePosteutilisateur: idPoste={}, idUtilisateur={}, idEquipe={}",
                idPoste, idUtilisateur, idEquipe);
        EquipePosteutilisateurPK id = new EquipePosteutilisateurPK(idPoste, idUtilisateur, idEquipe);
        equipePosteutilisateurService.delete(id);
        return ResponseEntity.ok().build();
    }
}