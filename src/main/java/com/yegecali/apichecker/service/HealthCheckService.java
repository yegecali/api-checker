package com.yegecali.apichecker.service;

import com.yegecali.apichecker.config.ConfiguredScenario;
import com.yegecali.apichecker.config.ScenarioConfigurationRegistry;
import com.yegecali.apichecker.config.ScenarioMetadata;
import com.yegecali.apichecker.dto.HealthCheckResponse;
import com.yegecali.apichecker.dto.HealthCheckResult;
import com.yegecali.apichecker.dto.HealthCheckStatus;
import com.yegecali.apichecker.filter.FilterContext;
import com.yegecali.apichecker.filter.HealthCheckFilter;
import com.yegecali.apichecker.filter.impl.CriticalityFilter;
import com.yegecali.apichecker.filter.impl.EnvironmentFilter;
import com.yegecali.apichecker.strategy.HealthCheckScenario;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@ApplicationScoped
public class HealthCheckService {

    private static final Logger LOGGER = Logger.getLogger(HealthCheckService.class.getName());

    @Inject
    Instance<HealthCheckScenario> healthCheckScenarios;

    @Inject
    EnvironmentFilter environmentFilter;

    @Inject
    CriticalityFilter criticalityFilter;

    @Inject
    ScenarioConfigurationRegistry configurationRegistry;

    private HealthCheckFilter filterChain;

    @PostConstruct
    public void init() {
        // Configurar la cadena de filtros
        filterChain = environmentFilter;
        environmentFilter.setNext(criticalityFilter);
        LOGGER.info("Cadena de filtros de health check configurada: EnvironmentFilter -> CriticalityFilter");
    }

    public HealthCheckResponse executeAllHealthChecks(HealthCheckScenario.Environment environment, boolean onlyCritical) {
        LOGGER.info("Iniciando ejecución de health checks para ambiente: " + environment + ", solo críticos: " + onlyCritical);

        FilterContext context = new FilterContext(environment, onlyCritical);
        List<HealthCheckScenario> scenarios = getFilteredScenarios(context);

        LOGGER.info("Escenarios filtrados: " + scenarios.size() + " de " + healthCheckScenarios.stream().count());

        List<HealthCheckResult> results = new ArrayList<>();
        boolean allPassed = true;

        for (HealthCheckScenario scenario : scenarios) {
            HealthCheckResult result = executeScenario(scenario);
            results.add(result);

            if (!result.available) {
                allPassed = false;
            }
        }

        HealthCheckResponse response = buildResponse(allPassed, results);
        LOGGER.info("Health check completado. Estado general: " + response.status);

        return response;
    }

    private List<HealthCheckScenario> getFilteredScenarios(FilterContext context) {
        return healthCheckScenarios.stream()
                .map(this::wrapWithConfiguration)
                .filter(scenario -> filterChain.filter(scenario, context))
                .sorted(Comparator.comparingInt(HealthCheckScenario::priority))
                .collect(Collectors.toList());
    }

    private HealthCheckScenario wrapWithConfiguration(HealthCheckScenario scenario) {
        String className = scenario.getClass().getSimpleName();
        ScenarioMetadata metadata = configurationRegistry.getMetadata(className);

        if (metadata != null) {
            return new ConfiguredScenario(scenario, metadata);
        }

        // Si no hay configuración, usar valores por defecto del escenario
        LOGGER.warning("No se encontró configuración para: " + className + ". Usando valores por defecto.");
        return scenario;
    }

    private HealthCheckResult executeScenario(HealthCheckScenario scenario) {
        try {
            boolean available = scenario.execute();
            HealthCheckStatus status = available ? HealthCheckStatus.OK : HealthCheckStatus.FAILED;

            return new HealthCheckResult(
                    scenario.getName(),
                    status,
                    available,
                    scenario.priority()
            );
        } catch (Exception e) {
            LOGGER.severe("Error ejecutando " + scenario.getName() + ": " + e.getMessage());
            return new HealthCheckResult(
                    scenario.getName(),
                    HealthCheckStatus.ERROR,
                    false,
                    scenario.priority()
            );
        }
    }

    private HealthCheckResponse buildResponse(boolean allPassed, List<HealthCheckResult> results) {
        HealthCheckStatus status = allPassed ? HealthCheckStatus.OK : HealthCheckStatus.FAILED;

        return new HealthCheckResponse(
                status,
                allPassed,
                results.size(),
                results
        );
    }
}

