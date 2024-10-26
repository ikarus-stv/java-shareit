package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.dto.UserUpdateDto;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User addUser(UserCreateDto userCreateDto) {
        User user = UserMapper.userCreateDto2User(userCreateDto);
        return userRepository.addUser(user);
    }

    public User updateUser(Integer userId, UserUpdateDto userUpdateDto) {
        User user = userRepository.getUserById(userId);
        UserMapper.updateUser(user, userUpdateDto);
        return userRepository.updateUser(userId, user);
    }

    public User getUserById(Integer userId) {
        return userRepository.getUserById(userId);
    }

    public void deleteUser(Integer userId) {
        userRepository.deleteUser(userId);
    }
}
