package com.yegecali.apichecker.config;

import io.quarkus.runtime.annotations.StaticInitSafe;
import io.smallrye.config.ConfigMapping;

import java.util.Map;
import java.util.Optional;

@StaticInitSafe
@ConfigMapping(prefix = "health-check.environments")
public interface EnvironmentConfiguration {

    Map<String, EnvironmentConfig> configs();

    interface EnvironmentConfig {
        ApiUrls urls();
        TestData testData();
    }

    interface ApiUrls {
        String cardApi();
        String personApi();
        String paymentApi();
    }

    interface TestData {
        String cardNumber();
        String documentNumber();
        String apiKey();
        Optional<String> customHeader();
    }
}
