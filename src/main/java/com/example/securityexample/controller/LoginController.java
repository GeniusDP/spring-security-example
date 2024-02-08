package com.example.securityexample.controller;

import com.example.securityexample.dto.LoginRequest;
import com.example.securityexample.dto.TokenResponse;
import com.example.securityexample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @PostMapping("login")
    public TokenResponse login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }

}
