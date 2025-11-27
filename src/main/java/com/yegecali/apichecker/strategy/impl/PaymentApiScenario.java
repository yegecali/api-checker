package com.yegecali.apichecker.strategy.impl;

import com.yegecali.apichecker.config.EnvironmentConfigService;
import com.yegecali.apichecker.config.ExecutionContext;
import com.yegecali.apichecker.strategy.HealthCheckScenario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Random;
import java.util.logging.Logger;

@ApplicationScoped
public class PaymentApiScenario implements HealthCheckScenario {

    private static final Logger LOGGER = Logger.getLogger(PaymentApiScenario.class.getName());
    private final Random random = new Random();

    @Inject
    EnvironmentConfigService environmentConfigService;

    @Inject
    ExecutionContext executionContext;

    @Override
    public boolean execute() {
        Environment environment = executionContext.getCurrentEnvironment();
        LOGGER.info("Ejecutando health check: " + getName() + " para ambiente: " + environment);

        // Obtener configuración del ambiente
        try {
            String apiUrl = environmentConfigService.getPaymentApiUrl(environment);
            String cardNumber = environmentConfigService.getCardNumber(environment);
            String apiKey = environmentConfigService.getApiKey(environment);

            LOGGER.info("URL API: " + apiUrl);
            LOGGER.info("Card Number (masked): " + maskCardNumber(cardNumber));
            LOGGER.info("API Key (masked): " + maskApiKey(apiKey));
        } catch (Exception e) {
            LOGGER.warning("No se pudo cargar configuración para ambiente: " + environment + ". " + e.getMessage());
        }

        // Simular llamada a API con delay
        try {
            Thread.sleep(500 + random.nextInt(1000)); // 0.5 a 1.5 segundos
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }

        // Resultado aleatorio: 70% disponible, 30% no disponible
        boolean available = random.nextInt(100) < 70;
        LOGGER.info(getName() + " - Disponibilidad: " + available);

        return available;
    }

    private String maskCardNumber(String cardNumber) {
        if (cardNumber != null && cardNumber.length() > 4) {
            return "****" + cardNumber.substring(cardNumber.length() - 4);
        }
        return "****";
    }

    private String maskApiKey(String apiKey) {
        if (apiKey != null && apiKey.length() > 8) {
            return apiKey.substring(0, 4) + "..." + apiKey.substring(apiKey.length() - 4);
        }
        return "****";
    }

    @Override
    public String getName() {
        return "Payment API";
    }

    @Override
    public int priority() {
        return 3; // Valor por defecto, será sobrescrito por configuración
    }

    @Override
    public Environment getEnvironment() {
        return Environment.ALL; // Valor por defecto, será sobrescrito por configuración
    }

    @Override
    public CriticalityLevel getCriticalityLevel() {
        return CriticalityLevel.NORMAL; // Valor por defecto, será sobrescrito por configuración
    }
}

