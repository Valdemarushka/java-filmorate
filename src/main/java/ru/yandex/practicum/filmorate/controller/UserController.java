package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserInvalidUpdateId;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;

import static ru.yandex.practicum.filmorate.tools.ModelTools.nextIndex;
import static ru.yandex.practicum.filmorate.tools.ModelTools.validateUser;

@RestController
@Slf4j
public class UserController {
    private final HashMap<Integer, User> users = new HashMap<>();
    private Integer userIndex = 0;

    @GetMapping("/users")
    public Collection<User> findAllUser() {
        log.debug("Текущее количество юзеров: {}", users.size());
        return users.values();
    }

    @PostMapping(value = "/users")
    public User createUser(@RequestBody User user) {
        validateUser(user);
        user.setId(nextIndex(userIndex));
        users.put(user.getId(), user);
        log.debug("Юзер создан", user.toString());
        return users.get(user.getId());
    }

    @PutMapping(value = "/users")
    public User updateUser(@RequestBody User updateUser) {
        validateUser(updateUser);

        if (users.containsKey(updateUser.getId())) {
            users.put(updateUser.getId(), updateUser);
            log.debug("Юзер создан новый", updateUser.toString());
            return users.get(updateUser.getId());

        } else {
            throw new UserInvalidUpdateId("Такого Id для обновления не существует");
        }
    }
}
