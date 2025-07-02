// Dans un nouveau fichier: src/main/java/com/csys/template/dtoProjection/PerformanceStatDTO.java
package com.csys.template.dtoProjection;

public class PerformanceStatDTO {
    private String category; // Nom de l'employé ou de l'équipe
    private Long completedTickets;

    public PerformanceStatDTO(String category, Long completedTickets) {
        this.category = category;
        this.completedTickets = completedTickets;
    }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public Long getCompletedTickets() { return completedTickets; }
    public void setCompletedTickets(Long completedTickets) { this.completedTickets = completedTickets; }
}