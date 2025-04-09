package com.csys.template.web.rest.ressource;

import com.csys.template.dto.PosteDTO;
import com.csys.template.service.PosteService;
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
 * REST controller for managing Poste.
 */
@RestController
@RequestMapping("/api")
public class PosteResource {
  private static final String ENTITY_NAME = "poste";

  private final PosteService posteService;

  private final Logger log = LoggerFactory.getLogger(PosteService.class);

  public PosteResource(PosteService posteService) {
    this.posteService=posteService;
  }

  /**
   * POST  /postes : Create a new poste.
   *
   * @param posteDTO
   * @param bindingResult
   * @return the ResponseEntity with status 201 (Created) and with body the new poste, or with status 400 (Bad Request) if the poste has already an ID
   * @throws URISyntaxException if the Location URI syntax is incorrect
   * @throws org.springframework.web.bind.MethodArgumentNotValidException
   */
  @PostMapping("/postes")
  public ResponseEntity<PosteDTO> createPoste(@Valid @RequestBody PosteDTO posteDTO, BindingResult bindingResult) throws URISyntaxException, MethodArgumentNotValidException {
    log.debug("REST request to save Poste : {}", posteDTO);
    if ( posteDTO.getId() != null) {
      bindingResult.addError( new FieldError("PosteDTO","id","POST method does not accepte "+ENTITY_NAME+" with code"));
      throw new MethodArgumentNotValidException(null, bindingResult);
    }
    if (bindingResult.hasErrors()) {
      throw new MethodArgumentNotValidException(null, bindingResult);
    }
    PosteDTO result = posteService.save(posteDTO);
    return ResponseEntity.created( new URI("/api/postes/"+ result.getId())).body(result);
  }

  /**
   * PUT  /postes : Updates an existing poste.
   *
   * @param id
   * @param posteDTO the poste to update
   * @return the ResponseEntity with status 200 (OK) and with body the updated poste,
   * or with status 400 (Bad Request) if the poste is not valid,
   * or with status 500 (Internal Server Error) if the poste couldn't be updated
   * @throws org.springframework.web.bind.MethodArgumentNotValidException
   */
  @PutMapping("/postes/{id}")
  public ResponseEntity<PosteDTO> updatePoste(@PathVariable Integer id, @Valid @RequestBody PosteDTO posteDTO) throws MethodArgumentNotValidException {
    log.debug("Request to update Poste: {}",id);
    posteDTO.setId(id);
    PosteDTO result =posteService.update(posteDTO);
    return ResponseEntity.ok().body(result);
  }

  /**
   * GET /postes/{id} : get the "id" poste.
   *
   * @param id the id of the poste to retrieve
   * @return the ResponseEntity with status 200 (OK) and with body of poste, or with status 404 (Not Found)
   */
  @GetMapping("/postes/{id}")
  public ResponseEntity<PosteDTO> getPoste(@PathVariable Integer id) {
    log.debug("Request to get Poste: {}",id);
    PosteDTO dto = posteService.findOne(id);
    RestPreconditions.checkFound(dto, "poste.NotFound");
    return ResponseEntity.ok().body(dto);
  }

  /**
   * GET /postes : get all the postes.
   *
   * @return the ResponseEntity with status 200 (OK) and the list of postes in body
   */
  @GetMapping("/postes")
  public Collection<PosteDTO> getAllPostes() {
    log.debug("Request to get all  Postes : {}");
    return posteService.findAll();
  }

  /**
   * DELETE  /postes/{id} : delete the "id" poste.
   *
   * @param id the id of the poste to delete
   * @return the ResponseEntity with status 200 (OK)
   */
  @DeleteMapping("/postes/{id}")
  public ResponseEntity<Void> deletePoste(@PathVariable Integer id) {
    log.debug("Request to delete Poste: {}",id);
    posteService.delete(id);
    return ResponseEntity.ok().build();
  }
}

