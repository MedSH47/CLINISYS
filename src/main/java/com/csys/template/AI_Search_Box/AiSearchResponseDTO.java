package com.csys.template.AI_Search_Box;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AiSearchResponseDTO {
    private String entityType;
    private List<?> data;
    private String doumean;

    public AiSearchResponseDTO(String entityType, List<?> data) {
        this.entityType = entityType;
        this.data = data;
    }

    public AiSearchResponseDTO(String doumean) {
        this.doumean = doumean;
    }

    // Getters and Setters
    public String getEntityType() { return entityType; }
    public void setEntityType(String entityType) { this.entityType = entityType; }
    public List<?> getData() { return data; }
    public void setData(List<?> data) { this.data = data; }
    public String getDoumean() { return doumean; }
    public void setDoumean(String doumean) { this.doumean = doumean; }
}