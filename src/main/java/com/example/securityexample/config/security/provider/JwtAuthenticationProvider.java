package com.example.securityexample.config.security.provider;

import com.example.securityexample.config.security.authentication.JwtAuthAuthenticationToken;
import com.example.securityexample.config.security.authentication.UserSuccessfulAuthenticationToken;
import com.example.securityexample.entity.User;
import com.example.securityexample.service.UserService;
import com.example.securityexample.util.JwtFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    JwtFacade jwtFacade;

    @Autowired
    UserService userService;

    @Override
    public UserSuccessfulAuthenticationToken authenticate(Authentication untrustedAuthentication) throws AuthenticationException {
        JwtAuthAuthenticationToken authenticationToken = (JwtAuthAuthenticationToken) untrustedAuthentication;
        String token = authenticationToken.getPrincipal();
        boolean tokenIsValid = jwtFacade.tokenIsValid(token);
        if (!tokenIsValid) {
            throw new BadCredentialsException("token is not valid");
        }
        String username = jwtFacade.getUserNameFromJwtToken(token);
        User user = (User)userService.loadUserByUsername(username);
        return new UserSuccessfulAuthenticationToken(user);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthAuthenticationToken.class.equals(authentication);
    }
}
