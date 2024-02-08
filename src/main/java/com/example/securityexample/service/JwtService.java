package com.example.securityexample.service;

import com.example.securityexample.dto.TokenResponse;
import com.example.securityexample.entity.User;
import com.example.securityexample.util.JwtFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    @Autowired
    JwtFacade jwtFacade;

    public TokenResponse generateAccessToken(User user) {
        String token = jwtFacade.generateAccessJwtToken(user.username());
        return new TokenResponse(token);
    }
}
