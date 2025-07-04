package com.csys.template.web.rest.ressource;

import com.csys.template.domain.DocumentJointes;
import com.csys.template.dtoResponse.DocumentJointesResponseDTO;
import com.csys.template.service.DocumentJointesService;
import com.csys.template.util.RestPreconditions;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class DocumentJointesResource {
    private final Logger log = LoggerFactory.getLogger(DocumentJointesResource.class);
    private final DocumentJointesService documentJointesService;

    public DocumentJointesResource(DocumentJointesService documentJointesService) {
        this.documentJointesService = documentJointesService;
    }

    /**
     * Endpoint pour l'upload (téléversement) d'un fichier.
     * Consomme 'multipart/form-data'.
     */
    @PostMapping(value = "/document-jointes", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentJointesResponseDTO> createDocumentJointes(
            @RequestParam("file") MultipartFile file,
            @RequestParam("idTicket") Integer idTicket) throws URISyntaxException, IOException {
        log.debug("Requête REST pour sauvegarder un document pour le ticket ID: {}", idTicket);
        DocumentJointesResponseDTO result = documentJointesService.save(file, idTicket);
        return ResponseEntity.created(new URI("/api/document-jointes/" + result.getId())).body(result);
    }

    /**
     * Endpoint pour le téléchargement d'un fichier.
     * Renvoie les données binaires du fichier.
     */
    @GetMapping("/document-jointes/{id}/download")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable Integer id) {
        log.debug("Requête REST pour télécharger le contenu du document : {}", id);
        DocumentJointes document = documentJointesService.findDocumentJointes(id);
        RestPreconditions.checkFound(document, "documentjointes.NotFound");

        String filename = document.getNomDocument() + "." + document.getExtension();

        HttpHeaders header = new HttpHeaders();
        // C'est cet en-tête qui indique au navigateur de télécharger le fichier avec le bon nom
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(document.getDocument().length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM) // Type MIME générique pour un fichier binaire
                .body(document.getDocument());
    }

    /**
     * Endpoint pour supprimer un document.
     */
    @DeleteMapping("/document-jointes/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Integer id) {
        log.debug("Requête REST pour supprimer le document : {}", id);
        documentJointesService.delete(id);
        return ResponseEntity.noContent().build();
    }
}