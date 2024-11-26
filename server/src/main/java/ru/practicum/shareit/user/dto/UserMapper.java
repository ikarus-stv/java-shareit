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
        User  result = new User();
        result.setName(uc.getName());
        result.setEmail(uc.getEmail());
        return result;
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
