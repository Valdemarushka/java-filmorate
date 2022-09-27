package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.LikesDao;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static ru.yandex.practicum.filmorate.tools.ModelTools.*;

@Service
public class FilmService {

    private final Integer AMOUNT_POPULAR_FILMS = 10;
    private final FilmStorage filmStorage;
    private final LikesDao likesDao;

    @Autowired
    public FilmService(@Qualifier("filmDbStorage") FilmStorage filmStorage, LikesDao likesDao) {
        this.filmStorage = filmStorage;
        this.likesDao = likesDao;
    }

    public void addLike(Integer idFilm, Integer idLiker) {
        likesDao.addLike(idFilm, idLiker);
    }

    public void deleteLike(Integer idFilm, Integer idLiker) {
        idValidator(idLiker);
        idValidator(idFilm);
        Film film = filmStorage.getFilmById(idFilm);
        filmNotNull(film);
        Set<Integer> likes = film.getLikes();
        likes.remove(idLiker);
        film.setLikes(likes);
        filmStorage.updateFilm(film);
    }

    public Collection<Film> getMostPopularFilm(Integer count) {
        return filmStorage.getPopular(count);
    }

    public Film addFilm(Film film) {
        validateFilm(film);
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film updateFilm) {
        filmIdValidator(updateFilm);
        validateFilm(updateFilm);
        return filmStorage.updateFilm(updateFilm);
    }

    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public Film getFilmById(Integer id) {
        idValidator(id);
        return filmStorage.getFilmById(id);
    }

    public void deleteAllFilms() {
        filmStorage.deleteAllFilms();
    }
}
