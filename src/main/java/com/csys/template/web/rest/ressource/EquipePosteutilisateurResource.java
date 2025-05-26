package com.csys.template.web.rest.ressource;

import com.csys.template.domain.EquipePosteutilisateurPK;
import com.csys.template.dto.EquipePosteutilisateurDTO;
import com.csys.template.service.EquipePosteutilisateurService;
import com.csys.template.util.RestPreconditions;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
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
  private static final String ENTITY_NAME = "equipeposteutilisateur";

  private final EquipePosteutilisateurService equipeposteutilisateurService;

  private final Logger log = LoggerFactory.getLogger(EquipePosteutilisateurService.class);

  public EquipePosteutilisateurResource(EquipePosteutilisateurService equipeposteutilisateurService) {
    this.equipeposteutilisateurService = equipeposteutilisateurService;
  }

  /**
   * POST /equipeposteutilisateurs : Create a new equipeposteutilisateur.
   */
  @PostMapping("/equipeposteutilisateurs")
  public ResponseEntity<EquipePosteutilisateurDTO> createEquipePosteutilisateur(
      @Valid @RequestBody EquipePosteutilisateurDTO equipeposteutilisateurDTO, BindingResult bindingResult)
      throws URISyntaxException, MethodArgumentNotValidException {
    log.debug("REST request to save EquipePosteutilisateur : {}", equipeposteutilisateurDTO);
    if (equipeposteutilisateurDTO.getEquipePosteutilisateurPK() == null ||
        equipeposteutilisateurDTO.getIdPoste() == null ||
        equipeposteutilisateurDTO.getIdUtilisateur() == null ||
        equipeposteutilisateurDTO.getIdEquipe() == null) {
      bindingResult.addError(new FieldError("EquipePosteutilisateurDTO", "equipePosteutilisateurPK",
          "POST method requires idPoste, idUtilisateur, and idEquipe"));
      throw new MethodArgumentNotValidException(null, bindingResult);
    }
    if (bindingResult.hasErrors()) {
      throw new MethodArgumentNotValidException(null, bindingResult);
    }
    EquipePosteutilisateurDTO result = equipeposteutilisateurService.save(equipeposteutilisateurDTO);
    String locationUri = String.format("/api/equipeposteutilisateurs/%d/%d/%d",
        result.getIdPoste(), result.getIdUtilisateur(), result.getIdEquipe());
    return ResponseEntity.created(new URI(locationUri)).body(result);
  }

  /**
   * PUT /equipeposteutilisateurs/{idPoste}/{idUtilisateur}/{idEquipe} : Updates an existing equipeposteutilisateur.
   */
  @PutMapping("/equipeposteutilisateurs/{idPoste}/{idUtilisateur}/{idEquipe}")
  public ResponseEntity<EquipePosteutilisateurDTO> updateEquipePosteutilisateur(
      @PathVariable Integer idPoste,
      @PathVariable Integer idUtilisateur,
      @PathVariable Integer idEquipe,
      @Valid @RequestBody EquipePosteutilisateurDTO equipeposteutilisateurDTO)
      throws MethodArgumentNotValidException {
    log.debug("Request to update EquipePosteutilisateur: idPoste={}, idUtilisateur={}, idEquipe={}",
        idPoste, idUtilisateur, idEquipe);
    EquipePosteutilisateurPK id = new EquipePosteutilisateurPK(idPoste, idUtilisateur, idEquipe);
    equipeposteutilisateurDTO.setEquipePosteutilisateurPK(id);
    EquipePosteutilisateurDTO result = equipeposteutilisateurService.update(equipeposteutilisateurDTO);
    return ResponseEntity.ok().body(result);
  }

  /**
   * GET /equipeposteutilisateurs/{idPoste}/{idUtilisateur}/{idEquipe} : get the equipeposteutilisateur by composite key.
   */
  @GetMapping("/equipeposteutilisateurs/{idPoste}/{idUtilisateur}/{idEquipe}")
  public ResponseEntity<EquipePosteutilisateurDTO> getEquipePosteutilisateur(
      @PathVariable Integer idPoste,
      @PathVariable Integer idUtilisateur,
      @PathVariable Integer idEquipe) {
    log.debug("Request to get EquipePosteutilisateur: idPoste={}, idUtilisateur={}, idEquipe={}",
        idPoste, idUtilisateur, idEquipe);
    EquipePosteutilisateurPK id = new EquipePosteutilisateurPK(idPoste, idUtilisateur, idEquipe);
    EquipePosteutilisateurDTO dto = equipeposteutilisateurService.findOne(id);
    RestPreconditions.checkFound(dto, "equipeposteutilisateur.NotFound");
    return ResponseEntity.ok().body(dto);
  }

  /**
   * GET /equipeposteutilisateurs : get all the equipeposteutilisateurs.
   */
  @GetMapping("/equipeposteutilisateurs")
  public Collection<EquipePosteutilisateurDTO> getAllEquipePosteutilisateurs() {
    log.debug("Request to get all EquipePosteutilisateurs");
    return equipeposteutilisateurService.findAll(false);
  }

  /**
   * GET /equipeposteutilisateurs/details : get all the equipeposteutilisateurs with details.
   */
  @GetMapping("/equipeposteutilisateurs/details")
  public Collection<EquipePosteutilisateurDTO> getAllEquipePosteutilisateursWithDetails() {
    log.debug("Request to get all EquipePosteutilisateurs with details");
    return equipeposteutilisateurService.findAll(true);
  }

  /**
   * DELETE /equipeposteutilisateurs/{idPoste}/{idUtilisateur}/{idEquipe} : delete the equipeposteutilisateur by composite key.
   */
  @DeleteMapping("/equipeposteutilisateurs/{idPoste}/{idUtilisateur}/{idEquipe}")
  public ResponseEntity<Void> deleteEquipePosteutilisateur(
      @PathVariable Integer idPoste,
      @PathVariable Integer idUtilisateur,
      @PathVariable Integer idEquipe) {
    log.debug("Request to delete EquipePosteutilisateur: idPoste={}, idUtilisateur={}, idEquipe={}",
        idPoste, idUtilisateur, idEquipe);
    EquipePosteutilisateurPK id = new EquipePosteutilisateurPK(idPoste, idUtilisateur, idEquipe);
    equipeposteutilisateurService.delete(id);
    return ResponseEntity.ok().build();
  }
}