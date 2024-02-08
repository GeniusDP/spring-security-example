package com.example.securityexample.config.security.filter;

import static com.example.securityexample.misc.Constants.BASIC_AUTH_PREFIX;
import static com.example.securityexample.misc.Constants.BEARER_TOKEN_AUTH_PREFIX;

import com.example.securityexample.config.security.converter.BasicAuthTokenConverter;
import com.example.securityexample.config.security.converter.JwtAuthTokenConverter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    AuthenticationManager manager;

    @Autowired
    BasicAuthTokenConverter basicAuthTokenConverter;

    @Autowired
    JwtAuthTokenConverter jwtAuthTokenConverter;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }

        Authentication uncheckedAuthentication;
        if (authorizationHeader.startsWith(BASIC_AUTH_PREFIX)) {
            uncheckedAuthentication = basicAuthTokenConverter.convert(authorizationHeader);
        } else if (authorizationHeader.startsWith(BEARER_TOKEN_AUTH_PREFIX)) {
            uncheckedAuthentication = jwtAuthTokenConverter.convert(authorizationHeader);
        } else {
            throw new IllegalArgumentException("Such authentication type not provided");
        }

        Authentication successfulAuthentication = manager.authenticate(uncheckedAuthentication);
        SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
        emptyContext.setAuthentication(successfulAuthentication);
        SecurityContextHolder.setContext(emptyContext);
        filterChain.doFilter(request, response);
    }

}
