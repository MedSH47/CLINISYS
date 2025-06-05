package com.csys.template.web.rest.ressource;

import com.csys.template.dto.UtilisateurDTO;
import com.csys.template.service.UtilisateurService;

import java.io.IOException;
import java.lang.Integer;
import java.lang.String;
import java.lang.Void;
import java.net.URI;
import java.net.URISyntaxException;
import com.csys.template.util.RestPreconditions;

import java.util.Collection;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class UtilisateurResource {

  private final UtilisateurService utilisateurService;

  private final Logger log = LoggerFactory.getLogger(UtilisateurService.class);

  public UtilisateurResource(UtilisateurService utilisateurService) {
    this.utilisateurService=utilisateurService;
  }


   @PostMapping(
    value = "/utilisateurs",
    consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE }
  )
  public ResponseEntity<UtilisateurDTO> createUtilisateur(
      @RequestPart("utilisateur") @Valid UtilisateurDTO utilisateurDTO,
      @RequestPart(value = "file", required = false) MultipartFile file,
      BindingResult bindingResult
  ) throws URISyntaxException, MethodArgumentNotValidException, IOException {
    log.debug("REST request to save Utilisateur : {}", utilisateurDTO);
    if (utilisateurDTO.getId() != null) {
      bindingResult.addError(new FieldError("UtilisateurDTO","id","POST does not accept ID"));
      throw new MethodArgumentNotValidException(null, bindingResult);
    }
    
    //  ←── ici on gère la photo
    if (file != null && !file.isEmpty()) {
      utilisateurDTO.setPhoto(file.getBytes());
    }
    UtilisateurDTO result = utilisateurService.save(utilisateurDTO);
    return ResponseEntity
        .created(new URI("/api/utilisateurs/" + result.getId()))
        .body(result);
  }


 @PutMapping(
    value = "/utilisateurs/{id}",
    consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE }
  )
  public ResponseEntity<UtilisateurDTO> updateUtilisateur(
      @PathVariable Integer id,
      @RequestPart("utilisateur") @Valid UtilisateurDTO utilisateurDTO,
      @RequestPart(value = "file", required = false) MultipartFile file
  ) throws MethodArgumentNotValidException, IOException {
    // Log the initial DTO received from the JSON part
    log.debug("REST request to update Utilisateur ID: {}", id);
    log.debug("UtilisateurDTO from @RequestPart('utilisateur'): {}", utilisateurDTO);
    // Note: At this point, utilisateurDTO.getPhoto() will likely be null,
    // as the 'photo' field is not expected in the JSON part from your React app.

    utilisateurDTO.setId(id);

    if (file != null && !file.isEmpty()) {
      log.info("New file provided for update. Name: '{}', Size: {} bytes", file.getOriginalFilename(), file.getSize());
      utilisateurDTO.setPhoto(file.getBytes());
      // Now, utilisateurDTO.getPhoto() should contain the bytes of the new image.
    } else {
      log.info("No new file provided for update for Utilisateur ID: {}", id);
      // utilisateurDTO.getPhoto() remains null if no new file is uploaded.
    }

    // Log the DTO just before sending it to the service
    log.debug("UtilisateurDTO being passed to service update method: ID={}, HasNewPhoto={}",
        utilisateurDTO.getId(), (utilisateurDTO.getPhoto() != null && utilisateurDTO.getPhoto().length > 0));
    if (utilisateurDTO.getPhoto() != null) {
        log.debug("Photo byte array length being passed to service: {}", utilisateurDTO.getPhoto().length);
    }


    UtilisateurDTO result = utilisateurService.update(utilisateurDTO);
    return ResponseEntity.ok().body(result);
  }
  /**
   * GET /utilisateurs/{id} : get the "id" utilisateur.
   *
   * @param id the id of the utilisateur to retrieve
   * @return the ResponseEntity with status 200 (OK) and with body of utilisateur, or with status 404 (Not Found)
   */
  @GetMapping("/utilisateurs/{id}")
  public ResponseEntity<UtilisateurDTO> getUtilisateur(@PathVariable Integer id) {
    log.debug("Request to get Utilisateur: {}",id);
    UtilisateurDTO dto = utilisateurService.findOne(id);
    RestPreconditions.checkFound(dto, "utilisateur.NotFound");
    return ResponseEntity.ok().body(dto);
  }

  /**
   * GET /utilisateurs : get all the utilisateurs.
   *
   * @return the ResponseEntity with status 200 (OK) and the list of utilisateurs in body
   */
  @GetMapping("/utilisateurs")
  public Collection<UtilisateurDTO> getAllUtilisateurs() {
    log.debug("Request to get all  Utilisateurs : {}");
    return utilisateurService.findAll();
  }

  /**
   * DELETE  /utilisateurs/{id} : delete the "id" utilisateur.
   *
   * @param id the id of the utilisateur to delete
   * @return the ResponseEntity with status 200 (OK)
   */
  @DeleteMapping("/utilisateurs/{id}")
  public ResponseEntity<Void> deleteUtilisateur(@PathVariable Integer id) {
    log.debug("Request to delete Utilisateur: {}",id);
    utilisateurService.delete(id);
    return ResponseEntity.ok().build();
  }
  @GetMapping("/utilisateurs/findbyemail/{email}")
  public UtilisateurDTO getMethodName(@PathVariable String email) {
      return utilisateurService.findByemail(email);
  }
  
}

