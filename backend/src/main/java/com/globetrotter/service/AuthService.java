package com.globetrotter.service;

import com.globetrotter.dto.JwtAuthResponse;
import com.globetrotter.dto.LoginRequest;
import com.globetrotter.dto.SignupRequest;
import com.globetrotter.model.User;
import com.globetrotter.repository.UserRepository;
import com.globetrotter.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    public JwtAuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication);

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new JwtAuthResponse(token, "Bearer", user.getId(), user.getEmail(), user.getName());
    }

    public JwtAuthResponse signup(SignupRequest signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(signupRequest.getName());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        user = userRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signupRequest.getEmail(),
                        signupRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication);

        return new JwtAuthResponse(token, "Bearer", user.getId(), user.getEmail(), user.getName());
    }
}

