package ru.yandex.practicum.filmorate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

import static ru.yandex.practicum.filmorate.requests.FilmDbRequest.sqlAddGenreInFilm;
import static ru.yandex.practicum.filmorate.requests.FilmDbRequest.sqlDeleteAllGenresInFilm;
import static ru.yandex.practicum.filmorate.requests.GenreDbRequest.sqlGetAllGenres;
import static ru.yandex.practicum.filmorate.requests.GenreDbRequest.sqlGetGenreById;
import static ru.yandex.practicum.filmorate.tools.ModelTools.idValidator;
import static ru.yandex.practicum.filmorate.tools.ModelTools.validateFilm;

@Repository
public class GenreDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenreDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Genre getGenreById(Integer idGenre) {
        idValidator(idGenre);
        SqlRowSet genreRows = jdbcTemplate.queryForRowSet(sqlGetGenreById, idGenre);
        if (genreRows.next()) {
            return new Genre(
                    genreRows.getInt("id_genre"),
                    genreRows.getString("genre"));
        }
        return new Genre(0, "no genres");
    }

    public Collection<Genre> getAllGenres() {
        return jdbcTemplate.query(sqlGetAllGenres, (rs, rowNum) -> new Genre(
                rs.getInt("id_genre"),
                rs.getString("genre")));
    }

    public void updateGenreInFilm(Film film) {
        validateFilm(film);
        jdbcTemplate.update(sqlDeleteAllGenresInFilm, film.getId());
        if (!film.getGenres().isEmpty()) {
            for (Genre genre : film.getGenres()) {
                jdbcTemplate.update(sqlAddGenreInFilm, film.getId(), genre.getId());
            }
        }
    }
}
