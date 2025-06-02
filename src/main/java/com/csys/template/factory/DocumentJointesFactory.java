package com.csys.template.factory;

import com.csys.template.domain.DocumentJointes;
import com.csys.template.dto.DocumentJointesDTO;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Factory for converting between DocumentJointes and DocumentJointesDTO.
 */
public class DocumentJointesFactory {

    public static DocumentJointesDTO toDTO(DocumentJointes document) {
        if (document == null) return null;

        DocumentJointesDTO dto = new DocumentJointesDTO();
        dto.setId(document.getId());
        dto.setNomDocument(document.getNomDocument());
        dto.setExtension(document.getExtension());
        dto.setDateDocument(document.getDateDocument());
        dto.setDocument(document.getDocument());
        dto.setIdTicket(document.getIdTicket());
        
        if (document.getTicketfichierSet() != null) {
            dto.setTicketfichierSet(document.getTicketfichierSet().stream()
                .map(TicketfichierFactory::toDTOLight) 
                .collect(Collectors.toSet()));
        }

        return dto;
    }

    public static DocumentJointesDTO toDTOLight(DocumentJointes document) {
        if (document == null) return null;

        DocumentJointesDTO dto = new DocumentJointesDTO();
        dto.setId(document.getId());
        dto.setNomDocument(document.getNomDocument());
        dto.setExtension(document.getExtension());
        return dto;
    }

    public static DocumentJointes toEntity(DocumentJointesDTO dto) {
        if (dto == null) return null;

        DocumentJointes entity = new DocumentJointes();
        entity.setId(dto.getId());
        entity.setNomDocument(dto.getNomDocument());
        entity.setExtension(dto.getExtension());
        entity.setDateDocument(dto.getDateDocument());
        entity.setDocument(dto.getDocument());
        entity.setIdTicket(dto.getIdTicket());

        return entity;
    }

    public static List<DocumentJointesDTO> toDTOs(Collection<DocumentJointes> documents) {
        if (documents == null) return Collections.emptyList();
        return documents.stream().map(DocumentJointesFactory::toDTO).collect(Collectors.toList());
    }
    
    public static List<DocumentJointesDTO> toDTOsLight(Collection<DocumentJointes> documents) {
        if (documents == null) return Collections.emptyList();
        return documents.stream().map(DocumentJointesFactory::toDTOLight).collect(Collectors.toList());
    }

    public static List<DocumentJointes> toEntities(Collection<DocumentJointesDTO> dtos) {
        if (dtos == null) return Collections.emptyList();
        return dtos.stream().map(DocumentJointesFactory::toEntity).collect(Collectors.toList());
    }
}