package com.yegecali.apichecker.config;

import com.yegecali.apichecker.strategy.HealthCheckScenario;

public class ConfiguredScenario implements HealthCheckScenario {

    private final HealthCheckScenario delegate;
    private final ScenarioMetadata metadata;

    public ConfiguredScenario(HealthCheckScenario delegate, ScenarioMetadata metadata) {
        this.delegate = delegate;
        this.metadata = metadata;
    }

    @Override
    public boolean execute() {
        return delegate.execute();
    }

    @Override
    public String getName() {
        return delegate.getName();
    }

    @Override
    public int priority() {
        return metadata.getPriority();
    }

    @Override
    public Environment getEnvironment() {
        return metadata.getEnvironment();
    }

    @Override
    public CriticalityLevel getCriticalityLevel() {
        return metadata.getCriticalityLevel();
    }
}

