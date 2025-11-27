package com.yegecali.apichecker.filter;

import com.yegecali.apichecker.strategy.HealthCheckScenario;

import java.util.logging.Logger;

public abstract class HealthCheckFilter {

    private static final Logger LOGGER = Logger.getLogger(HealthCheckFilter.class.getName());

    protected HealthCheckFilter nextFilter;

    public HealthCheckFilter setNext(HealthCheckFilter nextFilter) {
        this.nextFilter = nextFilter;
        return nextFilter;
    }

    public boolean filter(HealthCheckScenario scenario, FilterContext context) {
        if (doFilter(scenario, context)) {
            if (nextFilter != null) {
                return nextFilter.filter(scenario, context);
            }
            return true;
        }
        LOGGER.info("Scenario '" + scenario.getName() + "' filtrado por: " + this.getClass().getSimpleName());
        return false;
    }

    protected abstract boolean doFilter(HealthCheckScenario scenario, FilterContext context);
}

