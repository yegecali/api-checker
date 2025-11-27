package com.yegecali.apichecker.config;

import com.yegecali.apichecker.strategy.HealthCheckScenario;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class ExecutionContext {

    private HealthCheckScenario.Environment currentEnvironment;

    public HealthCheckScenario.Environment getCurrentEnvironment() {
        return currentEnvironment != null ? currentEnvironment : HealthCheckScenario.Environment.ALL;
    }

    public void setCurrentEnvironment(HealthCheckScenario.Environment environment) {
        this.currentEnvironment = environment;
    }
}

