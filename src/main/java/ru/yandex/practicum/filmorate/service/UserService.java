package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class UserService {
    InMemoryUserStorage inMemoryUserStorage;

    @Autowired

    public UserService(InMemoryUserStorage inMemoryUserStorage) {

        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public void addFriend(Integer idUser, Integer idFriend) {


        User user = inMemoryUserStorage.getUserById(idUser);
        Set<Integer> friendsOfUser = user.getFriends();
        friendsOfUser.add(idFriend);
        user.setFriends(friendsOfUser);
        inMemoryUserStorage.updateUser(user);

        User userFriend = inMemoryUserStorage.getUserById(idFriend);
        Set<Integer> friendsOfFriendUser = userFriend.getFriends();
        friendsOfFriendUser.add(idUser);
        userFriend.setFriends(friendsOfFriendUser);
        inMemoryUserStorage.updateUser(userFriend);


    }

    public void deleteFriend(Integer idUser, Integer idFriend) {
        User user = inMemoryUserStorage.getUserById(idUser);
        Set<Integer> friendsOfUser = user.getFriends();
        friendsOfUser.remove(idFriend);
        user.setFriends(friendsOfUser);
        inMemoryUserStorage.updateUser(user);

        User userFriend = inMemoryUserStorage.getUserById(idFriend);
        Set<Integer> friendsOfFriendUser = userFriend.getFriends();
        friendsOfFriendUser.remove(idUser);
        userFriend.setFriends(friendsOfFriendUser);
        inMemoryUserStorage.updateUser(userFriend);
    }

    public List<Integer> getMutualFriend(Integer idUser1, Integer idUser2) {
        User user = inMemoryUserStorage.getUserById(idUser1);
        User user2 = inMemoryUserStorage.getUserById(idUser2);
        List<Integer> mutualFriend = new ArrayList<>();

        for (Integer IdFriendsUser1 : user.getFriends()) {
            if (user2.getFriends().contains(IdFriendsUser1)) {
                mutualFriend.add(IdFriendsUser1);
            }
        }
        return mutualFriend;
    }

    public Set<Integer> getFriend(Integer id) {
        User user = inMemoryUserStorage.getUserById(id);
        Set<Integer> friend = user.getFriends();
        return friend;
    }


}
