package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static ru.yandex.practicum.filmorate.tools.ModelTools.*;


@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    private final HashMap<Integer, User> users = new HashMap<>();
    private Integer userIndex = 0;


    @Override
    public List<User> getAllUser() {
        log.debug("Текущее количество юзеров: {}", users.size());
        return new ArrayList<User>(users.values());
    }

    @Override
    public User createUser(User user) {
        log.debug("Создаем юзера");
        validateUser(user);
        usersNotNull(users);
        user.setId(nextIndex(userIndex));
        users.put(user.getId(), user);
        log.debug("Юзер создан");
        return users.get(user.getId());
    }

    @Override
    public User updateUser(User updateUser) {
        validateUser(updateUser);
        usersContainsId(users, updateUser.getId());
        users.put(updateUser.getId(), updateUser);
        log.debug("Юзер обновлен");
        return users.get(updateUser.getId());
    }

    @Override
    public User getUserById(Integer id) {
        usersContainsId(users, id);
        log.debug("Возвращен юзер с id{}", id);
        return users.get(id);
    }

    @Override
    public void deleteAllUser() {
        usersNotNull(users);
        users.clear();
        log.debug("Удалены все юзеры");
    }

    @Override
    public void deleteUser(Integer id) {
        usersContainsId(users, id);
        users.remove(id);
        log.debug("Удален юзер с id{}", id);
    }
}

