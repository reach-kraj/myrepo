package com.example.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfiguration {
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("customer_route", r -> r.path("/customers/**")
                        .uri("http://localhost:8081"))
                .route("order_route", r -> r.path("/orders/**")
                        .uri("http://localhost:8080"))
                .build();
    }
}