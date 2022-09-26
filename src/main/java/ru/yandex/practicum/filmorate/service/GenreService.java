package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Collection;

@Service
public class GenreService {
    private final GenreDao genreDao;
    private final FilmStorage filmStorage;

    @Autowired
    public GenreService(GenreDao genreDao,
                        @Qualifier("filmDbStorage") FilmStorage filmStorage) {
        this.genreDao = genreDao;
        this.filmStorage = filmStorage;
    }

    public Genre getGenreById(Integer id) {
        return genreDao.getGenreById(id);
    }

    public Collection<Genre> getAllGenres() {
        return genreDao.getAllGenres();
    }

    public Film updateGenreById(Film film) {
        genreDao.updateGenreInFilm(film);
        return filmStorage.getFilmById(film.getId());
    }
}