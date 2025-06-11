package com.csys.template.web.rest.ressource;

import com.csys.template.dtoRequest.UtilisateurRequestDTO;
import com.csys.template.dtoResponse.UtilisateurResponseDTO;
import com.csys.template.service.UtilisateurService;
import com.csys.template.util.RestPreconditions;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class UtilisateurResource {

    private final Logger log = LoggerFactory.getLogger(UtilisateurResource.class);
    private final UtilisateurService utilisateurService;

    public UtilisateurResource(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @PostMapping(value = "/utilisateurs", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UtilisateurResponseDTO> createUtilisateur(
            @RequestPart("utilisateur") @Valid UtilisateurRequestDTO utilisateurRequestDTO,
            @RequestPart(value = "photo", required = false) MultipartFile photo) throws URISyntaxException, IOException {
        log.debug("REST request to save Utilisateur : {}", utilisateurRequestDTO);
        
        if (photo != null && !photo.isEmpty()) {
            utilisateurRequestDTO.setPhoto(photo.getBytes());
        }
        
        UtilisateurResponseDTO result = utilisateurService.save(utilisateurRequestDTO);
        return ResponseEntity.created(new URI("/api/utilisateurs/" + result.getId())).body(result);
    }

    @PutMapping(value = "/utilisateurs/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UtilisateurResponseDTO> updateUtilisateur(
            @PathVariable Integer id,
            @RequestPart("utilisateur") @Valid UtilisateurRequestDTO utilisateurRequestDTO,
            @RequestPart(value = "photo", required = false) MultipartFile photoFile) throws IOException {
        log.debug("REST request to update Utilisateur ID: {}", id);

        byte[] photoBytes = (photoFile != null && !photoFile.isEmpty()) ? photoFile.getBytes() : null;

        UtilisateurResponseDTO result = utilisateurService.update(id, utilisateurRequestDTO, photoBytes);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/utilisateurs/{id}")
    public ResponseEntity<UtilisateurResponseDTO> getUtilisateur(@PathVariable Integer id) {
        log.debug("Request to get Utilisateur: {}", id);
        UtilisateurResponseDTO dto = utilisateurService.findOne(id);
        RestPreconditions.checkFound(dto, "utilisateur.NotFound");
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/utilisateurs")
    public Collection<UtilisateurResponseDTO> getAllUtilisateurs() {
        log.debug("Request to get all Utilisateurs");
        return utilisateurService.findAll();
    }
    
    @GetMapping("/utilisateurs/find-by-login/{login}")
    public ResponseEntity<UtilisateurResponseDTO> getUtilisateurByLogin(@PathVariable String login) {
        log.debug("Request to get Utilisateur by login: {}", login);
        UtilisateurResponseDTO dto = utilisateurService.findByLogin(login);
        RestPreconditions.checkFound(dto, "utilisateur.NotFound");
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/utilisateurs/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Integer id) {
        log.debug("Request to delete Utilisateur: {}", id);
        utilisateurService.delete(id);
        return ResponseEntity.noContent().build();
    }
}