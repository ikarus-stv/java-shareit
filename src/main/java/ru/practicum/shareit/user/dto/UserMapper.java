package ru.practicum.shareit.user.dto;

import ru.practicum.shareit.user.User;

public class UserMapper {
    public static UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public static User userCreateDto2User(UserCreateDto uc) {
        return User.builder()
                .name(uc.getName())
                .email(uc.getEmail())
                .build();
    }

    public static void updateUser(User user, UserUpdateDto userUpdateDto) {
        if (userUpdateDto.getName() != null) {
            user.setName(userUpdateDto.getName());
        }

        if (userUpdateDto.getEmail() != null) {
            user.setEmail(userUpdateDto.getEmail());
        }
    }
}
