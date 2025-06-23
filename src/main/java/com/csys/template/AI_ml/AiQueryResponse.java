package com.csys.template.AI_ml;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AiQueryResponse {
    private String entityType;
    private Map<String, String> entities;
    private String doumean;

    // Getters and Setters
    public String getEntityType() { return entityType; }
    public void setEntityType(String entityType) { this.entityType = entityType; }
    public Map<String, String> getEntities() { return entities; }
    public void setEntities(Map<String, String> entities) { this.entities = entities; }
    public String getDoumean() { return doumean; }
    public void setDoumean(String doumean) { this.doumean = doumean; }
}