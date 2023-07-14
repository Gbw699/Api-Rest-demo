package com.example.Servletdemofull.application.utils;

import com.example.Servletdemofull.infrastructure.output.entity.User;
import com.example.Servletdemofull.infrastructure.output.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserExistInDb {

    private final UserRepository repository;

    public boolean ifUserExist(String email) {

        Optional<User> user = repository.findByEmail(email);
        return user.isPresent();
    }

}
