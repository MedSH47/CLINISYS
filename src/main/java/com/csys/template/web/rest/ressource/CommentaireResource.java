package com.csys.template.web.rest.ressource;

import com.csys.template.dto.CommentaireDTO;
import com.csys.template.service.CommentaireService;
import java.lang.Integer;
import java.lang.String;
import java.lang.Void;
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
 * REST controller for managing Commentaire.
 */
@RestController
@RequestMapping("/api")
public class CommentaireResource {
  private static final String ENTITY_NAME = "commentaire";

  private final CommentaireService commentaireService;

  private final Logger log = LoggerFactory.getLogger(CommentaireService.class);

  public CommentaireResource(CommentaireService commentaireService) {
    this.commentaireService=commentaireService;
  }

  /**
   * POST  /commentaires : Create a new commentaire.
   *
   * @param commentaireDTO
   * @param bindingResult
   * @return the ResponseEntity with status 201 (Created) and with body the new commentaire, or with status 400 (Bad Request) if the commentaire has already an ID
   * @throws URISyntaxException if the Location URI syntax is incorrect
   * @throws org.springframework.web.bind.MethodArgumentNotValidException
   */
  @PostMapping("/commentaires")
  public ResponseEntity<CommentaireDTO> createCommentaire(@Valid @RequestBody CommentaireDTO commentaireDTO, BindingResult bindingResult) throws URISyntaxException, MethodArgumentNotValidException {
    log.debug("REST request to save Commentaire : {}", commentaireDTO);
    if ( commentaireDTO.getId() != null) {
      bindingResult.addError( new FieldError("CommentaireDTO","id","POST method does not accepte "+ENTITY_NAME+" with code"));
      throw new MethodArgumentNotValidException(null, bindingResult);
    }
    if (bindingResult.hasErrors()) {
      throw new MethodArgumentNotValidException(null, bindingResult);
    }
    CommentaireDTO result = commentaireService.save(commentaireDTO);
    return ResponseEntity.created( new URI("/api/commentaires/"+ result.getId())).body(result);
  }

  /**
   * PUT  /commentaires : Updates an existing commentaire.
   *
   * @param id
   * @param commentaireDTO the commentaire to update
   * @return the ResponseEntity with status 200 (OK) and with body the updated commentaire,
   * or with status 400 (Bad Request) if the commentaire is not valid,
   * or with status 500 (Internal Server Error) if the commentaire couldn't be updated
   * @throws org.springframework.web.bind.MethodArgumentNotValidException
   */
  @PutMapping("/commentaires/{id}")
  public ResponseEntity<CommentaireDTO> updateCommentaire(@PathVariable Integer id, @Valid @RequestBody CommentaireDTO commentaireDTO) throws MethodArgumentNotValidException {
    log.debug("Request to update Commentaire: {}",id);
    commentaireDTO.setId(id);
    CommentaireDTO result =commentaireService.update(commentaireDTO);
    return ResponseEntity.ok().body(result);
  }

  /**
   * GET /commentaires/{id} : get the "id" commentaire.
   *
   * @param id the id of the commentaire to retrieve
   * @return the ResponseEntity with status 200 (OK) and with body of commentaire, or with status 404 (Not Found)
   */
  @GetMapping("/commentaires/{id}")
  public ResponseEntity<CommentaireDTO> getCommentaire(@PathVariable Integer id) {
    log.debug("Request to get Commentaire: {}",id);
    CommentaireDTO dto = commentaireService.findOne(id);
    RestPreconditions.checkFound(dto, "commentaire.NotFound");
    return ResponseEntity.ok().body(dto);
  }

  /**
   * GET /commentaires : get all the commentaires.
   *
   * @return the ResponseEntity with status 200 (OK) and the list of commentaires in body
   */
  @GetMapping("/commentaires")
  public Collection<CommentaireDTO> getAllCommentaires() {
    log.debug("Request to get all  Commentaires : {}");
    return commentaireService.findAll();
  }

  /**
   * DELETE  /commentaires/{id} : delete the "id" commentaire.
   *
   * @param id the id of the commentaire to delete
   * @return the ResponseEntity with status 200 (OK)
   */
  @DeleteMapping("/commentaires/{id}")
  public ResponseEntity<Void> deleteCommentaire(@PathVariable Integer id) {
    log.debug("Request to delete Commentaire: {}",id);
    commentaireService.delete(id);
    return ResponseEntity.ok().build();
  }
}

