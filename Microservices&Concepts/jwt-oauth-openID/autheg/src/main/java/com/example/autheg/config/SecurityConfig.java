package com.example.autheg.config;

import org.springframework.context.annotation.Bean; // Marks a method as a Bean provider
import org.springframework.context.annotation.Configuration; // Indicates this class contains Spring configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity; // Configures HTTP security
import org.springframework.security.web.SecurityFilterChain; // Represents a chain of security filters

@Configuration // Declares this class as a configuration source
public class SecurityConfig {

    @Bean // Registers the security filter chain bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth // Configure request authorization
                .requestMatchers("/").permitAll() // Allow unauthenticated access to the root URL
                .anyRequest().authenticated() // All other endpoints require authentication
            )
            .oauth2Login() // Enables OAuth2 login support (e.g., Google, Auth0)
            .and()
            .oauth2ResourceServer(oauth2 -> oauth2.jwt()); // Enables JWT-based authentication for API access

        return http.build(); // Builds and returns the security filter chain
    }
}