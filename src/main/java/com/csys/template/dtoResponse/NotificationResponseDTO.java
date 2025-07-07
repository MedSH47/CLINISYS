// Créez un nouveau fichier : src/main/java/com/csys/template/dtoResponse/NotificationResponseDTO.java

package com.csys.template.dtoResponse;

import java.time.LocalDateTime;

import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponseDTO {

    private Integer id;
    private String message;
    private boolean read;
    private LocalDateTime createdAt;
    private String link;

   
}