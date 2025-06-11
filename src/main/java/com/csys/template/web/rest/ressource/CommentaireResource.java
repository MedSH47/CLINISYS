package com.csys.template.web.rest.ressource;

import com.csys.template.dtoRequest.CommentaireRequestDTO;
import com.csys.template.dtoResponse.CommentaireResponseDTO;
import com.csys.template.service.CommentaireService;
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
public class CommentaireResource {
    private final Logger log = LoggerFactory.getLogger(CommentaireResource.class);
    private final CommentaireService commentaireService;

    public CommentaireResource(CommentaireService commentaireService) {
        this.commentaireService = commentaireService;
    }

    @PostMapping("/commentaires")
    public ResponseEntity<CommentaireResponseDTO> createCommentaire(@Valid @RequestBody CommentaireRequestDTO commentaireRequestDTO) throws URISyntaxException {
        log.debug("REST request to save Commentaire : {}", commentaireRequestDTO);
        CommentaireResponseDTO result = commentaireService.save(commentaireRequestDTO);
        return ResponseEntity.created(new URI("/api/commentaires/" + result.getId())).body(result);
    }

    @PutMapping("/commentaires/{id}")
    public ResponseEntity<CommentaireResponseDTO> updateCommentaire(@PathVariable Integer id, @Valid @RequestBody CommentaireRequestDTO commentaireRequestDTO) {
        log.debug("REST request to update Commentaire : {}", id);
        CommentaireResponseDTO result = commentaireService.update(id, commentaireRequestDTO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/commentaires/{id}")
    public ResponseEntity<CommentaireResponseDTO> getCommentaire(@PathVariable Integer id) {
        log.debug("REST request to get Commentaire : {}", id);
        CommentaireResponseDTO dto = commentaireService.findOne(id);
        RestPreconditions.checkFound(dto, "commentaire.NotFound");
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/commentaires")
    public List<CommentaireResponseDTO> getAllCommentaires() {
        log.debug("Request to get all Commentaires");
        return commentaireService.findAll();
    }

    @DeleteMapping("/commentaires/{id}")
    public ResponseEntity<Void> deleteCommentaire(@PathVariable Integer id) {
        log.debug("Request to delete Commentaire: {}", id);
        commentaireService.delete(id);
        return ResponseEntity.noContent().build(); // Changed to 204 No Content
    }
}