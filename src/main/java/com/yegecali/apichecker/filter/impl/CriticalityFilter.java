package com.yegecali.apichecker.filter.impl;

import com.yegecali.apichecker.filter.FilterContext;
import com.yegecali.apichecker.filter.HealthCheckFilter;
import com.yegecali.apichecker.strategy.HealthCheckScenario;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CriticalityFilter extends HealthCheckFilter {

    @Override
    protected boolean doFilter(HealthCheckScenario scenario, FilterContext context) {
        // Si no se solicita solo críticos, todos pasan
        if (!context.isOnlyCritical()) {
            return true;
        }

        // Si se solicita solo críticos, solo pasan los CRITICAL
        return scenario.getCriticalityLevel() == HealthCheckScenario.CriticalityLevel.CRITICAL;
    }
}

