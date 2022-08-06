package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserInvalidUpdateId;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;

import static ru.yandex.practicum.filmorate.tools.ModelTools.nextIndex;
import static ru.yandex.practicum.filmorate.tools.ModelTools.validateUser;

@RestController
public class UserController {

    private final HashMap<Integer, User> users = new HashMap<>();
    private Integer userIndex = 0;
    private final static Logger log = LoggerFactory.getLogger(ru.yandex.practicum.filmorate.controller.FilmController.class);

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
