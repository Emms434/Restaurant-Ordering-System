package com.restaurant.ordering.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Liveness check: GET /health returns "OK". Load balancers (e.g. Elastic
 * Beanstalk) and monitoring can poll this to see whether the app is up.
 */
@RestController
public class HealthController {

    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}
