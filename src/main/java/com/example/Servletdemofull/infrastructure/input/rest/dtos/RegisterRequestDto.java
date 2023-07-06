package com.example.Servletdemofull.infrastructure.input.rest.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {

    private String firstname;

    private String lastname;

    private String email;

    private String password;
}
