package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static ru.yandex.practicum.filmorate.tools.ModelTools.userNotNull;
import static ru.yandex.practicum.filmorate.tools.ModelTools.usersContainsIdAndNotNull;

@Component
public class UserService {
    InMemoryUserStorage inMemoryUserStorage;

    @Autowired
    public UserService(InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public void addFriend(Integer idUser, Integer idFriend) {
        usersContainsIdAndNotNull(inMemoryUserStorage.getUsers(), idUser);
        usersContainsIdAndNotNull(inMemoryUserStorage.getUsers(), idFriend);
        addFriendOneDirection(idUser, idFriend);
        addFriendOneDirection(idFriend, idUser);
    }

    public void addFriendOneDirection(Integer idUser1, Integer idUser2) {
        User user = inMemoryUserStorage.getUserById(idUser1);
        userNotNull(user);
        Set<Integer> friendsOfUser = user.getFriends();
        friendsOfUser.add(idUser2);
        user.setFriends(friendsOfUser);
        inMemoryUserStorage.updateUser(user);
    }

    public void deleteFriend(Integer idUser, Integer idFriend) {
        usersContainsIdAndNotNull(inMemoryUserStorage.getUsers(), idUser);
        usersContainsIdAndNotNull(inMemoryUserStorage.getUsers(), idFriend);
        deleteFriendOneDirection(idUser, idFriend);
        deleteFriendOneDirection(idFriend, idUser);
    }

    public void deleteFriendOneDirection(Integer idUser1, Integer idUser2) {
        User user = inMemoryUserStorage.getUserById(idUser1);
        userNotNull(user);
        Set<Integer> friendsOfUser = user.getFriends();
        if (friendsOfUser.contains(idUser2)) {
            friendsOfUser.remove(idUser2);
            user.setFriends(friendsOfUser);
            inMemoryUserStorage.updateUser(user);
        }
    }

    public Set<User> getСommonFriends(Integer idUser1, Integer idUser2) {
        usersContainsIdAndNotNull(inMemoryUserStorage.getUsers(), idUser1);
        User user = inMemoryUserStorage.getUserById(idUser1);
        userNotNull(user);
        usersContainsIdAndNotNull(inMemoryUserStorage.getUsers(), idUser2);
        User user2 = inMemoryUserStorage.getUserById(idUser2);
        userNotNull(user2);
        Set<User> сommonFriends = new HashSet<>();
        if (user.getFriends() == null || user2.getFriends() == null) {
            return сommonFriends;
        }
        for (Integer IdFriendsUser1 : user.getFriends()) {
            if (user2.getFriends().contains(IdFriendsUser1)) {
                сommonFriends.add(inMemoryUserStorage.getUserById(IdFriendsUser1));
            }
        }
        return сommonFriends;
    }

    public Set<User> getFriend(Integer id) {
        usersContainsIdAndNotNull(inMemoryUserStorage.getUsers(), id);
        User user = inMemoryUserStorage.getUserById(id);
        userNotNull(user);
        Set<User> setOfFriends = new HashSet<>();
        if (user.getFriends() == null) {
            return setOfFriends;
        } else {
            for (int idFriend : user.getFriends()) {
                setOfFriends.add(inMemoryUserStorage.getUserById(idFriend));
            }
            return setOfFriends;
        }
    }

    public List<User> getAllUser() {
        return inMemoryUserStorage.getAllUser();
    }

    public User createUser(User user) {
        return inMemoryUserStorage.createUser(user);
    }

    public User updateUser(User updateUser) {
        return inMemoryUserStorage.updateUser(updateUser);
    }

    public User getUserById(Integer id) {
        return inMemoryUserStorage.getUserById(id);
    }

    public HashMap<Integer, User> getUsers() {
        return inMemoryUserStorage.getUsers();
    }

    public void deleteAllUser() {
        inMemoryUserStorage.deleteAllUser();
    }

    public void deleteUser(Integer id) {
        inMemoryUserStorage.deleteUser(id);
    }
}
