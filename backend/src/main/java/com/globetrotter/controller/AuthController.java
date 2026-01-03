package com.globetrotter.controller;

import com.globetrotter.dto.JwtAuthResponse;
import com.globetrotter.dto.LoginRequest;
import com.globetrotter.dto.SignupRequest;
import com.globetrotter.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        JwtAuthResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthResponse> signup(@Valid @RequestBody SignupRequest signupRequest) {
        JwtAuthResponse response = authService.signup(signupRequest);
        return ResponseEntity.ok(response);
    }
}

