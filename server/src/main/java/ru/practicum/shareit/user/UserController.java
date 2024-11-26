package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.dto.UserUpdateDto;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    final UserService userService;

    @PostMapping
    public User addUser(@Valid @RequestBody UserCreateDto userCreateDto) {
        return userService.addUser(userCreateDto);
    }

    @PatchMapping("/{userId}")
    public User updateUser(@PathVariable Integer userId, @Valid @RequestBody UserUpdateDto userUpdateDto) {
        return userService.updateUser(userId, userUpdateDto);
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable Integer userId) {
        User user = userService.getUserById(userId);
        UserDto userDto = UserMapper.toUserDto(user);
        return userDto;
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
    }


}
