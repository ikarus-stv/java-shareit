package ru.practicum.shareit.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserController;
import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.dto.UserUpdateDto;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void addUserTest() throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("22");
        user.setEmail("aaa@aaa");

        UserCreateDto createDto = new UserCreateDto();
        createDto.setName(user.getName());
        createDto.setEmail(user.getEmail());

        when(userService.addUser(any()))
                .thenReturn(user);

        mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(createDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(user.getId()), Integer.class))
                .andExpect(jsonPath("$.name", Matchers.is(user.getName())))
                .andExpect(jsonPath("$.email", Matchers.is(user.getEmail())));

        verify(userService, times(1))
                .addUser(any());
    }

    @Test
    void updateUserTest() throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("22");
        user.setEmail("aaa@aaa");

        UserUpdateDto userUpdateDto = new UserUpdateDto();
        userUpdateDto.setName(user.getName());
        userUpdateDto.setEmail(user.getEmail());

        when(userService.updateUser(any(), any()))
                .thenReturn(user);

        mockMvc.perform(patch("/users/1")
                        .content(objectMapper.writeValueAsString(userUpdateDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(user.getId()), Integer.class))
                .andExpect(jsonPath("$.name", Matchers.is(user.getName())))
                .andExpect(jsonPath("$.email", Matchers.is(user.getEmail())));

        verify(userService, times(1))
                .updateUser(any(), any());
    }


    @Test
    void getUserByIdTest() throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("22");
        user.setEmail("aaa@aaa");

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());

        when(userService.getUserById(any()))
                .thenReturn(user);

        mockMvc.perform(get("/users/1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(userDto.getId()), Integer.class))
                .andExpect(jsonPath("$.name", Matchers.is(userDto.getName())))
                .andExpect(jsonPath("$.email", Matchers.is(userDto.getEmail())));

        verify(userService, times(1))
                .getUserById(any());
    }

    @Test
    void deleteUserTest() throws Exception {
        mockMvc.perform(delete("/users/1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk());

        verify(userService, times(1))
                .deleteUser(any());
    }
}
