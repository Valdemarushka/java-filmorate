package ru.yandex.practicum.filmorate.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.requests.FilmDbRequest;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.requests.FilmDbRequest.*;

@Repository("filmDbStorage")
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final LikesDao likesDao;
    private final MpaDao mpaDao;
    private final GenreDao genreDao;


    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate, LikesDao likesDao, MpaDao mpaDao, GenreDao genreDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.likesDao = likesDao;
        this.mpaDao = mpaDao;
        this.genreDao = genreDao;
    }

    @Override
    public Film addFilm(Film film) {
        jdbcTemplate.update(sqlAddFilm, film.getName(), film.getDescription(), film.getReleaseDate(),
                film.getDuration(), film.getMpa().getId());

        SqlRowSet filmRows = jdbcTemplate.queryForRowSet(sqlGetFilmId, film.getName(), film.getDescription());
        if (filmRows.next()) {
            Integer idFilm = filmRows.getInt("id_film");
            Mpa mpa = mpaDao.getMpaById(film.getMpa().getId());
            Set<Integer> likes = new HashSet<>(jdbcTemplate.query(sqlGetLikes, (rsLikes, rowNum) ->
                    (rsLikes.getInt("id_user")), idFilm));

            if (film.getGenres() != null) {
                for (Genre genre : film.getGenres()) {
                    jdbcTemplate.update(sqlAddGenreInFilm, idFilm, genre.getId());
                }
            }
            Set<Genre> genres = new HashSet<>();
            SqlRowSet genreIdRows = jdbcTemplate.queryForRowSet(sqlGetIdGenresOfFilm, idFilm);
            if (genreIdRows.next()) {
                Genre genre = genreDao.getGenreById(genreIdRows.getInt("id_genre"));
                genres.add(genre);
            }


            return new Film(idFilm, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
                    mpa, likes, genres);
        }
        return null;
    }


    @Override
    public Film updateFilm(Film film) {
        int idFilm = film.getId();

        jdbcTemplate.update(sqlDeleteAllGenresInFilm, idFilm);
        for (int likerId : likesDao.getLikes(idFilm)) {
            likesDao.removeLike(idFilm, likerId);
        }

        jdbcTemplate.update(sqlUpdateFilm, film.getName(), film.getDescription(), film.getReleaseDate(),
                film.getDuration(), film.getMpa().getId(), film.getId());

        Set<Integer> likes = film.getLikes();
        for (int likerId : likes) {
            likesDao.addLike(idFilm, likerId);
        }

        Collection<Genre> genres = film.getGenres();
        if (genres != null || !genres.isEmpty()) {
            for (Genre genre : genres) {
                jdbcTemplate.update(sqlAddGenreInFilm, idFilm, genre.getId());
            }
        }

        return getFilmById(idFilm);
    }

    private Film makeFilm(ResultSet rs) throws SQLException {
        Integer idFilm = rs.getInt("id_film");
        String name = rs.getString("name");
        String description = rs.getString("description");
        LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
        int duration = rs.getInt("duration");
        int mpaId = rs.getInt("id_mpa");
        Mpa mpa = mpaDao.getMpaById(mpaId);

        Set<Integer> likes = new HashSet<>(jdbcTemplate.query(sqlGetLikes, (rsLikes, rowNum) ->
                (rsLikes.getInt("id_user")), idFilm));

        Set<Genre> genres = new HashSet<>();
        SqlRowSet genreIdRows = jdbcTemplate.queryForRowSet(sqlGetIdGenresOfFilm, idFilm);
        System.out.println(genreIdRows);
        Set<Integer> idGenre = new HashSet<>();
        while (genreIdRows.next()) {
            Genre genre = genreDao.getGenreById(genreIdRows.getInt("id_genre"));
            if (!idGenre.contains(genre.getId())) {
                genres.add(genre);
                idGenre.add(genre.getId());
            }
        }

        return new Film(idFilm, name, description, releaseDate, duration, mpa, likes,
                genres.stream()
                        .sorted((f1, f2) -> Integer.compare(f1.getId(), f2.getId()))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public List<Film> getAllFilms() {
        return jdbcTemplate.query(sqlGetAllFilms, (rs, rowNum) -> makeFilm(rs));
    }

    @Override
    public Film getFilmById(Integer id) {
        return jdbcTemplate.query(sqlGetFilmById, (rs, rowNum) -> makeFilm(rs), id).get(0);
    }

    @Override
    public HashMap<Integer, Film> getFilms() {
        return null;
    }

    @Override
    public void deleteAllFilms() {
        List<Film> filmList = jdbcTemplate.query(sqlGetAllFilms, (rs, rowNum) -> makeFilm(rs));
        for (Film film : filmList) {
            jdbcTemplate.update(FilmDbRequest.sqlDeleteAllGenresInFilm, film.getId());
            jdbcTemplate.update(FilmDbRequest.sqlDeleteFilm, film.getId());

        }
    }

    @Override
    public void deleteFilm(Integer id) {
        jdbcTemplate.update(sqlDeleteAllGenresInFilm, id);
        jdbcTemplate.update(FilmDbRequest.sqlDeleteFilm, id);
    }

    @Override
    public void addLike(Integer filmId, Integer userId) {
        likesDao.addLike(filmId, userId);
    }

    @Override
    public void removeLike(Integer filmId, Integer userId) {
        likesDao.removeLike(filmId, userId);
    }

    public Collection<Integer> getLikes(Integer filmId) {
        return likesDao.getLikes(filmId);
    }

    @Override
    public List<Film> getPopular(Integer count) {
        return getAllFilms()
                .stream()
                .sorted((f1, f2) -> Integer.compare(getLikes(f2.getId()).size(), getLikes(f1.getId()).size()))
                .limit(count)
                .collect(Collectors.toList());
    }
}
