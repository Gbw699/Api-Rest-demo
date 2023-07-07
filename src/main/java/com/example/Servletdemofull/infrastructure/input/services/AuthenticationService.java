package com.example.Servletdemofull.infrastructure.input.services;

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

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponseDto register(RegisterRequestDto requestDto) {
        var user = User.builder()
                .firstname(requestDto.getFirstname())
                .lastname(requestDto.getLastname())
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .role(RoleEnum.USER)
                .build();
        repository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponseDto.builder()
                .token(jwtToken)
                .build();
    }

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
