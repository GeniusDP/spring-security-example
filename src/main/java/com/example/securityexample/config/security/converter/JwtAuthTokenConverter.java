package com.example.securityexample.config.security.converter;

import static com.example.securityexample.misc.Constants.BEARER_TOKEN_AUTH_PREFIX;

import com.example.securityexample.config.security.authentication.JwtAuthAuthenticationToken;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthTokenConverter implements Converter<String, JwtAuthAuthenticationToken> {

    @Override
    public JwtAuthAuthenticationToken convert(String authorizationHeader) {
        String token = authorizationHeader.replace(BEARER_TOKEN_AUTH_PREFIX, "");
        return new JwtAuthAuthenticationToken(token);
    }

}
