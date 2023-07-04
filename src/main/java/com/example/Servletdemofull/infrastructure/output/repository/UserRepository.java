package com.example.Servletdemofull.infrastructure.output.repository;

import com.example.Servletdemofull.infrastructure.output.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
}
