package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.List;

public interface UserStorage {

    User createUser(User user);

    User updateUser(User updateUser);

    List<User> getAllUser();

    HashMap<Integer, User> getUsers();

    User getUserById(Integer id);

    void deleteAllUser();

    void deleteUser(Integer id);
}