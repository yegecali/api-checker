package com.yegecali.apichecker.strategy;

public interface HealthCheckScenario {
    boolean execute();
    String getName();
    int priority();
    Environment getEnvironment();
    CriticalityLevel getCriticalityLevel();

    enum Environment {
        DESARROLLO,
        QA,
        PRODUCCION,
        ALL
    }

    enum CriticalityLevel {
        CRITICAL,
        NORMAL,
        LOW
    }
}

