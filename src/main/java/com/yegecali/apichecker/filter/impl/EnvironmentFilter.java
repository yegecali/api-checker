package com.yegecali.apichecker.filter.impl;

import com.yegecali.apichecker.filter.FilterContext;
import com.yegecali.apichecker.filter.HealthCheckFilter;
import com.yegecali.apichecker.strategy.HealthCheckScenario;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EnvironmentFilter extends HealthCheckFilter {

    @Override
    protected boolean doFilter(HealthCheckScenario scenario, FilterContext context) {
        HealthCheckScenario.Environment scenarioEnv = scenario.getEnvironment();
        HealthCheckScenario.Environment targetEnv = context.getTargetEnvironment();

        // Si el escenario es para ALL, siempre pasa
        if (scenarioEnv == HealthCheckScenario.Environment.ALL) {
            return true;
        }

        // Si el ambiente coincide, pasa
        return scenarioEnv == targetEnv;
    }
}
