package com.csys.template.web.rest.ressource;

import com.csys.template.dto.EquipeDTO;
import com.csys.template.service.EquipeService;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class EquipeResource {

  private static final String ENTITY_NAME = "equipe";
  private final Logger log = LoggerFactory.getLogger(EquipeResource.class);
  private final EquipeService equipeService;

  public EquipeResource(EquipeService equipeService) {
    this.equipeService = equipeService;
  }

  /**
   * POST /equipes : Create a new equipe.
   */
  @PostMapping("/equipes")
  public ResponseEntity<EquipeDTO> createEquipe(@Valid @RequestBody EquipeDTO equipeDTO, BindingResult bindingResult)
      throws URISyntaxException, MethodArgumentNotValidException {
    log.debug("REST request to save Equipe : {}", equipeDTO);

    if (equipeDTO.getId() != null) {
      bindingResult.addError(
          new FieldError("EquipeDTO", "id", "A new " + ENTITY_NAME + " cannot already have an ID"));
      throw new MethodArgumentNotValidException(null, bindingResult);
    }

    if (bindingResult.hasErrors()) {
      throw new MethodArgumentNotValidException(null, bindingResult);
    }

    EquipeDTO result = equipeService.save(equipeDTO);
    return ResponseEntity.created(new URI("/api/equipes/" + result.getId())).body(result);
  }

  /**
   * PUT /equipes/{id} : Updates an existing equipe.
   */
  @PutMapping("/equipes/{id}")
  public ResponseEntity<EquipeDTO> updateEquipe(@PathVariable Integer id, @Valid @RequestBody EquipeDTO equipeDTO)
      throws MethodArgumentNotValidException {
    log.debug("REST request to update Equipe : {}", id);
    equipeDTO.setId(id);
    EquipeDTO result = equipeService.update(equipeDTO);
    return ResponseEntity.ok().body(result);
  }

  /**
   * GET /equipes/{id} : Get the "id" equipe.
   */
  @GetMapping("/equipes/{id}")
  public ResponseEntity<EquipeDTO> getEquipe(@PathVariable Integer id) {
    log.debug("REST request to get Equipe : {}", id);
    EquipeDTO dto = equipeService.findOne(id);
    RestPreconditions.checkFound(dto, "equipe.NotFound");
    return ResponseEntity.ok().body(dto);
  }

  /**
   * GET /equipes : Get all the equipes.
   */
  @GetMapping("/equipes")
  public Collection<EquipeDTO> getAllEquipes() {
    log.debug("REST request to get all Equipes");
    return equipeService.findAll();
  }

  /**
   * DELETE /equipes/{id} : Delete the "id" equipe.
   */
  @DeleteMapping("/equipes/{id}")
  public ResponseEntity<Void> deleteEquipe(@PathVariable Integer id) {
    log.debug("REST request to delete Equipe : {}", id);
    equipeService.delete(id);
    return ResponseEntity.ok().build();
  }
}
