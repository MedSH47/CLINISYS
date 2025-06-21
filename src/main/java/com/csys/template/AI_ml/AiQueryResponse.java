package com.csys.template.AI_ml;

import java.util.Map;

public class AiQueryResponse {
    private String entityType;
    private Map<String, String> entities;

    // Getters and Setters
    public String getEntityType() { return entityType; }
    public void setEntityType(String entityType) { this.entityType = entityType; }
    public Map<String, String> getEntities() { return entities; }
    public void setEntities(Map<String, String> entities) { this.entities = entities; }
}