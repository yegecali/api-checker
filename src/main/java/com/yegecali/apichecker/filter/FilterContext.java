package com.yegecali.apichecker.filter;

import com.yegecali.apichecker.strategy.HealthCheckScenario;

public class FilterContext {
    private final HealthCheckScenario.Environment targetEnvironment;
    private final boolean onlyCritical;

    public FilterContext(HealthCheckScenario.Environment targetEnvironment, boolean onlyCritical) {
        this.targetEnvironment = targetEnvironment;
        this.onlyCritical = onlyCritical;
    }

    public HealthCheckScenario.Environment getTargetEnvironment() {
        return targetEnvironment;
    }

    public boolean isOnlyCritical() {
        return onlyCritical;
    }
}

