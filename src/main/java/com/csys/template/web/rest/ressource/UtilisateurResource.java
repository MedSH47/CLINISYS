package com.csys.template.web.rest.ressource;
import com.csys.template.util.RestPreconditions;
import com.csys.template.domain.Utilisateur;
import com.csys.template.dto.UtilisateurDTO;
import com.csys.template.service.UtilisateurService;
import java.lang.Integer;
import java.lang.String;
import java.lang.Void;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

import javax.imageio.IIOException;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import org.springframework.web.bind.annotation.RequestParam;


/**
 * REST controller for managing Utilisateur.
 */
@RestController
@RequestMapping("/api")
public class UtilisateurResource {
  private static final String ENTITY_NAME = "utilisateur";

  private final UtilisateurService utilisateurService;

  private final PasswordEncoder passwordEncoder;

  private final Logger log = LoggerFactory.getLogger(UtilisateurService.class);

  public UtilisateurResource(UtilisateurService utilisateurService,PasswordEncoder passwordEncoder) {
    this.utilisateurService=utilisateurService;
    this.passwordEncoder=passwordEncoder;
  }


  @PostMapping("/utilisateurs")
  public ResponseEntity<?> createUtilisateur( @RequestBody Utilisateur utilisateurDTO) throws IIOException {
    
    return utilisateurService.createUtilisateur(utilisateurDTO);
    
  }

  
  @PutMapping("/utilisateurs/{id}")
  public ResponseEntity<UtilisateurDTO> updateUtilisateur(@PathVariable Integer id, @Valid @RequestBody UtilisateurDTO utilisateurDTO) throws MethodArgumentNotValidException {
    log.debug("Request to update Utilisateur: {}",id);
    utilisateurDTO.setId(id);
    UtilisateurDTO result =utilisateurService.update(utilisateurDTO);
    return ResponseEntity.ok().body(result);
  }

  @GetMapping("/utilisateurs/{id}")
  public ResponseEntity<UtilisateurDTO> getUtilisateur(@PathVariable Integer id) {
    log.debug("Request to get Utilisateur: {}",id);
    UtilisateurDTO dto = utilisateurService.findOne(id);
    RestPreconditions.checkFound(dto, "utilisateur.NotFound");
    return ResponseEntity.ok().body(dto);
  }

  @GetMapping("/utilisateurs")
  public Collection<UtilisateurDTO> getAllUtilisateurs() {
    log.debug("Request to get all  Utilisateurs : {}");
    return utilisateurService.findAll();
  }


  @DeleteMapping("/utilisateurs/{id}")
  public ResponseEntity<Void> deleteUtilisateur(@PathVariable Integer id) {
    log.debug("Request to delete Utilisateur: {}",id);
    utilisateurService.delete(id);
    return ResponseEntity.ok().build();
  }
  @GetMapping("/test")
  public boolean getMethodName(@RequestBody Utilisateur object ) {
     Utilisateur user = utilisateurService.findByemail(object.getEmail());
     return passwordEncoder.matches(object.getMotDePasse(),user.getMotDePasse() );
     
     
  }
  @GetMapping("/gethash/{param}")
  public String getMethodName(@PathVariable String param) {
      return passwordEncoder.encode(param);
  }
  
  
  
}

