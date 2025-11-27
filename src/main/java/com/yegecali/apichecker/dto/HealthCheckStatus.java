package com.yegecali.apichecker.dto;

public enum HealthCheckStatus {
    OK("OK"),
    FAILED("FAILED"),
    ERROR("ERROR");

    private final String value;

    HealthCheckStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}

