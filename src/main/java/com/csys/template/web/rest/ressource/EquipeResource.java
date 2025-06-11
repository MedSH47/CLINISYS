package com.csys.template.web.rest.ressource;

import com.csys.template.dtoRequest.EquipeRequestDTO;
import com.csys.template.dtoResponse.EquipeResponseDTO;
import com.csys.template.service.EquipeService;
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
public class EquipeResource {
    private final Logger log = LoggerFactory.getLogger(EquipeResource.class);
    private final EquipeService equipeService;

    public EquipeResource(EquipeService equipeService) {
        this.equipeService = equipeService;
    }

    @PostMapping("/equipes")
    public ResponseEntity<EquipeResponseDTO> createEquipe(@Valid @RequestBody EquipeRequestDTO equipeRequestDTO) throws URISyntaxException {
        log.debug("REST request to save Equipe : {}", equipeRequestDTO);
        EquipeResponseDTO result = equipeService.save(equipeRequestDTO);
        return ResponseEntity.created(new URI("/api/equipes/" + result.getId())).body(result);
    }

    @PutMapping("/equipes/{id}")
    public ResponseEntity<EquipeResponseDTO> updateEquipe(@PathVariable Integer id, @Valid @RequestBody EquipeRequestDTO equipeRequestDTO) {
        log.debug("Request to update Equipe: {}", id);
        EquipeResponseDTO result = equipeService.update(id, equipeRequestDTO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/equipes/{id}")
    public ResponseEntity<EquipeResponseDTO> getEquipe(@PathVariable Integer id) {
        log.debug("Request to get Equipe: {}", id);
        EquipeResponseDTO dto = equipeService.findOne(id);
        RestPreconditions.checkFound(dto, "equipe.NotFound");
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/equipes")
    public List<EquipeResponseDTO> getAllEquipes() {
        log.debug("Request to get all Equipes");
        return equipeService.findAll();
    }

    @DeleteMapping("/equipes/{id}")
    public ResponseEntity<Void> deleteEquipe(@PathVariable Integer id) {
        log.debug("Request to delete Equipe: {}", id);
        equipeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}