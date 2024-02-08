package com.example.securityexample.config.security.converter;

import static com.example.securityexample.misc.Constants.BASIC_AUTH_PREFIX;

import com.example.securityexample.config.security.authentication.BasicAuthAuthenticationToken;
import java.util.Base64;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BasicAuthTokenConverter implements Converter<String, BasicAuthAuthenticationToken> {

    @Override
    public BasicAuthAuthenticationToken convert(String authorizationHeader) {
        String headerValue = authorizationHeader.replace(BASIC_AUTH_PREFIX, "");
        String decoded = new String(Base64.getDecoder().decode(headerValue));
        String[] split = decoded.split(":");
        if (split.length != 2) {
            throw new IllegalArgumentException("Wrong Basic auth input data format format");
        }
        String username = split[0];
        String password = split[1];
        return new BasicAuthAuthenticationToken(username, password);
    }

}
