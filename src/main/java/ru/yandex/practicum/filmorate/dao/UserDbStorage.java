package ru.yandex.practicum.filmorate.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static ru.yandex.practicum.filmorate.requests.FriendsDbRequest.sqlGetFriends;
import static ru.yandex.practicum.filmorate.requests.FriendsDbRequest.sqlGetFriendsStatus;
import static ru.yandex.practicum.filmorate.requests.UserDbRequest.*;
import static ru.yandex.practicum.filmorate.tools.ModelTools.*;

@Slf4j
@Repository("userDbStorage")
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;
    private final FriendsDao friendDao;

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate, FriendsDao friendDao) {
        this.friendDao = friendDao;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User addUser(User user) {
        validateUser(user);
        jdbcTemplate.update(sqlCreateUser, user.getEmail(), user.getName(), user.getLogin(), user.getBirthday());
        SqlRowSet userRows = jdbcTemplate.queryForRowSet(sqlGetUserId, user.getEmail());
        if (userRows.next()) {
            log.debug("UserDbStorage");
            return new User(userRows.getInt("id_user"), user.getEmail(), user.getName(), user.getLogin(),
                    user.getBirthday());
        }
        return user;
    }

    @Override
    public User updateUser(User updateUser) {
        validateUser(updateUser);
        userIdValidator(updateUser);
        jdbcTemplate.update(sqlUpdateUser, updateUser.getEmail(), updateUser.getName(), updateUser.getLogin(),
                updateUser.getBirthday(), updateUser.getId());
        return updateUser;
    }

    @Override
    public List<User> getAllUser() {
        List<User> userList = jdbcTemplate.query(sqlGetAllUser, (rs, rowNum) -> makeUser(rs));
        return userList;
    }

    private User makeUser(ResultSet rs) throws SQLException {
        Integer userId = rs.getInt("id_user");
        String email = rs.getString("email");
        String name = rs.getString("name");
        String login = rs.getString("login");
        LocalDate birthday = rs.getDate("birthday").toLocalDate();

        HashSet<Integer> friends = new HashSet<>(jdbcTemplate.query(sqlGetFriends,
                (rs1, rowNum) -> (rs1.getInt("id_friend")), userId));
        HashMap<Integer, Integer> friendStatus = new HashMap<>();
        SqlRowSet friendsRowSet = jdbcTemplate.queryForRowSet(sqlGetFriendsStatus, userId);
        if (friendsRowSet.next()) {
            friendStatus.put(friendsRowSet.getInt("id_friend"), friendsRowSet.getInt("id_status"));
        }
        HashSet<Integer> likedFilms = new HashSet<>(jdbcTemplate.query(sqlGetLike,
                (rs3, rowNum) -> (rs3.getInt("id_film")), userId));
        return new User(userId, email, name, login, birthday, friends, friendStatus, likedFilms);
    }

    public HashMap<Integer, User> getUsers() {
        return null;
    }

    @Override
    public User getUserById(Integer userId) {
        idValidator(userId);
        return jdbcTemplate.query(sqlGetUserById, (rs, rowNum) -> makeUser(rs), userId).get(0);
    }

    @Override
    public void deleteAllUser() {
        List<User> userList = jdbcTemplate.query(sqlGetAllUser, (rs, rowNum) -> makeUser(rs));
        for (User user : userList) {
            jdbcTemplate.update(sqlDeleteUser, user.getId());
        }
    }

    @Override
    public void deleteUser(Integer id) {
        jdbcTemplate.update(sqlDeleteUser, id);
    }

    @Override
    public Collection<User> getСommonFriends(Integer idUser, Integer idFriend) {
        idValidator(idUser);
        idValidator(idFriend);
        return friendDao.getСommonFriends(idUser, idFriend);
    }

    @Override
    public Collection<User> getUserFriends(Integer id) {
        idValidator(id);
        return friendDao.getUserFriends(id);
    }

    @Override
    public void addToFriends(Integer userId, Integer friendId) {
        idValidator(userId);
        idValidator(friendId);
        friendDao.addToFriends(userId, friendId);
    }

    public void removeFromFriends(Integer userId, Integer friendId) {
        idValidator(userId);
        idValidator(friendId);
        friendDao.removeFromFriends(userId, friendId);
    }
}
