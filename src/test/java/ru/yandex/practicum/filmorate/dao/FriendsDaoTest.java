package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class FriendsDaoTest {
    private final UserDbStorage userbStorage;
    private final FriendsDao friendsDao;
    private User user1;

    private User user1ForEquals;
    private User user1Update;
    private User user2;
    private User user2ForEquals;
    private User userCommon;
    private User userCommonForEquals;

    @BeforeEach
    public void createFilmsTests() {
        user1 = new User("1asasd@asd.ru", "1", "1",
                LocalDate.of(2000, 1, 1));
        user1ForEquals = new User(1, "1asasd@asd.ru", "1", "1",
                LocalDate.of(2000, 1, 1), new HashSet<>(), new HashMap<>(), new HashSet<>());

        user1Update = new User(1, "UPD1asasd@asd.ru", "UPD1", "UPD1",
                LocalDate.of(2000, 1, 1), new HashSet<>(), new HashMap<>(), new HashSet<>());

        user2 = new User("2asasd@asd.ru", "2", "2",
                LocalDate.of(2000, 1, 1));
        user2ForEquals = new User(2, "2asasd@asd.ru", "2", "2",
                LocalDate.of(2000, 1, 1), new HashSet<>(), new HashMap<>(), new HashSet<>());

        userCommon = new User("3asasd@asd.ru", "3", "3",
                LocalDate.of(2000, 1, 1));
        userCommonForEquals = new User(2, "2asasd@asd.ru", "2", "2",
                LocalDate.of(2000, 1, 1), new HashSet<>(), new HashMap<>(), new HashSet<>());

    }

    @Test
    void addToFriends() {
        userbStorage.addUser(user1);
        userbStorage.addUser(user2);
        friendsDao.addToFriends(1, 2);
        assertTrue(userbStorage.getUserFriends(1).size() == 1);
        assertTrue(userbStorage.getUserFriends(2).size() == 0);
        friendsDao.addToFriends(2, 1);
        assertTrue(userbStorage.getUserFriends(1).size() == 1);
        assertTrue(userbStorage.getUserFriends(2).size() == 1);
    }

    @Test
    void removeFromFriends() {
        userbStorage.addUser(user1);
        userbStorage.addUser(user2);
        friendsDao.addToFriends(1, 2);
        friendsDao.addToFriends(2, 1);
        friendsDao.removeFromFriends(1, 2);
        System.out.println(friendsDao.getUserFriends(1).size());
        System.out.println(friendsDao.getUserFriends(2).size());

        assertTrue(friendsDao.getUserFriends(1).size() == 0);
        assertTrue(friendsDao.getUserFriends(2).size() == 1);
        friendsDao.removeFromFriends(2, 1);
        assertTrue(friendsDao.getUserFriends(1).size() == 0);
        assertTrue(friendsDao.getUserFriends(2).size() == 0);
    }

    @Test
    void getUserFriends() {
        userbStorage.addUser(user1);
        userbStorage.addUser(user2);
        friendsDao.addToFriends(1, 2);
        assertTrue(friendsDao.getUserFriends(1).size() == 1);
        assertTrue(friendsDao.getUserFriends(2).size() == 0);
    }

    @Test
    void getСommonFriends() {
        userbStorage.addUser(user1);
        userbStorage.addUser(user2);
        userbStorage.addUser(userCommon);
        friendsDao.addToFriends(1, 3);
        friendsDao.addToFriends(2, 3);
        assertTrue(friendsDao.getСommonFriends(1, 2).size() == 1);
    }
}