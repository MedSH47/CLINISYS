package com.csys.template.web.rest.ressource;

import com.csys.template.dto.PosteDTO;
import com.csys.template.service.PosteService;
import java.lang.Integer;
import java.lang.String;
import java.lang.Void;
import java.net.URI;
import java.net.URISyntaxException;
import com.csys.template.util.RestPreconditions;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PosteResource {

  private final PosteService posteService;

  private final Logger log = LoggerFactory.getLogger(PosteService.class);

  public PosteResource(PosteService posteService) {
    this.posteService=posteService;
  }

 
  @PostMapping("/postes")
  public ResponseEntity<PosteDTO> createPoste(@Valid @RequestBody PosteDTO posteDTO,@RequestParam String user, BindingResult bindingResult) throws URISyntaxException, MethodArgumentNotValidException {
   
    if (bindingResult.hasErrors()) {
      throw new MethodArgumentNotValidException(null, bindingResult);
    }
    PosteDTO result = posteService.save(posteDTO,user);
    return ResponseEntity.created( new URI("/api/postes/")).body(result);
  }

 
  @PutMapping("/postes/{id}")
  public ResponseEntity<PosteDTO> updatePoste(@PathVariable Integer id,@RequestParam String user, @Valid @RequestBody PosteDTO posteDTO) throws MethodArgumentNotValidException {
    log.debug("Request to update Poste: {}",id);
    posteDTO.setId(id);
    PosteDTO result =posteService.update(posteDTO,user);
    return ResponseEntity.ok().body(result);
  }

  @GetMapping("/postes/{id}")
  public ResponseEntity<PosteDTO> getPoste(@PathVariable Integer id) {
    log.debug("Request to get Poste: {}",id);
    PosteDTO dto = posteService.findOne(id);
    RestPreconditions.checkFound(dto, "poste.NotFound");
    return ResponseEntity.ok().body(dto);
  }

  @GetMapping("/postes")
  public List<PosteDTO> getAllPostes(@RequestParam(required = false) Boolean [] actifs) {
    log.debug("Request to get all  Postes : {}");
    return posteService.findAll(actifs);
  }

  @DeleteMapping("/postes/{id}")
  public ResponseEntity<Void> deletePoste(@PathVariable Integer id) {
    log.debug("Request to delete Poste: {}",id);
    posteService.delete(id);
    return ResponseEntity.ok().build();
  }
}

