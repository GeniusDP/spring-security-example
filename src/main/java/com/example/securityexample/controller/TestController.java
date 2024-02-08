package com.example.securityexample.controller;

import com.example.securityexample.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("admin-url")
    public String adminOnly() {
        return "admin-ony resource";
    }

    @GetMapping("allowed-to-all")
    public String allowedToAll() {
        return "allowed to all";
    }

    @GetMapping("authenticated")
    public String authenticated(@AuthenticationPrincipal User user) {
        return "resource for authenticated user " + user;
    }

}
