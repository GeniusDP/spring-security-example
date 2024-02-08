package com.example.securityexample.service;

import com.example.securityexample.dto.LoginRequest;
import com.example.securityexample.dto.TokenResponse;
import com.example.securityexample.entity.User;
import com.example.securityexample.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository repository;

    @Autowired
    JwtService jwtService;

    public TokenResponse login(LoginRequest request) {
        User user = repository.findByUsername(request.username());
        if (!user.password().equals(request.password())) {
            throw new IllegalArgumentException("Password does not match");
        }
        return jwtService.generateAccessToken(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username);
    }

}
