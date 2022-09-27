package ru.yandex.practicum.filmorate.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static ru.yandex.practicum.filmorate.requests.FriendsDbRequest.*;
import static ru.yandex.practicum.filmorate.requests.UserDbRequest.sqlGetLike;

@Slf4j
@Component
public class FriendsDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FriendsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addToFriends(Integer userId, Integer friendId) {
        log.debug("Юзер {} добавил юзера {} в друзья", userId, friendId);
        jdbcTemplate.update(sqlAddInFriends, userId, friendId, 1);
        if (!userAlreadyAdded(friendId, userId)) {
            jdbcTemplate.update(sqlAddInFriends, friendId, userId, 0);
        }
    }

    public void removeFromFriends(Integer userId, Integer friendId) {
        log.debug("Юзер {} удалил юзера {} из друзей", userId, friendId);
        jdbcTemplate.update(sqlChangeStatus, 0, userId, friendId);

        if (!userAlreadyAdded(friendId, userId)) {
            jdbcTemplate.update(sqlRemoveFromFriends, userId, friendId);
        }
    }

    private boolean userAlreadyAdded(Integer user2, Integer user1) {
        List<Integer> friends = jdbcTemplate.query(sqlGetUserFriends, (rs, rowNum) -> rs.getInt("id_friend"),
                user2, 1);
        if (friends.contains(user1)) {
            return true;
        }
        return false;
    }

    public Collection<User> getUserFriends(Integer id) {
        log.debug("Выводятся все друзья юзера {}", id);
        Collection<User> friendSet = jdbcTemplate.query(sqlGetUserFriends, (rs, rowNum) -> makeOfUser(rs), id, 1);
        if (friendSet.isEmpty() || friendSet == null) {
            return new HashSet<>();
        }
        return friendSet;
    }

    public Collection<User> getСommonFriends(Integer id, Integer id1) {
        log.debug("Выводятся все общие друзья между юзерами {} и {}", id, id1);
        Collection<User> userSet = jdbcTemplate.query(sqlGetСommonFriends, (rs, rowNum) -> makeOfUser(rs), id, id1);
        if (userSet.isEmpty() || userSet == null) {
            return new HashSet<>();
        }
        return userSet;
    }

    private User makeOfUser(ResultSet rs) throws SQLException {

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
        log.debug("Создается юзер");
        return new User(userId, email, name, login, birthday, friends, friendStatus, likedFilms);
    }
}
