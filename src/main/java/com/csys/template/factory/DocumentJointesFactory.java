package com.csys.template.factory;

import com.csys.template.domain.DocumentJointes;
import com.csys.template.dto.DocumentJointesDTO;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Factory for creating and converting DocumentJointes objects.
 * This is a self-contained utility class with static methods.
 */
public class DocumentJointesFactory {

    /**
     * Converts a DocumentJointes entity to a DocumentJointesDTO.
     *
     * @param entity The DocumentJointes entity to convert.
     * @return The corresponding DTO, or null if the Entity is null.
     */
    public static DocumentJointesDTO toDTO(DocumentJointes entity) {
        if (entity == null) {
            return null;
        }
        DocumentJointesDTO dto = new DocumentJointesDTO();
        dto.setId(entity.getId());
        dto.setExtension(entity.getExtension());
        dto.setDocument(entity.getDocument());
        dto.setDateDocument(entity.getDateDocument());
        dto.setNomDocument(entity.getNomDocument());
        
        return dto;
    }

    /**
     * Converts a DocumentJointesDTO to a DocumentJointes entity.
     *
     * @param dto The DocumentJointesDTO to convert.
     * @return The corresponding Entity, or null if the DTO is null.
     */
    public static DocumentJointes toEntity(DocumentJointesDTO dto) {
        if (dto == null) {
            return null;
        }
        DocumentJointes entity = new DocumentJointes();
        entity.setId(dto.getId());
        entity.setExtension(dto.getExtension());
        entity.setDocument(dto.getDocument());
        entity.setDateDocument(dto.getDateDocument());
        entity.setNomDocument(dto.getNomDocument());
      
        return entity;
    }

    /**
     * Converts a collection of Entities to a list of DTOs.
     *
     * @param entities The collection of Entities to convert.
     * @return A list of corresponding DTOs, or an empty list if the input is null.
     */
    public static List<DocumentJointesDTO> toDTOs(Collection<DocumentJointes> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream()
                .map(DocumentJointesFactory::toDTO) // Corrected: Use class name for static method reference
                .collect(Collectors.toList());
    }

    /**
     * Converts a collection of DTOs to a list of Entities.
     *
     * @param dtos The collection of DTOs to convert.
     * @return A list of corresponding Entities, or an empty list if the input is null.
     */
    public static List<DocumentJointes> toEntities(Collection<DocumentJointesDTO> dtos) {
        if (dtos == null) {
            return Collections.emptyList();
        }
        return dtos.stream()
                .map(DocumentJointesFactory::toEntity) // Corrected: Use class name for static method reference
                .collect(Collectors.toList());
    }
}