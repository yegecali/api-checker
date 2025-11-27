package com.yegecali.apichecker.config;

import com.yegecali.apichecker.strategy.HealthCheckScenario;

public class ScenarioMetadata {
    private final HealthCheckScenario.Environment environment;
    private final HealthCheckScenario.CriticalityLevel criticalityLevel;
    private final int priority;

    public ScenarioMetadata(HealthCheckScenario.Environment environment,
                           HealthCheckScenario.CriticalityLevel criticalityLevel,
                           int priority) {
        this.environment = environment;
        this.criticalityLevel = criticalityLevel;
        this.priority = priority;
    }

    public HealthCheckScenario.Environment getEnvironment() {
        return environment;
    }

    public HealthCheckScenario.CriticalityLevel getCriticalityLevel() {
        return criticalityLevel;
    }

    public int getPriority() {
        return priority;
    }
}
