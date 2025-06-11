package com.csys.template.factory;

import com.csys.template.domain.DocumentJointes;
import com.csys.template.domain.Ticket;
import com.csys.template.dtoResponse.DocumentJointesResponseDTO;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils; // Add this dependency if not present

public class DocumentJointesFactory {

    /**
     * Converts a DocumentJointes entity to a DocumentJointesResponseDTO.
     * The document content (byte array) is intentionally omitted for performance.
     */
    public static DocumentJointesResponseDTO toResponseDTO(DocumentJointes entity) {
        if (entity == null) return null;
        
        DocumentJointesResponseDTO dto = new DocumentJointesResponseDTO();
        dto.setId(entity.getId());
        dto.setExtension(entity.getExtension());
        dto.setDateDocument(entity.getDateDocument());
        dto.setNomDocument(entity.getNomDocument());
        if (entity.getTicket() != null) {
            dto.setIdTicket(entity.getTicket().getId());
        }
        
        return dto;
    }

    /**
     * Creates a DocumentJointes entity from a file and ticket ID.
     */
    public static DocumentJointes toEntity(MultipartFile file, Integer idTicket) throws IOException {
        if (file == null || file.isEmpty()) return null;
        
        DocumentJointes entity = new DocumentJointes();
        entity.setNomDocument(FilenameUtils.getBaseName(file.getOriginalFilename()));
        entity.setExtension(FilenameUtils.getExtension(file.getOriginalFilename()));
        entity.setDocument(file.getBytes());
        entity.setDateDocument(LocalDateTime.now());
        
        if (idTicket != null) {
            Ticket ticket = new Ticket();
            ticket.setId(idTicket);
            entity.setTicket(ticket);
        }
        
        return entity;
    }

    public static List<DocumentJointesResponseDTO> toResponseDTOs(Collection<DocumentJointes> entities) {
        if (entities == null) return Collections.emptyList();
        return entities.stream()
                .map(DocumentJointesFactory::toResponseDTO)
                .collect(Collectors.toList());
    }
}