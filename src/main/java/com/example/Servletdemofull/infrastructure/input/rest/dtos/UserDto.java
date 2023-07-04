package com.example.Servletdemofull.infrastructure.input.rest.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {

    private UUID id;

    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "Lastname cannot be null")
    private String lastName;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Email must be valid")
    private String email;

    @Size(
            min = 10,
            max = 14,
            message = "Cellphone numbers must contain between 10 and 14 characters")
    private String cellphoneNumber;
}
