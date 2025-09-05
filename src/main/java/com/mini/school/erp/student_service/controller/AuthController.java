package com.mini.school.erp.student_service.controller;

import com.mini.school.erp.student_service.dto.LoginRequest;
import com.mini.school.erp.student_service.dto.LoginResponse;
import com.mini.school.erp.student_service.entity.User;
import com.mini.school.erp.student_service.repository.UserRepository;
import com.mini.school.erp.student_service.security.JwtUtil;
import com.mini.school.erp.student_service.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final org.springframework.security.core.userdetails.UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();

        String token = jwtUtil.generateToken(userDetails, user.getRole().name());

        tokenService.saveToken(user.getUsername(), token, jwtUtil.getExpirationTime());

        return ResponseEntity.ok(new LoginResponse(token, user.getRole().name()));
    }
}
