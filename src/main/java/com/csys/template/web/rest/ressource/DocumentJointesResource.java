package com.csys.template.web.rest.ressource;

import com.csys.template.domain.DocumentJointes;
import com.csys.template.dtoResponse.DocumentJointesResponseDTO;
import com.csys.template.service.DocumentJointesService;
import com.csys.template.util.RestPreconditions;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
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

    @PostMapping(value = "/document-jointes", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentJointesResponseDTO> createDocumentJointes(
            @RequestParam("file") MultipartFile file,
            @RequestParam("idTicket") Integer idTicket) throws URISyntaxException, IOException {
        log.debug("REST request to save DocumentJointes for ticket ID: {}", idTicket);
        DocumentJointesResponseDTO result = documentJointesService.save(file, idTicket);
        return ResponseEntity.created(new URI("/api/document-jointes/" + result.getId())).body(result);
    }

    @GetMapping("/document-jointes/{id}/download")
    public ResponseEntity<byte[]> downloadDocumentJointes(@PathVariable Integer id) {
        log.debug("REST request to download DocumentJointes content: {}", id);
        DocumentJointes document = documentJointesService.findDocumentJointes(id);
        RestPreconditions.checkFound(document, "documentjointes.NotFound");

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getNomDocument() + "\"");
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(document.getDocument().length)
                .contentType(MediaType.parseMediaType(document.getExtension()))
                .body(document.getDocument());
    }

    @GetMapping("/document-jointes")
    public List<DocumentJointesResponseDTO> getAllDocumentJointes() {
        log.debug("Request to get all DocumentJointes (metadata only)");
        return documentJointesService.findAll();
    }

    @DeleteMapping("/document-jointes/{id}")
    public ResponseEntity<Void> deleteDocumentJointes(@PathVariable Integer id) {
        log.debug("Request to delete DocumentJointes: {}", id);
        documentJointesService.delete(id);
        return ResponseEntity.noContent().build();
    }
}