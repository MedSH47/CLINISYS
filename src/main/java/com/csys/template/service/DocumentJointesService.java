package com.csys.template.service;

import com.csys.template.domain.DocumentJointes;
import com.csys.template.dtoResponse.DocumentJointesResponseDTO;
import com.csys.template.factory.DocumentJointesFactory;
import com.csys.template.repository.DocumentJointesRepository;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class DocumentJointesService {
    private final Logger log = LoggerFactory.getLogger(DocumentJointesService.class);
    private final DocumentJointesRepository documentJointesRepository;

    public DocumentJointesService(DocumentJointesRepository documentJointesRepository) {
        this.documentJointesRepository = documentJointesRepository;
    }

    public DocumentJointesResponseDTO save(MultipartFile file, Integer idTicket) throws IOException {
        log.debug("Request to save DocumentJointes for ticket ID: {}", idTicket);
        DocumentJointes documentJointes = DocumentJointesFactory.toEntity(file, idTicket);
        documentJointes = documentJointesRepository.save(documentJointes);
        return DocumentJointesFactory.toResponseDTO(documentJointes);
    }

    @Transactional(readOnly = true)
    public DocumentJointes findDocumentJointes(Integer id) {
        log.debug("Request to get DocumentJointes entity: {}", id);
        // This method can be used to fetch the full entity including byte[]
        return documentJointesRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<DocumentJointesResponseDTO> findAll() {
        log.debug("Request to get All DocumentJointes");
        List<DocumentJointes> result = documentJointesRepository.findAll();
        return DocumentJointesFactory.toResponseDTOs(result);
    }

    public void delete(Integer id) {
        log.debug("Request to delete DocumentJointes: {}", id);
        documentJointesRepository.deleteById(id);
    }
}