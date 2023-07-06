package com.example.Servletdemofull.infrastructure.output.repository;

import com.example.Servletdemofull.infrastructure.output.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
}
