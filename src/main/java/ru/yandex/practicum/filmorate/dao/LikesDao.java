package ru.yandex.practicum.filmorate.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static ru.yandex.practicum.filmorate.requests.LikesDbRequest.*;
import static ru.yandex.practicum.filmorate.tools.ModelTools.idValidator;

@Slf4j
@Component
public class LikesDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LikesDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addLike(Integer filmId, Integer userId) {
        idValidator(userId);
        log.debug("Добавлен лайк к фильму {} от пользователя {}", filmId, userId);
        jdbcTemplate.update(sqlAddLike, filmId, userId);
    }

    public void removeLike(Integer filmId, Integer userId) {
        idValidator(userId);
        log.debug("Убран лайк с фильма {} от пользователя {}", filmId, userId);
        jdbcTemplate.update(sqlRemoveLike, filmId, userId);
    }

    public Collection<Integer> getLikes(Integer filmId) {
        SqlRowSet likerRows = jdbcTemplate.queryForRowSet(sqlGetIdUserWhoLikesFilm, filmId);
        List<Integer> likerList = new ArrayList<>();
        if (likerRows.next()) {
            Integer idLiker = likerRows.getInt("id_user");
            likerList.add(idLiker);
        }
        log.debug("выведен список всех лайков в фильме {}", filmId);
        return likerList;
    }
}
