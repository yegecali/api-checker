package com.yegecali.apichecker.dto;

import java.util.List;

public class HealthCheckResponse {
    public HealthCheckStatus status;
    public boolean allHealthy;
    public int totalChecks;
    public List<HealthCheckResult> checks;

    public HealthCheckResponse(HealthCheckStatus status, boolean allHealthy, int totalChecks, List<HealthCheckResult> checks) {
        this.status = status;
        this.allHealthy = allHealthy;
        this.totalChecks = totalChecks;
        this.checks = checks;
    }
}
