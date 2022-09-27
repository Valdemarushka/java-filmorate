package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@RestController
public class UserController {
    private final HashMap<Integer, User> users = new HashMap<>();
    private Integer userIndex = 0;
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> findAllUser() {
        return userService.getAllUser();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable("id") Integer id) {
        return userService.getUserById(id);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void addFriends(@PathVariable("id") Integer id, @PathVariable("friendId") Integer friendId) {
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteFriends(@PathVariable("id") Integer id, @PathVariable("friendId") Integer friendId) {
        userService.removeRomFriend(id, friendId);
    }

    @GetMapping("/users/{id}/friends")
    public Collection<User> getFriends(@PathVariable("id") Integer id) {
        return userService.getUserFriends(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public Collection<User> getСommonFriend(@PathVariable("id") Integer id, @PathVariable("otherId") Integer otherId) {
        return userService.getСommonFriends(id, otherId);
    }


    @PostMapping(value = "/users")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);

    }

    @PutMapping(value = "/users")
    public User updateUser(@RequestBody User updateUser) {
        return userService.updateUser(updateUser);
    }


}
