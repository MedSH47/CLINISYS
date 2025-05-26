package com.csys.template.web.rest.ressource;

import com.csys.template.dto.DocumentJointesDTO;
import com.csys.template.service.DocumentJointesService;
import java.lang.Integer;
import java.lang.String;
import com.csys.template.util.RestPreconditions;

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
 * REST controller for managing DocumentJointes.
 */
@RestController
@RequestMapping("/api")
public class DocumentJointesResource {
  private static final String ENTITY_NAME = "documentjointes";

  private final DocumentJointesService documentjointesService;

  private final Logger log = LoggerFactory.getLogger(DocumentJointesService.class);

  public DocumentJointesResource(DocumentJointesService documentjointesService) {
    this.documentjointesService=documentjointesService;
  }

  /**
   * POST  /documentjointess : Create a new documentjointes.
   *
   * @param documentjointesDTO
   * @param bindingResult
   * @return the ResponseEntity with status 201 (Created) and with body the new documentjointes, or with status 400 (Bad Request) if the documentjointes has already an ID
   * @throws URISyntaxException if the Location URI syntax is incorrect
   * @throws org.springframework.web.bind.MethodArgumentNotValidException
   */
  @PostMapping("/documentjointess")
  public ResponseEntity<DocumentJointesDTO> createDocumentJointes(@Valid @RequestBody DocumentJointesDTO documentjointesDTO, BindingResult bindingResult) throws URISyntaxException, MethodArgumentNotValidException {
    log.debug("REST request to save DocumentJointes : {}", documentjointesDTO);
    if ( documentjointesDTO.getId() != null) {
      bindingResult.addError( new FieldError("DocumentJointesDTO","id","POST method does not accepte "+ENTITY_NAME+" with code"));
      throw new MethodArgumentNotValidException(null, bindingResult);
    }
    if (bindingResult.hasErrors()) {
      throw new MethodArgumentNotValidException(null, bindingResult);
    }
    DocumentJointesDTO result = documentjointesService.save(documentjointesDTO);
    return ResponseEntity.created( new URI("/api/documentjointess/"+ result.getId())).body(result);
  }

  /**
   * PUT  /documentjointess : Updates an existing documentjointes.
   *
   * @param id
   * @param documentjointesDTO the documentjointes to update
   * @return the ResponseEntity with status 200 (OK) and with body the updated documentjointes,
   * or with status 400 (Bad Request) if the documentjointes is not valid,
   * or with status 500 (Internal Server Error) if the documentjointes couldn't be updated
   * @throws org.springframework.web.bind.MethodArgumentNotValidException
   */
  @PutMapping("/documentjointess/{id}")
  public ResponseEntity<DocumentJointesDTO> updateDocumentJointes(@PathVariable Integer id, @Valid @RequestBody DocumentJointesDTO documentjointesDTO) throws MethodArgumentNotValidException {
    log.debug("Request to update DocumentJointes: {}",id);
    documentjointesDTO.setId(id);
    DocumentJointesDTO result =documentjointesService.update(documentjointesDTO);
    return ResponseEntity.ok().body(result);
  }

  /**
   * GET /documentjointess/{id} : get the "id" documentjointes.
   *
   * @param id the id of the documentjointes to retrieve
   * @return the ResponseEntity with status 200 (OK) and with body of documentjointes, or with status 404 (Not Found)
   */
  @GetMapping("/documentjointess/{id}")
  public ResponseEntity<DocumentJointesDTO> getDocumentJointes(@PathVariable Integer id) {
    log.debug("Request to get DocumentJointes: {}",id);
    DocumentJointesDTO dto = documentjointesService.findOne(id);
    RestPreconditions.checkFound(dto, "documentjointes.NotFound");
    return ResponseEntity.ok().body(dto);
  }

  /**
   * GET /documentjointess : get all the documentjointess.
   *
   * @return the ResponseEntity with status 200 (OK) and the list of documentjointess in body
   */
  @GetMapping("/documentjointess")
  public Collection<DocumentJointesDTO> getAllDocumentJointess() {
    log.debug("Request to get all  DocumentJointess : {}");
    return documentjointesService.findAll();
  }

  /**
   * DELETE  /documentjointess/{id} : delete the "id" documentjointes.
   *
   * @param id the id of the documentjointes to delete
   * @return the ResponseEntity with status 200 (OK)
   */
  @DeleteMapping("/documentjointess/{id}")
  public ResponseEntity<Void> deleteDocumentJointes(@PathVariable Integer id) {
    log.debug("Request to delete DocumentJointes: {}",id);
    documentjointesService.delete(id);
    return ResponseEntity.ok().build();
  }
}

