package com.example.securityexample.config.security.provider;

import com.example.securityexample.config.security.authentication.BasicAuthAuthenticationToken;
import com.example.securityexample.config.security.authentication.UserSuccessfulAuthenticationToken;
import com.example.securityexample.entity.User;
import com.example.securityexample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class BasicAuthAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService service;

    @Override
    public UserSuccessfulAuthenticationToken authenticate(Authentication authentication) throws AuthenticationException {
        var token = (BasicAuthAuthenticationToken) authentication;
        String username = token.getPrincipal();
        UserDetails userDetails = service.loadUserByUsername(username);
        if (userDetails == null || !userDetails.getPassword().equals(token.getCredentials())) {
            throw new BadCredentialsException("password of user " + username + " (" + userDetails.getPassword() + ") " + " is not equal to " + token.getCredentials());
        }
        return new UserSuccessfulAuthenticationToken((User) userDetails);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return BasicAuthAuthenticationToken.class.equals(authentication);
    }
}
