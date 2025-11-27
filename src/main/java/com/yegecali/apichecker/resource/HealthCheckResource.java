package com.yegecali.apichecker.resource;

import com.yegecali.apichecker.dto.HealthCheckResponse;
import com.yegecali.apichecker.service.HealthCheckService;
import com.yegecali.apichecker.strategy.HealthCheckScenario;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/health-check")
public class HealthCheckResource {

    @Inject
    HealthCheckService healthCheckService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response executeHealthChecks(
            @QueryParam("environment") String environment,
            @QueryParam("onlyCritical") boolean onlyCritical
    ) {
        HealthCheckScenario.Environment env = parseEnvironment(environment);
        HealthCheckResponse response = healthCheckService.executeAllHealthChecks(env, onlyCritical);

        return Response
                .status(response.allHealthy ? Response.Status.OK : Response.Status.SERVICE_UNAVAILABLE)
                .entity(response)
                .build();
    }

    private HealthCheckScenario.Environment parseEnvironment(String environment) {
        if (environment == null || environment.isEmpty()) {
            return HealthCheckScenario.Environment.ALL;
        }

        try {
            return HealthCheckScenario.Environment.valueOf(environment.toUpperCase());
        } catch (IllegalArgumentException e) {
            return HealthCheckScenario.Environment.ALL;
        }
    }
}

