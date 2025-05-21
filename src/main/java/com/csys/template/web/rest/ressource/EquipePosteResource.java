package com.csys.template.web.rest.ressource;
import com.csys.template.util.RestPreconditions;

import com.csys.template.dto.EquipePosteDTO;
import com.csys.template.service.EquipePosteService;
import java.lang.Integer;
import java.lang.String;
import java.lang.Void;
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
 * REST controller for managing EquipePoste.
 */
@RestController
@RequestMapping("/api")
public class EquipePosteResource {
  private static final String ENTITY_NAME = "equipeposte";

  private final EquipePosteService equipeposteService;

  private final Logger log = LoggerFactory.getLogger(EquipePosteService.class);

  public EquipePosteResource(EquipePosteService equipeposteService) {
    this.equipeposteService=equipeposteService;
  }

  /**
   * POST  /equipepostes : Create a new equipeposte.
   *
   * @param equipeposteDTO
   * @param bindingResult
   * @return the ResponseEntity with status 201 (Created) and with body the new equipeposte, or with status 400 (Bad Request) if the equipeposte has already an ID
   * @throws URISyntaxException if the Location URI syntax is incorrect
   * @throws org.springframework.web.bind.MethodArgumentNotValidException
   */
  @PostMapping("/equipepostes")
  public ResponseEntity<EquipePosteDTO> createEquipePoste(@Valid @RequestBody EquipePosteDTO equipeposteDTO, BindingResult bindingResult) throws URISyntaxException, MethodArgumentNotValidException {
    log.debug("REST request to save EquipePoste : {}", equipeposteDTO);
    if ( equipeposteDTO.getId() != null) {
      bindingResult.addError( new FieldError("EquipePosteDTO","id","POST method does not accepte "+ENTITY_NAME+" with code"));
      throw new MethodArgumentNotValidException(null, bindingResult);
    }
    if (bindingResult.hasErrors()) {
      throw new MethodArgumentNotValidException(null, bindingResult);
    }
    EquipePosteDTO result = equipeposteService.save(equipeposteDTO);
    return ResponseEntity.created( new URI("/api/equipepostes/"+ result.getId())).body(result);
  }

  /**
   * PUT  /equipepostes : Updates an existing equipeposte.
   *
   * @param id
   * @param equipeposteDTO the equipeposte to update
   * @return the ResponseEntity with status 200 (OK) and with body the updated equipeposte,
   * or with status 400 (Bad Request) if the equipeposte is not valid,
   * or with status 500 (Internal Server Error) if the equipeposte couldn't be updated
   * @throws org.springframework.web.bind.MethodArgumentNotValidException
   */
  @PutMapping("/equipepostes/{id}")
  public ResponseEntity<EquipePosteDTO> updateEquipePoste(@PathVariable Integer id, @Valid @RequestBody EquipePosteDTO equipeposteDTO) throws MethodArgumentNotValidException {
    log.debug("Request to update EquipePoste: {}",id);
    equipeposteDTO.setId(id);
    EquipePosteDTO result =equipeposteService.update(equipeposteDTO);
    return ResponseEntity.ok().body(result);
  }

  /**
   * GET /equipepostes/{id} : get the "id" equipeposte.
   *
   * @param id the id of the equipeposte to retrieve
   * @return the ResponseEntity with status 200 (OK) and with body of equipeposte, or with status 404 (Not Found)
   */
  @GetMapping("/equipepostes/{id}")
  public ResponseEntity<EquipePosteDTO> getEquipePoste(@PathVariable Integer id) {
    log.debug("Request to get EquipePoste: {}",id);
    EquipePosteDTO dto = equipeposteService.findOne(id);
    RestPreconditions.checkFound(dto, "equipeposte.NotFound");
    return ResponseEntity.ok().body(dto);
  }

  /**
   * GET /equipepostes : get all the equipepostes.
   *
   * @return the ResponseEntity with status 200 (OK) and the list of equipepostes in body
   */
  @GetMapping("/equipepostes")
  public Collection<EquipePosteDTO> getAllEquipePostes() {
    log.debug("Request to get all  EquipePostes : {}");
    return equipeposteService.findAll();
  }

  /**
   * DELETE  /equipepostes/{id} : delete the "id" equipeposte.
   *
   * @param id the id of the equipeposte to delete
   * @return the ResponseEntity with status 200 (OK)
   */
  @DeleteMapping("/equipepostes/{id}")
  public ResponseEntity<Void> deleteEquipePoste(@PathVariable Integer id) {
    log.debug("Request to delete EquipePoste: {}",id);
    equipeposteService.delete(id);
    return ResponseEntity.ok().build();
  }
}

