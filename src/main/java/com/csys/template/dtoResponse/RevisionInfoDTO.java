// Créez le fichier : src/main/java/com/csys/template/dtoResponse/RevisionInfoDTO.java
package com.csys.template.dtoResponse;

import java.util.Date;

import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RevisionInfoDTO {
    private int id;
    private Date timestamp;
    private String userCreate;

   
}