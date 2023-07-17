package com.example.Servletdemofull.application.utils;

import com.example.Servletdemofull.domain.user.RoleEnum;
import com.example.Servletdemofull.infrastructure.output.entity.User;
import com.example.Servletdemofull.infrastructure.output.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserExistInDbTest {

    private UserExistInDb userExistInDb;
    @Mock
    private UserRepository repository;

    @BeforeEach
    void setUp() {
        userExistInDb = new UserExistInDb(repository);
    }

    @Test
    void ifUserExist() {
        String email = "carlitos@gmail.com";
        User user = new User(1, "Carlos", "Carlitos", "carlitos@gmail.com", "$2a$10$TnJ5gupMxWpLtx71eRVAR.5lw36iUXSIRoc1VnEaanz7I2ordWYo6", RoleEnum.ADMIN);

        when(repository.findByEmail(email)).thenReturn(Optional.of(user));

        Boolean response = userExistInDb.ifUserExist(email);

        Assertions.assertEquals(true, response);
    }
}