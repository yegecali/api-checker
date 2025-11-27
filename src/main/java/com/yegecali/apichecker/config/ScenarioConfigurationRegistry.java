package com.yegecali.apichecker.config;

import com.yegecali.apichecker.strategy.HealthCheckScenario;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@ApplicationScoped
public class ScenarioConfigurationRegistry {

    private static final Logger LOGGER = Logger.getLogger(ScenarioConfigurationRegistry.class.getName());

    private final Map<String, ScenarioMetadata> scenarioConfigurations = new HashMap<>();

    @PostConstruct
    public void init() {
        LOGGER.info("Inicializando configuración de escenarios de health check...");

        // Configuración de CardExistScenario
        registerScenario(
            "CardExistScencario",
            HealthCheckScenario.Environment.ALL,
            HealthCheckScenario.CriticalityLevel.CRITICAL,
            1
        );

        // Configuración de PersonExistScenario
        registerScenario(
            "PersonExistScenario",
            HealthCheckScenario.Environment.PRODUCCION,
            HealthCheckScenario.CriticalityLevel.NORMAL,
            2
        );

        // Configuración de PaymentApiScenario
        registerScenario(
            "PaymentApiScenario",
            HealthCheckScenario.Environment.QA,
            HealthCheckScenario.CriticalityLevel.LOW,
            3
        );

        LOGGER.info("Configuración de escenarios completada: " + scenarioConfigurations.size() + " escenarios registrados");
    }

    private void registerScenario(String scenarioClassName,
                                 HealthCheckScenario.Environment environment,
                                 HealthCheckScenario.CriticalityLevel criticalityLevel,
                                 int priority) {
        ScenarioMetadata metadata = new ScenarioMetadata(environment, criticalityLevel, priority);
        scenarioConfigurations.put(scenarioClassName, metadata);
        LOGGER.info("Registrado: " + scenarioClassName + " -> Ambiente: " + environment +
                   ", Criticidad: " + criticalityLevel + ", Prioridad: " + priority);
    }

    public ScenarioMetadata getMetadata(String scenarioClassName) {
        return scenarioConfigurations.get(scenarioClassName);
    }

    public HealthCheckScenario.Environment getEnvironment(String scenarioClassName) {
        ScenarioMetadata metadata = scenarioConfigurations.get(scenarioClassName);
        return metadata != null ? metadata.getEnvironment() : HealthCheckScenario.Environment.ALL;
    }

    public HealthCheckScenario.CriticalityLevel getCriticalityLevel(String scenarioClassName) {
        ScenarioMetadata metadata = scenarioConfigurations.get(scenarioClassName);
        return metadata != null ? metadata.getCriticalityLevel() : HealthCheckScenario.CriticalityLevel.NORMAL;
    }

    public int getPriority(String scenarioClassName) {
        ScenarioMetadata metadata = scenarioConfigurations.get(scenarioClassName);
        return metadata != null ? metadata.getPriority() : 999;
    }
}

