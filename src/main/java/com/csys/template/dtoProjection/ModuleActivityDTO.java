package com.csys.template.dtoProjection;

public class ModuleActivityDTO {

    private String moduleName;
    private long totalTickets;
    private long openTickets;

    public ModuleActivityDTO(String moduleName, long totalTickets, long openTickets) {
        this.moduleName = moduleName;
        this.totalTickets = totalTickets;
        this.openTickets = openTickets;
    }

    // Getters et Setters
    public String getModuleName() { return moduleName; }
    public void setModuleName(String moduleName) { this.moduleName = moduleName; }
    public long getTotalTickets() { return totalTickets; }
    public void setTotalTickets(long totalTickets) { this.totalTickets = totalTickets; }
    public long getOpenTickets() { return openTickets; }
    public void setOpenTickets(long openTickets) { this.openTickets = openTickets; }
}