package com.mb.ninjabank.security.controllers;

import com.mb.ninjabank.security.services.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController()
@RequestMapping("/api/v0/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private  final SecurityService securityService;
    @PostMapping("/token")
    public ResponseEntity<Map<String, String>> getToken(String username, String password){
        final Map<String, String> token = securityService.getToken(username, password);
        return ResponseEntity.ok(token);
    }
}
