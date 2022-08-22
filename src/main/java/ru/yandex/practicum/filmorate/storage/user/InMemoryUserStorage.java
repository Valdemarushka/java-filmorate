package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserInvalidUpdateId;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static ru.yandex.practicum.filmorate.tools.ModelTools.nextIndex;
import static ru.yandex.practicum.filmorate.tools.ModelTools.validateUser;

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
        validateUser(user);
        user.setId(nextIndex(userIndex));
        users.put(user.getId(), user);
        log.debug("Юзер создан");
        return users.get(user.getId());
    }

    @Override
    public User updateUser(User updateUser) {
        validateUser(updateUser);

        if (users.containsKey(updateUser.getId())) {
            users.put(updateUser.getId(), updateUser);
            log.debug("Юзер обновлен");
            return users.get(updateUser.getId());
        } else {
            throw new UserInvalidUpdateId("Такого Id для обновления не существует");
        }
    }

    @Override
    public User getUserById(Integer id) {
        log.debug("Возвращен юзер с id{}", id);
        return users.get(id);
    }

    @Override
    public void deleteAllUser() {
        users.clear();
        log.debug("Удалены все юзеры");
    }

    @Override
    public void deleteUser(Integer id) {
        users.remove(id);
        log.debug("Удален юзер с id{}", id);
    }
}

