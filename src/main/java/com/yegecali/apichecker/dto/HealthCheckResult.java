package com.yegecali.apichecker.dto;

public class HealthCheckResult {
    public String name;
    public HealthCheckStatus status;
    public boolean available;
    public int priority;

    public HealthCheckResult(String name, HealthCheckStatus status, boolean available, int priority) {
        this.name = name;
        this.status = status;
        this.available = available;
        this.priority = priority;
    }
}



