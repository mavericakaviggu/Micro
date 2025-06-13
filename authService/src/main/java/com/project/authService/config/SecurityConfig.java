package com.project.authService.config;

import com.project.authService.filter.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import com.project.authService.service.AppUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ProviderManager;

import java.util.List;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // Marks this class as a Spring configuration class
@EnableWebSecurity
@RequiredArgsConstructor // Generates a constructor for required fields (finals) using Lombok
public class SecurityConfig {

    // Custom user details service that loads user data from DB
    private final AppUserDetailsService userDetailsService;
    private final JwtRequestFilter jwtRequestFilter; // JWT filter to validate tokens

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection (useful for stateless REST APIs)
                // Define public and secured endpoints
                .authorizeHttpRequests(authz -> authz 
                        .requestMatchers("/login", "/register", "/send-reset-otp", "/reset-password", "/logout").permitAll() // These endpoints are accessible without authentication
                        .anyRequest().authenticated() // All other endpoints require authentication
                )

                // Stateless session management (no HTTP session will be created or used)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // Disable default logout handler (optional based on implementation)
                .logout(logout -> logout.disable())
                // Add JWT filter before the default filter
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build(); // Builds and returns the configured security filter chain
    }

    // Bean for password encryption using BCrypt hashing
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean to register the CORS filter with custom configuration
    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter(corsConfigurationSource());
    }

    // Custom CORS configuration (allows frontend app to talk to backend)
    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:8084")); // Allow requests from this origin (your frontend running on port 8084)
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")); // Allow specified HTTP methods
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));  // Allow headers required for JWT-based auth
        configuration.setAllowCredentials(true); // Allow credentials (cookies, auth headers, etc.)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Register configuration for all endpoints
        return source;
    }

    // Bean for authentication provider which uses the custom user details service and password encoder
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService); // Set your custom user service
        provider.setPasswordEncoder(passwordEncoder); // Set password encoder
        return provider;
    }

    // Bean for authentication manager, responsible for performing authentication
    @Bean
    public AuthenticationManager authenticationManager(DaoAuthenticationProvider daoAuthenticationProvider) {
        return new ProviderManager(daoAuthenticationProvider);
    }
}
