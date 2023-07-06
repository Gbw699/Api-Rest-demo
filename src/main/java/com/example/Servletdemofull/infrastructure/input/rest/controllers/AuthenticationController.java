package com.example.Servletdemofull.infrastructure.input.rest.controllers;

import com.example.Servletdemofull.infrastructure.input.rest.dtos.AuthenticationRequestDto;
import com.example.Servletdemofull.infrastructure.input.rest.dtos.AuthenticationResponseDto;
import com.example.Servletdemofull.infrastructure.input.rest.dtos.RegisterRequestDto;
import com.example.Servletdemofull.infrastructure.input.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> Register(
            @RequestBody RegisterRequestDto request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponseDto> Register(
            @RequestBody AuthenticationRequestDto request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
