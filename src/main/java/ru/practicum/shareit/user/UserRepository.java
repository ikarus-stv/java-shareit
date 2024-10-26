package ru.practicum.shareit.user;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.DuplicatedDataException;
import ru.practicum.shareit.exception.NotFoundException;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserRepository {
    private Map<Integer, User> userMap = new HashMap<>();
    private Integer maxId = 0;

    public User addUser(User user) {

        if (!userMap.values().stream().noneMatch(user1 -> user1.getEmail().equals(user.getEmail()))) {
            throw new DuplicatedDataException("Пользователь с email=" + user.getEmail() + " уже есть");
        }

        maxId++;
        user.setId(maxId);
        userMap.put(maxId, user);
        return user;
    }

    public User getUserById(Integer userId) {
        if (!userMap.containsKey(userId)) {
            throw new NotFoundException("Нет пользователя с id=" + userId);
        }
        return userMap.get(userId);
    }

    public User updateUser(Integer userId, User user) {
        user.setId(userId);
        if (userMap.containsKey(userId)) {

            if (!userMap.values().stream().noneMatch(user1 -> !user1.getId().equals(userId) && user1.getEmail().equals(user.getEmail()))) {
                throw new DuplicatedDataException("Пользователь с email=" + user.getEmail() + " уже есть");
            }

            return userMap.put(userId, user);
        } else {
            throw new NotFoundException("Ошибка обновления: Нет пользователя с id = " + userId);
        }
    }

    public void deleteUser(Integer userId) {
        if (userMap.containsKey(userId)) {
            userMap.remove(userId);
        } else {
            throw new NotFoundException("Ошибка обновления: Нет пользователя с id = " + userId);
        }
    }
}
