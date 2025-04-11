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
import java.util.List;

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
@RequestMapping("/api/postes")
public class PosteResource {
  private static final String ENTITY_NAME = "poste";

  private final PosteService posteService;

  private final Logger log = LoggerFactory.getLogger(PosteService.class);

  public PosteResource(PosteService posteService) {
    this.posteService=posteService;
  }

  @PostMapping
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

  
  @PutMapping("/{id}")
  public ResponseEntity<PosteDTO> updatePoste(@PathVariable Integer id, @Valid @RequestBody PosteDTO posteDTO) throws MethodArgumentNotValidException {
    log.debug("Request to update Poste: {}",id);
    posteDTO.setId(id);
    PosteDTO result =posteService.update(posteDTO);
    return ResponseEntity.ok().body(result);
  }

 
  @GetMapping("/{id}")
  public ResponseEntity<PosteDTO> getPoste(@PathVariable Integer id) {
    log.debug("Request to get Poste: {}",id);
    PosteDTO dto = posteService.findOne(id);
    RestPreconditions.checkFound(dto, "poste.NotFound");
    return ResponseEntity.ok().body(dto);
  }

  
  @GetMapping
  public List<PosteDTO> getAllPostes() {
    log.debug("Request to get all  Postes : {}");
    return posteService.findAll();
  }

  
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePoste(@PathVariable Integer id) {
    log.debug("Request to delete Poste: {}",id);
    posteService.delete(id);
    return ResponseEntity.ok().build();
  }
}

