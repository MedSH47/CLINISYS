package com.csys.template.dtoProjection;



public class TeamPerformanceDTO {

    private String teamName;
    private double onTimeRate; // Taux de résolution à temps (en %)

    public TeamPerformanceDTO(String teamName, double onTimeRate) {
        this.teamName = teamName;
        this.onTimeRate = onTimeRate;
    }

    // Getters et Setters
    public String getTeamName() { return teamName; }
    public void setTeamName(String teamName) { this.teamName = teamName; }
    public double getOnTimeRate() { return onTimeRate; }
    public void setOnTimeRate(double onTimeRate) { this.onTimeRate = onTimeRate; }
}