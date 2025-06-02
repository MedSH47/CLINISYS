package com.csys.template.factory;

import com.csys.template.domain.Avancement;
import com.csys.template.dto.AvancementDTO;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Factory for converting between Avancement and AvancementDTO.
 */
public class AvancementFactory {

    public static AvancementDTO toDTO(Avancement avancement) {
        if (avancement == null) return null;

        AvancementDTO dto = new AvancementDTO();
        dto.setId(avancement.getId());
        dto.setDateEcheance(avancement.getDateEcheance());
        dto.setDateDebut(avancement.getDateDebut());
        dto.setDateFin(avancement.getDateFin());
        dto.setDureeTravail(avancement.getDureeTravail());
        
        // For safe serialization, this should ideally be a DTO, not a direct entity reference.
        // Assuming the DTO is designed to handle this.
        dto.setIdTicket(avancement.getIdTicket());

        return dto;
    }

    public static Avancement toEntity(AvancementDTO dto) {
        if (dto == null) return null;

        Avancement entity = new Avancement();
        entity.setId(dto.getId());
        entity.setDateEcheance(dto.getDateEcheance());
        entity.setDateDebut(dto.getDateDebut());
        entity.setDateFin(dto.getDateFin());
        entity.setDureeTravail(dto.getDureeTravail());
        entity.setIdTicket(dto.getIdTicket());

        return entity;
    }

    public static List<AvancementDTO> toDTOs(Collection<Avancement> avancements) {
        if (avancements == null) return Collections.emptyList();
        return avancements.stream().map(AvancementFactory::toDTO).collect(Collectors.toList());
    }

    public static List<Avancement> toEntities(Collection<AvancementDTO> dtos) {
        if (dtos == null) return Collections.emptyList();
        return dtos.stream().map(AvancementFactory::toEntity).collect(Collectors.toList());
    }
}