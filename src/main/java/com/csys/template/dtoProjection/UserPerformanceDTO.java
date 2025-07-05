package com.csys.template.dtoProjection;


public class UserPerformanceDTO {

    private String userName;
    private double onTimeRate;
    private long ticketsCompleted;

    public UserPerformanceDTO(String userName, double onTimeRate, long ticketsCompleted) {
        this.userName = userName;
        this.onTimeRate = onTimeRate;
        this.ticketsCompleted = ticketsCompleted;
    }

    // Getters et Setters
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public double getOnTimeRate() { return onTimeRate; }
    public void setOnTimeRate(double onTimeRate) { this.onTimeRate = onTimeRate; }
    public long getTicketsCompleted() { return ticketsCompleted; }
    public void setTicketsCompleted(long ticketsCompleted) { this.ticketsCompleted = ticketsCompleted; }
}