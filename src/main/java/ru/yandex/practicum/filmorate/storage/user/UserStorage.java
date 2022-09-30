package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public interface UserStorage {

    User addUser(User user);

    User updateUser(User updateUser);

    List<User> getAllUser();

    HashMap<Integer, User> getUsers();

    User getUserById(Integer id);

    void deleteAllUser();

    void deleteUser(Integer id);

    Collection<User> getСommonFriends(Integer idUser, Integer idFriend);

    public Collection<User> getUserFriends(Integer id);

    void addToFriends(Integer userId, Integer friendId);

    void removeFromFriends(Integer userId, Integer friendId);
}