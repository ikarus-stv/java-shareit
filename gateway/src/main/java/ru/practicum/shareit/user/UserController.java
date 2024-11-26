package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.dto.UserUpdateDto;

@Controller
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
@Validated

public class UserController {
    private final UserClient userClient;

    @PostMapping
    public ResponseEntity<Object> addUser(@Valid @RequestBody UserCreateDto userCreateDto) {
        log.info("Creating user {}", userCreateDto);
        return userClient.addUser(userCreateDto);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable Integer userId,
                                             @Valid @RequestBody UserUpdateDto userUpdateDto) {
        log.info("Updating user {}", userUpdateDto);
        return userClient.updateUser(userId, userUpdateDto);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUser(@PathVariable Integer userId) {
        log.info("Get user id = {}", userId);
        return userClient.getUserById(userId);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable Integer userId) {
        log.info("Delete user id = {}", userId);
        return userClient.deleteUser(userId);
    }

}
