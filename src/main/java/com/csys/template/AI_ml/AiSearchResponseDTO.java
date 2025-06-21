package com.csys.template.AI_ml;


import java.util.List;

public class AiSearchResponseDTO {
    private String entityType;
    private List<?> data; // Using wildcard to hold any type of DTO list

    public AiSearchResponseDTO(String entityType, List<?> data) {
        this.entityType = entityType;
        this.data = data;
    }

    // Getters and Setters
    public String getEntityType() { return entityType; }
    public void setEntityType(String entityType) { this.entityType = entityType; }
    public List<?> getData() { return data; }
    public void setData(List<?> data) { this.data = data; }
}