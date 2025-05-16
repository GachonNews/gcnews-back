package com.yourorg.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("crawling-service", r -> r.path("/api/crawling/**").uri("lb://CRAWLING-SERVER"))
            .route("article-service", r -> r.path("/api/article/**").uri("lb://ARTICLE-SERVER"))
            .route("quiz-service", r -> r.path("/api/quiz/**").uri("lb://QUIZ-SERVER"))
            .route("strike-service", r -> r.path("/api/strike/**").uri("lb://STRIKE-SERVER"))
            .route("summary-service", r -> r.path("/api/summary/**").uri("lb://SUMMARY-SERVER"))
            .route("user-info-service", r -> r.path("/api/user-info/**").uri("lb://USER-INFO-SERVER"))
            .build();
    }
}
