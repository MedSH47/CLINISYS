// Dans un nouveau fichier: src/main/java/com/csys/template/dtoProjection/PerformanceStatDTO.java
package com.csys.template.dtoProjection;


public class PerformanceStatsDTO {

    private String timeUnit;        // Ex: "Lun", "Mar", "janv.", "2024"
    private String groupName;       // Ex: "Utilisateur A", "Module CRM"
    private long totalTickets;      // Nombre total de tickets terminés
    private long onTimeTickets;     // Nombre de tickets terminés à temps

    // Constructeur
    public PerformanceStatsDTO(String timeUnit, String groupName, long totalTickets, long onTimeTickets) {
        this.timeUnit = timeUnit;
        this.groupName = groupName;
        this.totalTickets = totalTickets;
        this.onTimeTickets = onTimeTickets;
    }

    // Getters et Setters
    public String getTimeUnit() { return timeUnit; }
    public void setTimeUnit(String timeUnit) { this.timeUnit = timeUnit; }
    public String getGroupName() { return groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }
    public long getTotalTickets() { return totalTickets; }
    public void setTotalTickets(long totalTickets) { this.totalTickets = totalTickets; }
    public long getOnTimeTickets() { return onTimeTickets; }
    public void setOnTimeTickets(long onTimeTickets) { this.onTimeTickets = onTimeTickets; }
}