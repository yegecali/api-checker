package com.yegecali.apichecker.strategy.impl;

import com.yegecali.apichecker.strategy.HealthCheckScenario;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Random;
import java.util.logging.Logger;

@ApplicationScoped
public class PaymentApiScenario implements HealthCheckScenario {

    private static final Logger LOGGER = Logger.getLogger(PaymentApiScenario.class.getName());
    private final Random random = new Random();

    @Override
    public boolean execute() {
        LOGGER.info("Ejecutando health check: " + getName());

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

