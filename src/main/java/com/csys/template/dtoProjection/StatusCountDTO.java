// Dans un nouveau fichier: src/main/java/com/csys/template/dtoProjection/StatusCountDTO.java
package com.csys.template.dtoProjection;

import com.csys.template.domain.enum_identifier.Status;

public class StatusCountDTO {
    private Status status;
    private Long count;

    public StatusCountDTO(Status status, Long count) {
        this.status = status;
        this.count = count;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}