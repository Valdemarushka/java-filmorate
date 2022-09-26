package ru.yandex.practicum.filmorate.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

import static ru.yandex.practicum.filmorate.requests.MpaDbRequest.sqlGetAllMpa;
import static ru.yandex.practicum.filmorate.requests.MpaDbRequest.sqlGetMpaById;

@Repository
@Slf4j
public class MpaDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MpaDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Mpa getMpaById(Integer idMpa) {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sqlGetMpaById, idMpa);
        if (rowSet.next()) {
            return new Mpa(
                    rowSet.getInt("id_mpa"),
                    rowSet.getString("mpa"));
        }
        return null;
    }

    public Collection<Mpa> getAllMpa() {
        return jdbcTemplate.query(sqlGetAllMpa, (rs, rowNum) -> new Mpa(
                rs.getInt("id_mpa"),
                rs.getString("mpa")
        ));
    }
}
