// Créez le fichier : src/main/java/com/csys/template/dtoResponse/AuditLogDTO.java
package com.csys.template.dtoResponse;

import org.hibernate.envers.RevisionType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogDTO {
    private RevisionInfoDTO revisionInfo;
    private RevisionType revisionType; // ADD, MOD, DEL
    private Object entity; // L'état de l'entité à cette révision

}