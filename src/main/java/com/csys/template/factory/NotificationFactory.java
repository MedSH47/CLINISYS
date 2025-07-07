// Créez un nouveau fichier : src/main/java/com/csys/template/factory/NotificationFactory.java

package com.csys.template.factory;

import com.csys.template.domain.Notification;
import com.csys.template.dtoResponse.NotificationResponseDTO;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class NotificationFactory {

    public static NotificationResponseDTO toResponseDTO(Notification entity) {
        if (entity == null) {
            return null;
        }
        NotificationResponseDTO dto = new NotificationResponseDTO();
        dto.setId(entity.getId());
        dto.setMessage(entity.getMessage());
        dto.setRead(entity.isRead());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setLink(entity.getLink());
        return dto;
    }

    public static List<NotificationResponseDTO> toResponseDTOs(Collection<Notification> entities) {
        return entities.stream()
                .map(NotificationFactory::toResponseDTO)
                .collect(Collectors.toList());
    }
}