package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

import static ru.yandex.practicum.filmorate.tools.ModelTools.*;

@Slf4j
@Component("inMemoryUserStorage")
public class InMemoryUserStorage implements UserStorage {

    private final HashMap<Integer, User> users = new HashMap<>();
    private Integer userIndex = 0;

    private int nextIndex() {
        return ++userIndex;
    }

    @Override
    public List<User> getAllUser() {
        log.debug("Текущее количество юзеров: {}", users.size());
        return new ArrayList<User>(users.values());
    }

    @Override
    public User addUser(User user) {
        log.debug("Создаем юзера");
        validateUser(user);
        usersNotNull(users);
        user.setId(nextIndex());
        users.put(user.getId(), user);
        log.debug("Юзер создан");
        return users.get(user.getId());
    }

    @Override
    public User updateUser(User updateUser) {
        validateUser(updateUser);
        usersContainsIdAndNotNull(users, updateUser.getId());
        users.put(updateUser.getId(), updateUser);
        log.debug("Юзер обновлен");
        return users.get(updateUser.getId());
    }

    @Override
    public User getUserById(Integer id) {
        usersContainsIdAndNotNull(users, id);
        log.debug("Возвращен юзер с id{}", id);
        return users.get(id);
    }

    @Override
    public HashMap<Integer, User> getUsers() {
        usersNotNull(users);
        return users;
    }

    @Override
    public void deleteAllUser() {
        usersNotNull(users);
        users.clear();
        log.debug("Удалены все юзеры");
    }

    @Override
    public void deleteUser(Integer id) {
        usersContainsIdAndNotNull(users, id);
        users.remove(id);
        log.debug("Удален юзер с id{}", id);
    }

    @Override
    public Set<User> getСommonFriends(Integer idUser1, Integer idUser2) {
        idValidator(idUser1);
        idValidator(idUser2);
        User user = getUserById(idUser1);
        userNotNull(user);
        User user2 = getUserById(idUser2);
        userNotNull(user2);
        Set<User> сommonFriends = new HashSet<>();
        if (user.getFriends() == null || user2.getFriends() == null) {
            return сommonFriends;
        }
        for (Integer IdFriendsUser1 : user.getFriends()) {
            if (user2.getFriends().contains(IdFriendsUser1)) {
                сommonFriends.add(getUserById(IdFriendsUser1));
            }
        }
        return сommonFriends;
    }

    @Override
    public Set<User> getUserFriends(Integer id) {
        idValidator(id);
        User user = getUserById(id);
        userNotNull(user);
        Set<User> setOfFriends = new HashSet<>();
        if (user.getFriends() == null) {
            return setOfFriends;
        } else {
            for (int idFriend : user.getFriends()) {
                setOfFriends.add(getUserById(idFriend));
            }
            return setOfFriends;
        }
    }

    @Override
    public void addToFriends(Integer userId, Integer friendId) {
        idValidator(userId);
        idValidator(friendId);
        addFriendOneDirection(userId, friendId);
        addFriendOneDirection(friendId, userId);
    }

    @Override
    public void removeFromFriends(Integer userId, Integer friendId) {
        idValidator(userId);
        idValidator(friendId);
        removeFriendOneDirection(userId, friendId);
        removeFriendOneDirection(friendId, userId);
    }

    public void addFriendOneDirection(Integer idUser1, Integer idUser2) {
        User user = getUserById(idUser1);
        userNotNull(user);
        Set<Integer> friendsOfUser = user.getFriends();
        friendsOfUser.add(idUser2);
        user.setFriends(friendsOfUser);
        updateUser(user);
    }

    public void removeFriendOneDirection(Integer idUser1, Integer idUser2) {
        User user = getUserById(idUser1);
        userNotNull(user);
        Set<Integer> friendsOfUser = user.getFriends();
        if (friendsOfUser.contains(idUser2)) {
            friendsOfUser.remove(idUser2);
            user.setFriends(friendsOfUser);
            updateUser(user);
        }
    }
}
