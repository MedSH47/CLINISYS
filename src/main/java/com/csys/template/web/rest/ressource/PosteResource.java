package com.csys.template.web.rest.ressource;

import com.csys.template.dtoRequest.PosteRequestDTO;
import com.csys.template.dtoResponse.PosteResponseDTO;
import com.csys.template.service.PosteService;
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
public class PosteResource {

    private final Logger log = LoggerFactory.getLogger(PosteResource.class);
    private final PosteService posteService;

    public PosteResource(PosteService posteService) {
        this.posteService = posteService;
    }

    @PostMapping("/postes")
    public ResponseEntity<PosteResponseDTO> createPoste(@Valid @RequestBody PosteRequestDTO posteRequestDTO) throws URISyntaxException {
        log.debug("REST request to save Poste: {}", posteRequestDTO);
        PosteResponseDTO result = posteService.save(posteRequestDTO);
        return ResponseEntity.created(new URI("/api/postes/" + result.getId())).body(result);
    }

    @PutMapping("/postes/{id}")
    public ResponseEntity<PosteResponseDTO> updatePoste(@PathVariable Integer id, @Valid @RequestBody PosteRequestDTO posteRequestDTO) {
        log.debug("Request to update Poste: {}", id);
        PosteResponseDTO result = posteService.update(id, posteRequestDTO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/postes/{id}")
    public ResponseEntity<PosteResponseDTO> getPoste(@PathVariable Integer id) {
        log.debug("Request to get Poste: {}", id);
        PosteResponseDTO dto = posteService.findOne(id);
        RestPreconditions.checkFound(dto, "poste.NotFound");
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/postes")
    public List<PosteResponseDTO> getAllPostes(@RequestParam(required = false) Boolean[] actifs) {
        log.debug("Request to get all Postes");
        return posteService.findAll(actifs);
    }

    @DeleteMapping("/postes/{id}")
    public ResponseEntity<Void> deletePoste(@PathVariable Integer id) {
        log.debug("Request to delete Poste: {}", id);
        posteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}