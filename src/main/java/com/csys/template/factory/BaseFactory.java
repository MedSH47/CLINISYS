package com.csys.template.factory;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A generic factory interface for converting between DTOs and Entities.
 *
 * @param <D> The DTO type.
 * @param <E> The Entity type.
 */
public interface BaseFactory<D, E> {

    /**
     * Converts a DTO to an Entity.
     *
     * @param dto The DTO to convert.
     * @return The corresponding Entity, or null if the DTO is null.
     */
    E toEntity(D dto);

    /**
     * Converts an Entity to a DTO.
     *
     * @param entity The Entity to convert.
     * @return The corresponding DTO, or null if the Entity is null.
     */
    D toDTO(E entity);

    /**
     * Converts a collection of DTOs to a list of Entities.
     *
     * @param dtos The collection of DTOs to convert.
     * @return A list of corresponding Entities, or an empty list if the input is null.
     */
    default List<E> toEntities(Collection<D> dtos) {
        if (dtos == null) {
            return java.util.Collections.emptyList();
        }
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    /**
     * Converts a collection of Entities to a list of DTOs.
     *
     * @param entities The collection of Entities to convert.
     * @return A list of corresponding DTOs, or an empty list if the input is null.
     */
    default List<D> toDTOs(Collection<E> entities) {
        if (entities == null) {
            return java.util.Collections.emptyList();
        }
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}