package com.example.Servletdemofull.application.services;

import org.springframework.stereotype.Service;

import com.example.Servletdemofull.config.JWT.JwtService;
import com.example.Servletdemofull.domain.user.RoleEnum;
import com.example.Servletdemofull.infrastructure.output.entity.User;
import com.example.Servletdemofull.infrastructure.output.repository.UserRepository;
import com.example.Servletdemofull.infrastructure.input.rest.dtos.RegisterRequestDto;
import com.example.Servletdemofull.infrastructure.input.rest.dtos.AuthenticationRequestDto;
import com.example.Servletdemofull.infrastructure.input.rest.dtos.AuthenticationResponseDto;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponseDto authenticate(AuthenticationRequestDto requestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.getEmail(),
                        requestDto.getPassword()
                )
        );
        var user = repository.findByEmail(requestDto.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponseDto.builder()
                .token(jwtToken)
                .build();
    }

}
