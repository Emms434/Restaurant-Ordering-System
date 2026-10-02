package com.restaurant.ordering.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS (Cross-Origin Resource Sharing) settings.
 *
 * The React app is served from a different origin than the API (port 5173 vs
 * 8080 locally, CloudFront vs Elastic Beanstalk in production), so browsers
 * block its requests unless the API explicitly allows that origin. The allowed
 * origin comes from APP_CORS_ALLOWED_ORIGIN, so each environment can set its own.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${app.cors.allowed-origin:http://localhost:5173}")
    private String allowedOrigin;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(allowedOrigin)
                .allowedMethods("GET", "POST", "DELETE", "OPTIONS");
    }
}
