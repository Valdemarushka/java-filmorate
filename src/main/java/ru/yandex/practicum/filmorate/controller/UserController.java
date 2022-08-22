package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
    public List<User> findAllUser() {
        return inMemoryUserStorage.getAllUser();
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable("id") Integer id) {
        return inMemoryUserStorage.getUserById(id);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void addFriends(@PathVariable("id") Integer id, @PathVariable("friendId") Integer friendId) {
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteFriends(@PathVariable("id") Integer id, @PathVariable("friendId") Integer friendId) {
        userService.deleteFriend(id, friendId);
    }

    @GetMapping("/user/{id}/friends")
    public Set<Integer> getFriends(@PathVariable("id") Integer id) {
        return userService.getFriend(id);
    }

    @GetMapping("/user/{id}/friends/common/{otherId}")
    public List<Integer> getMutualFriend(@PathVariable("id") Integer id, @PathVariable("otherId") Integer otherId) {
        return userService.getMutualFriend(id, otherId);
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
