package com.csys.template.web.rest.ressource;
import com.csys.template.util.RestPreconditions;

import com.csys.template.dto.FichiersjointesDTO;
import com.csys.template.service.FichiersjointesService;
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
 * REST controller for managing Fichiersjointes.
 */
@RestController
@RequestMapping("/api")
public class FichiersjointesResource {
  private static final String ENTITY_NAME = "fichiersjointes";

  private final FichiersjointesService fichiersjointesService;

  private final Logger log = LoggerFactory.getLogger(FichiersjointesService.class);

  public FichiersjointesResource(FichiersjointesService fichiersjointesService) {
    this.fichiersjointesService=fichiersjointesService;
  }

  /**
   * POST  /fichiersjointess : Create a new fichiersjointes.
   *
   * @param fichiersjointesDTO
   * @param bindingResult
   * @return the ResponseEntity with status 201 (Created) and with body the new fichiersjointes, or with status 400 (Bad Request) if the fichiersjointes has already an ID
   * @throws URISyntaxException if the Location URI syntax is incorrect
   * @throws org.springframework.web.bind.MethodArgumentNotValidException
   */
  @PostMapping("/fichiersjointess")
  public ResponseEntity<FichiersjointesDTO> createFichiersjointes(@Valid @RequestBody FichiersjointesDTO fichiersjointesDTO, BindingResult bindingResult) throws URISyntaxException, MethodArgumentNotValidException {
    log.debug("REST request to save Fichiersjointes : {}", fichiersjointesDTO);
    if ( fichiersjointesDTO.getId() != null) {
      bindingResult.addError( new FieldError("FichiersjointesDTO","id","POST method does not accepte "+ENTITY_NAME+" with code"));
      throw new MethodArgumentNotValidException(null, bindingResult);
    }
    if (bindingResult.hasErrors()) {
      throw new MethodArgumentNotValidException(null, bindingResult);
    }
    FichiersjointesDTO result = fichiersjointesService.save(fichiersjointesDTO);
    return ResponseEntity.created( new URI("/api/fichiersjointess/"+ result.getId())).body(result);
  }

  /**
   * PUT  /fichiersjointess : Updates an existing fichiersjointes.
   *
   * @param id
   * @param fichiersjointesDTO the fichiersjointes to update
   * @return the ResponseEntity with status 200 (OK) and with body the updated fichiersjointes,
   * or with status 400 (Bad Request) if the fichiersjointes is not valid,
   * or with status 500 (Internal Server Error) if the fichiersjointes couldn't be updated
   * @throws org.springframework.web.bind.MethodArgumentNotValidException
   */
  @PutMapping("/fichiersjointess/{id}")
  public ResponseEntity<FichiersjointesDTO> updateFichiersjointes(@PathVariable Integer id, @Valid @RequestBody FichiersjointesDTO fichiersjointesDTO) throws MethodArgumentNotValidException {
    log.debug("Request to update Fichiersjointes: {}",id);
    fichiersjointesDTO.setId(id);
    FichiersjointesDTO result =fichiersjointesService.update(fichiersjointesDTO);
    return ResponseEntity.ok().body(result);
  }

  /**
   * GET /fichiersjointess/{id} : get the "id" fichiersjointes.
   *
   * @param id the id of the fichiersjointes to retrieve
   * @return the ResponseEntity with status 200 (OK) and with body of fichiersjointes, or with status 404 (Not Found)
   */
  @GetMapping("/fichiersjointess/{id}")
  public ResponseEntity<FichiersjointesDTO> getFichiersjointes(@PathVariable Integer id) {
    log.debug("Request to get Fichiersjointes: {}",id);
    FichiersjointesDTO dto = fichiersjointesService.findOne(id);
    RestPreconditions.checkFound(dto, "fichiersjointes.NotFound");
    return ResponseEntity.ok().body(dto);
  }

  /**
   * GET /fichiersjointess : get all the fichiersjointess.
   *
   * @return the ResponseEntity with status 200 (OK) and the list of fichiersjointess in body
   */
  @GetMapping("/fichiersjointess")
  public Collection<FichiersjointesDTO> getAllFichiersjointess() {
    log.debug("Request to get all  Fichiersjointess : {}");
    return fichiersjointesService.findAll();
  }

  /**
   * DELETE  /fichiersjointess/{id} : delete the "id" fichiersjointes.
   *
   * @param id the id of the fichiersjointes to delete
   * @return the ResponseEntity with status 200 (OK)
   */
  @DeleteMapping("/fichiersjointess/{id}")
  public ResponseEntity<Void> deleteFichiersjointes(@PathVariable Integer id) {
    log.debug("Request to delete Fichiersjointes: {}",id);
    fichiersjointesService.delete(id);
    return ResponseEntity.ok().build();
  }
}

