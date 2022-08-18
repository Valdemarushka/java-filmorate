package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.util.Collection;
import java.util.HashMap;

@RestController
@Slf4j
public class UserController {
    private final HashMap<Integer, User> users = new HashMap<>();
    private Integer userIndex = 0;
    InMemoryUserStorage inMemoryUserStorage;
    UserService userService;

    public UserController(InMemoryUserStorage inMemoryUserStorage, UserService userService) {
        this.inMemoryUserStorage = inMemoryUserStorage;
        this.userService = userService;
    }

    @GetMapping("/users")
    public Collection<User> findAllUser() {
        return inMemoryUserStorage.findAllUser();
    }

    @PostMapping(value = "/users")
    public User createUser(@RequestBody User user) {
        return inMemoryUserStorage.createUser(user);
    }

    @PutMapping(value = "/users")
    public User updateUser(@RequestBody User updateUser) {
        return inMemoryUserStorage.updateUser(updateUser);
    }
}
