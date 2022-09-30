package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Service
@Component
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(@Qualifier("userDbStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(Integer idUser, Integer idFriend) {
        userStorage.addToFriends(idUser, idFriend);
    }

    public void removeRomFriend(Integer idUser, Integer idFriend) {
        userStorage.removeFromFriends(idUser, idFriend);
    }

    public Collection<User> getСommonFriends(Integer idUser1, Integer idUser2) {
        return userStorage.getСommonFriends(idUser1, idUser2);
    }

    public Collection<User> getUserFriends(Integer id) {
        return userStorage.getUserFriends(id);
    }

    public List<User> getAllUser() {
        return userStorage.getAllUser();
    }

    public User createUser(User user) {
        return userStorage.addUser(user);
    }

    public User updateUser(User updateUser) {
        return userStorage.updateUser(updateUser);
    }

    public User getUserById(Integer id) {
        return userStorage.getUserById(id);
    }

    public HashMap<Integer, User> getUsers() {
        return userStorage.getUsers();
    }

    public void deleteAllUser() {
        userStorage.deleteAllUser();
    }

    public void deleteUser(Integer id) {
        userStorage.deleteUser(id);
    }
}
