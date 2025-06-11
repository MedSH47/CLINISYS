package com.csys.template.web.rest.ressource;

import com.csys.template.domain.EquipePosteutilisateurPK;
import com.csys.template.dtoRequest.EquipePosteutilisateurRequestDTO;
import com.csys.template.dtoResponse.EquipePosteutilisateurResponseDTO;
import com.csys.template.service.EquipePosteutilisateurService;
import com.csys.template.util.RestPreconditions;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class EquipePosteutilisateurResource {

    private final Logger log = LoggerFactory.getLogger(EquipePosteutilisateurResource.class);
    private final EquipePosteutilisateurService equipePosteutilisateurService;

    public EquipePosteutilisateurResource(EquipePosteutilisateurService equipePosteutilisateurService) {
        this.equipePosteutilisateurService = equipePosteutilisateurService;
    }

    @PostMapping("/equipe-poste-utilisateurs")
    public ResponseEntity<EquipePosteutilisateurResponseDTO> createEquipePosteutilisateur(
            @Valid @RequestBody EquipePosteutilisateurRequestDTO requestDTO) throws URISyntaxException {
        log.debug("REST request to save EquipePosteutilisateur : {}", requestDTO);
        EquipePosteutilisateurResponseDTO result = equipePosteutilisateurService.save(requestDTO);
        String locationUri = String.format("/api/equipe-poste-utilisateurs/%d/%d/%d",
                result.getPoste(), result.getUtilisateur(), result.getEquipe());
        return ResponseEntity.created(new URI(locationUri)).body(result);
    }

    @GetMapping("/equipe-poste-utilisateurs/{idPoste}/{idUtilisateur}/{idEquipe}")
    public ResponseEntity<EquipePosteutilisateurResponseDTO> getEquipePosteutilisateur(
            @PathVariable Integer idPoste,
            @PathVariable Integer idUtilisateur,
            @PathVariable Integer idEquipe) {
        log.debug("Request to get EquipePosteutilisateur: idPoste={}, idUtilisateur={}, idEquipe={}",
                idPoste, idUtilisateur, idEquipe);
        EquipePosteutilisateurPK id = new EquipePosteutilisateurPK(idPoste, idUtilisateur, idEquipe);
        EquipePosteutilisateurResponseDTO dto = equipePosteutilisateurService.findOne(id);
        RestPreconditions.checkFound(dto, "EquipePosteutilisateur.NotFound");
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/equipe-poste-utilisateurs")
    public List<EquipePosteutilisateurResponseDTO> getAllEquipePosteutilisateurs() {
        log.debug("Request to get all EquipePosteutilisateurs");
        return equipePosteutilisateurService.findAll();
    }

    @DeleteMapping("/equipe-poste-utilisateurs/{idPoste}/{idUtilisateur}/{idEquipe}")
    public ResponseEntity<Void> deleteEquipePosteutilisateur(
            @PathVariable Integer idPoste,
            @PathVariable Integer idUtilisateur,
            @PathVariable Integer idEquipe) {
        log.debug("Request to delete EquipePosteutilisateur: idPoste={}, idUtilisateur={}, idEquipe={}",
                idPoste, idUtilisateur, idEquipe);
        EquipePosteutilisateurPK id = new EquipePosteutilisateurPK(idPoste, idUtilisateur, idEquipe);
        equipePosteutilisateurService.delete(id);
        return ResponseEntity.noContent().build();
    }
}