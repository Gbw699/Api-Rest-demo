package com.example.ApiRestDemo.infrastructure.output.repository;

import com.example.ApiRestDemo.infrastructure.output.entity.User;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
}
