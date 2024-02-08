package com.example.securityexample.config;

import com.example.securityexample.config.security.provider.BasicAuthAuthenticationProvider;
import com.example.securityexample.config.security.filter.CustomAuthenticationFilter;
import com.example.securityexample.config.security.provider.JwtAuthenticationProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.stereotype.Component;

@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity
public class SecurityConfig {

    private static final String[] WHITE_LIST = {
        "test/allowed-to-all"
    };


    @Lazy
    @Autowired
    CustomAuthenticationFilter authenticationFilter;

    @Bean
    SecurityFilterChain customSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorizeHttpRequests ->
                authorizeHttpRequests
                    .requestMatchers(WHITE_LIST).permitAll()
                    .requestMatchers(HttpMethod.POST, "login").permitAll()
                    .requestMatchers("test/admin-url").hasRole("ADMIN")
                    .anyRequest().authenticated()
            )
            .exceptionHandling(exceptionHandlingConfigurer ->
                exceptionHandlingConfigurer
                    .accessDeniedHandler(accessDeniedHandler())
            )
            .addFilterAfter(authenticationFilter, ExceptionTranslationFilter.class);
        return http.build();
    }

    @Bean
    AccessDeniedHandler accessDeniedHandler() {
        return (request, response, exc) -> response.sendError(HttpStatus.FORBIDDEN.value());
    }

    @Bean
    AuthenticationManager ldapAuthenticationManager(
        JwtAuthenticationProvider jwtAuthenticationProvider,
        BasicAuthAuthenticationProvider basicAuthAuthenticationProvider
    ) {
        return new ProviderManager(jwtAuthenticationProvider, basicAuthAuthenticationProvider);
    }

}
