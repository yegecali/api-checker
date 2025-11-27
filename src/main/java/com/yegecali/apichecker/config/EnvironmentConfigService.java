package com.yegecali.apichecker.config;

import com.yegecali.apichecker.strategy.HealthCheckScenario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.logging.Logger;

@ApplicationScoped
public class EnvironmentConfigService {

    private static final Logger LOGGER = Logger.getLogger(EnvironmentConfigService.class.getName());

    @Inject
    EnvironmentConfiguration environmentConfiguration;

    public EnvironmentConfiguration.EnvironmentConfig getConfigForEnvironment(HealthCheckScenario.Environment environment) {
        String envKey = mapEnvironmentToKey(environment);

        EnvironmentConfiguration.EnvironmentConfig config =
            environmentConfiguration.configs().get(envKey);

        if (config == null) {
            LOGGER.warning("No se encontró configuración para ambiente: " + environment +
                          ". Usando valores por defecto.");
            throw new IllegalStateException("Configuración no encontrada para ambiente: " + environment);
        }

        LOGGER.info("Cargando configuración para ambiente: " + environment);
        return config;
    }

    public String getCardApiUrl(HealthCheckScenario.Environment environment) {
        return getConfigForEnvironment(environment).urls().cardApi();
    }

    public String getPersonApiUrl(HealthCheckScenario.Environment environment) {
        return getConfigForEnvironment(environment).urls().personApi();
    }

    public String getPaymentApiUrl(HealthCheckScenario.Environment environment) {
        return getConfigForEnvironment(environment).urls().paymentApi();
    }

    public String getCardNumber(HealthCheckScenario.Environment environment) {
        return getConfigForEnvironment(environment).testData().cardNumber();
    }

    public String getDocumentNumber(HealthCheckScenario.Environment environment) {
        return getConfigForEnvironment(environment).testData().documentNumber();
    }

    public String getApiKey(HealthCheckScenario.Environment environment) {
        return getConfigForEnvironment(environment).testData().apiKey();
    }

    private String mapEnvironmentToKey(HealthCheckScenario.Environment environment) {
        return switch (environment) {
            case DESARROLLO -> "desarrollo";
            case QA -> "qa";
            case PRODUCCION -> "produccion";
            case ALL -> "desarrollo"; // Fallback a desarrollo
        };
    }
}

