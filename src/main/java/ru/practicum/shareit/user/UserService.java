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
        user = userRepository.save(user);
        return user;
    }

    public User updateUser(Integer userId, UserUpdateDto userUpdateDto) {
        User user = userRepository.getReferenceById(userId);
        UserMapper.updateUser(user, userUpdateDto);
        user.setId(userId);
        return userRepository.save(user);
    }

    public User getUserById(Integer userId) {
        return userRepository.getReferenceById(userId);
    }

    public void deleteUser(Integer userId) {
        User user = userRepository.getReferenceById(userId);
        userRepository.delete(user);
    }
}
