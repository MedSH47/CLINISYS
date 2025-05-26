package com.csys.template.web.rest.ressource;

import com.csys.template.dto.AvancementDTO;
import com.csys.template.service.AvancementService;
import com.csys.template.util.RestPreconditions;

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
 * REST controller for managing Avancement.
 */
@RestController
@RequestMapping("/api")
public class AvancementResource {
  private static final String ENTITY_NAME = "avancement";

  private final AvancementService avancementService;

  private final Logger log = LoggerFactory.getLogger(AvancementService.class);

  public AvancementResource(AvancementService avancementService) {
    this.avancementService=avancementService;
  }

  /**
   * POST  /avancements : Create a new avancement.
   *
   * @param avancementDTO
   * @param bindingResult
   * @return the ResponseEntity with status 201 (Created) and with body the new avancement, or with status 400 (Bad Request) if the avancement has already an ID
   * @throws URISyntaxException if the Location URI syntax is incorrect
   * @throws org.springframework.web.bind.MethodArgumentNotValidException
   */
  @PostMapping("/avancements")
  public ResponseEntity<AvancementDTO> createAvancement(@Valid @RequestBody AvancementDTO avancementDTO, BindingResult bindingResult) throws URISyntaxException, MethodArgumentNotValidException {
    log.debug("REST request to save Avancement : {}", avancementDTO);
    if ( avancementDTO.getId() != null) {
      bindingResult.addError( new FieldError("AvancementDTO","id","POST method does not accepte "+ENTITY_NAME+" with code"));
      throw new MethodArgumentNotValidException(null, bindingResult);
    }
    if (bindingResult.hasErrors()) {
      throw new MethodArgumentNotValidException(null, bindingResult);
    }
    AvancementDTO result = avancementService.save(avancementDTO);
    return ResponseEntity.created( new URI("/api/avancements/"+ result.getId())).body(result);
  }

  /**
   * PUT  /avancements : Updates an existing avancement.
   *
   * @param id
   * @param avancementDTO the avancement to update
   * @return the ResponseEntity with status 200 (OK) and with body the updated avancement,
   * or with status 400 (Bad Request) if the avancement is not valid,
   * or with status 500 (Internal Server Error) if the avancement couldn't be updated
   * @throws org.springframework.web.bind.MethodArgumentNotValidException
   */
  @PutMapping("/avancements/{id}")
  public ResponseEntity<AvancementDTO> updateAvancement(@PathVariable Integer id, @Valid @RequestBody AvancementDTO avancementDTO) throws MethodArgumentNotValidException {
    log.debug("Request to update Avancement: {}",id);
    avancementDTO.setId(id);
    AvancementDTO result =avancementService.update(avancementDTO);
    return ResponseEntity.ok().body(result);
  }

  /**
   * GET /avancements/{id} : get the "id" avancement.
   *
   * @param id the id of the avancement to retrieve
   * @return the ResponseEntity with status 200 (OK) and with body of avancement, or with status 404 (Not Found)
   */
  @GetMapping("/avancements/{id}")
  public ResponseEntity<AvancementDTO> getAvancement(@PathVariable Integer id) {
    log.debug("Request to get Avancement: {}",id);
    AvancementDTO dto = avancementService.findOne(id);
    RestPreconditions.checkFound(dto, "avancement.NotFound");
    return ResponseEntity.ok().body(dto);
  }

  /**
   * GET /avancements : get all the avancements.
   *
   * @return the ResponseEntity with status 200 (OK) and the list of avancements in body
   */
  @GetMapping("/avancements")
  public Collection<AvancementDTO> getAllAvancements() {
    log.debug("Request to get all  Avancements : {}");
    return avancementService.findAll();
  }

  /**
   * DELETE  /avancements/{id} : delete the "id" avancement.
   *
   * @param id the id of the avancement to delete
   * @return the ResponseEntity with status 200 (OK)
   */
  @DeleteMapping("/avancements/{id}")
  public ResponseEntity<Void> deleteAvancement(@PathVariable Integer id) {
    log.debug("Request to delete Avancement: {}",id);
    avancementService.delete(id);
    return ResponseEntity.ok().build();
  }
}

