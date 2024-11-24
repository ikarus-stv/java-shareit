package ru.practicum.shareit.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.dto.UserUpdateDto;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserServiceTest {
    private final UserService userService;

    private UserCreateDto createUserDto(Integer i) {
        return UserCreateDto.builder()
                .name("Username" + i)
                .email("mail" + i + "@mail.com")
                .build();
    }

    private UserUpdateDto updateUserDto(Integer i) {
        return UserUpdateDto.builder()
                .name("Username" + i)
                .email("mail" + i + "@mail.com")
                .build();
    }

    @Test
    void addUserTest() {
        UserCreateDto createUserDto = createUserDto(1);
        User user = userService.addUser(createUserDto);

        assertEquals(createUserDto.getName(), user.getName());
        assertEquals(createUserDto.getEmail(), user.getEmail());
        assertNotNull(user.getId());
    }

    @Test
    void updateUserTest() {
        UserCreateDto createUserDto = createUserDto(1);
        User user = userService.addUser(createUserDto);
        UserUpdateDto updateUserDto = updateUserDto(2);
        User updated = userService.updateUser(user.getId(), updateUserDto);

        assertEquals(updated.getName(), user.getName());
        assertEquals(updated.getEmail(), user.getEmail());

        UserUpdateDto updateUserDto2 = new UserUpdateDto();

        String oldName = user.getName();
        String oldEmail = user.getEmail();
        UserMapper.updateUser(updated, updateUserDto2);
        assertEquals(oldName, user.getName());
        assertEquals(oldEmail, user.getEmail());
    }

    @Test
    void getUserByIdTest() {
        UserCreateDto createUserDto = createUserDto(1);
        User created = userService.addUser(createUserDto);
        User found = userService.getUserById(created.getId());

        assertEquals(created.getName(), found.getName());
        assertEquals(created.getEmail(), found.getEmail());
        assertEquals(created.getId(), found.getId());
    }

    @Test
    void deleteUserTest() {
        UserCreateDto createUserDto1 = createUserDto(1);
        User user = userService.addUser(createUserDto1);

        userService.deleteUser(user.getId());
        userService.deleteUser(-100);
    }

}
