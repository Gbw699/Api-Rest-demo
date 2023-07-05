package com.example.Servletdemofull.infrastructure.input.rest.controllers;

import com.example.Servletdemofull.infrastructure.input.rest.dtos.UserDto;
import com.example.Servletdemofull.infrastructure.input.rest.mappers.UserMapper;
import com.example.Servletdemofull.infrastructure.output.entity.User;
import com.example.Servletdemofull.infrastructure.output.repository.UserRepository;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@Validated
@RequestMapping("/api/user")
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final Logger logger;

    public UserController(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.logger = LoggerFactory.getLogger(getClass());
    }

    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers() {

        Optional<List<User>> users = Optional.of(userRepository.findAll());

        logger.debug("Output {}", users.get().toString());

        if (users.get().isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity
                .ok()
                .body(users.get());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {

        logger.debug("Input parameters id {}", id);

        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) return ResponseEntity.notFound().build();

        logger.debug("Output {}", user.get().toString());

        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto) {

        User user = userMapper.fromUserDto(userDto);
        userRepository.save(user);

        logger.debug("Output {}", userDto.toString());

        return ResponseEntity.ok().body(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@RequestBody @Valid UserDto userDto, @PathVariable UUID id) {

        logger.debug("Input parameters id {}, body {}", id, userDto.toString());

        Optional<User> userToUpdate = userRepository.findById(id);

        if (userToUpdate.isEmpty())
            return new ResponseEntity<>("No se encotró el usuario en la BDD", HttpStatus.NOT_FOUND);

        User user = userToUpdate.get();
        user = userMapper.fromUserDto(userDto);
        userRepository.save(user);

        logger.debug("Output {}", userDto.toString());

        return ResponseEntity.ok().body("El usuario se editó correctamente en la BDD" +
                "");
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteAllUsers() {

        userRepository.deleteAll();

        return new ResponseEntity<>("Se eliminaron todos los usarios de la BDD", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable UUID id) {

        logger.debug("Input parameters id {}", id);

        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) return new ResponseEntity<>("No se encontró el usaurio en la BDD", HttpStatus.NOT_FOUND);

        userRepository.deleteById(id);

        return new ResponseEntity<>("El usuario se borró exitosamente de la BDD", HttpStatus.OK);
    }
}
