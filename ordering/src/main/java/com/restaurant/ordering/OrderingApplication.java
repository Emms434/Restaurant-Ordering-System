package com.restaurant.ordering;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the backend. @SpringBootApplication turns on component
 * scanning for everything under com.restaurant.ordering (controllers,
 * services, repositories, config) and auto-configures the web server, JPA,
 * and Flyway from application.properties.
 *
 * On startup Flyway runs the SQL files in resources/db/migration to create
 * and seed the schema, then Hibernate validates that the entities match it.
 */
@SpringBootApplication
public class OrderingApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderingApplication.class, args);
    }
}
