package com.example.securityexample.config.security.authentication;

import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class BasicAuthAuthenticationToken implements Authentication {

    private final String username;
    private final String password;

    public BasicAuthAuthenticationToken(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public Object getCredentials() {
        return password;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public String getPrincipal() {
        return username;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) {
        throw new IllegalStateException("cannot make BasicAuthAuthenticationToken trusted");
    }

    @Override
    public String getName() {
        return username;
    }
}
