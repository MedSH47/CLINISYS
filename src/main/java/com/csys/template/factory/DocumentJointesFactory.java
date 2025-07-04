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
import org.apache.commons.io.FilenameUtils; // Assurez-vous d'avoir cette dépendance

public class DocumentJointesFactory {

    public static DocumentJointesResponseDTO toResponseDTO(DocumentJointes entity) {
        if (entity == null) return null;
        
        DocumentJointesResponseDTO dto = new DocumentJointesResponseDTO();
        dto.setId(entity.getId());
        dto.setExtension(entity.getExtension());
        dto.setDateDocument(entity.getDateDocument());
        dto.setNomDocument(entity.getNomDocument());
        // On ne met PAS le contenu du fichier dans le DTO de réponse par défaut pour des raisons de performance.
        if (entity.getTicket() != null) {
            dto.setIdTicket(entity.getTicket().getId());
        }
        return dto;
    }

    public static DocumentJointes toEntity(MultipartFile file, Integer idTicket) throws IOException {
        if (file == null || file.isEmpty()) return null;
        
        DocumentJointes entity = new DocumentJointes();
        // Utilisation de FilenameUtils pour extraire proprement le nom du fichier sans son extension
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