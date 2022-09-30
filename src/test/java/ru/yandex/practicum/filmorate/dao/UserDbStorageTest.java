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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserDbStorageTest {

    private final UserDbStorage userbStorage;
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

    static void equalUser(User user1, User user2) {
        assertEquals(user1.getId(), user2.getId());
        assertEquals(user1.getEmail(), user2.getEmail());
        assertEquals(user1.getName(), user2.getName());
        assertEquals(user1.getLogin(), user2.getLogin());
        assertEquals(user1.getBirthday(), user2.getBirthday());
        assertEquals(user1.getFriends(), user2.getFriends());
        assertEquals(user1.getFriendsStatus(), user2.getFriendsStatus());
        assertEquals(user1.getLikedFilms(), user2.getLikedFilms());
    }

    @Test
    void createUser() {
        userbStorage.addUser(user1);
        equalUser(user1ForEquals, userbStorage.getUserById(1));
    }

    @Test
    void updateUser() {
        userbStorage.addUser(user1);
        userbStorage.updateUser(user1Update);
        equalUser(user1Update, userbStorage.getUserById(1));
    }

    @Test
    void getAllUser() {
        userbStorage.addUser(user1);
        assertTrue(userbStorage.getAllUser().size() == 1);
    }

    @Test
    void getUsers() {

    }

    @Test
    void getUserById() {
        userbStorage.addUser(user1);
        equalUser(user1ForEquals, userbStorage.getUserById(1));
    }

    @Test
    void deleteAllUser() {
        userbStorage.addUser(user1);
        userbStorage.addUser(user1);
        userbStorage.deleteAllUser();
        assertTrue(userbStorage.getAllUser().size() == 0);
    }

    @Test
    void deleteUser() {
        userbStorage.addUser(user1);
        userbStorage.deleteUser(1);
        assertTrue(userbStorage.getAllUser().size() == 0);
    }

    @Test
    void getСommonFriends() {
        userbStorage.addUser(user1);
        userbStorage.addUser(user2);
        userbStorage.addUser(userCommon);
        userbStorage.addToFriends(1, 3);
        userbStorage.addToFriends(2, 3);
        assertTrue(userbStorage.getСommonFriends(1, 2).size() == 1);
    }

    @Test
    void getUserFriends() {
        userbStorage.addUser(user1);
        userbStorage.addUser(user2);
        userbStorage.addToFriends(1, 2);
        assertTrue(userbStorage.getUserFriends(1).size() == 1);
        assertTrue(userbStorage.getUserFriends(2).size() == 0);
    }

    @Test
    void addToFriends() {
        userbStorage.addUser(user1);
        userbStorage.addUser(user2);
        userbStorage.addToFriends(1, 2);
        assertTrue(userbStorage.getUserFriends(1).size() == 1);
        assertTrue(userbStorage.getUserFriends(2).size() == 0);
        userbStorage.addToFriends(2, 1);
        assertTrue(userbStorage.getUserFriends(1).size() == 1);
        assertTrue(userbStorage.getUserFriends(2).size() == 1);


    }

    @Test
    void removeFromFriends() {
        userbStorage.addUser(user1);
        userbStorage.addUser(user2);
        userbStorage.addToFriends(1, 2);
        userbStorage.addToFriends(2, 1);
        userbStorage.removeFromFriends(1, 2);
        System.out.println(userbStorage.getUserFriends(1).size());
        System.out.println(userbStorage.getUserFriends(2).size());

        assertTrue(userbStorage.getUserFriends(1).size() == 0);
        assertTrue(userbStorage.getUserFriends(2).size() == 1);
        userbStorage.removeFromFriends(2, 1);
        assertTrue(userbStorage.getUserFriends(1).size() == 0);
        assertTrue(userbStorage.getUserFriends(2).size() == 0);

    }
}