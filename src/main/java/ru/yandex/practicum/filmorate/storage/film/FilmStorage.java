package ru.yandex.practicum.filmorate.storage.film;


import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.List;

public interface FilmStorage {

    // Хранение, обновление и поиск объектов.
    Film createFilm(Film film);

    Film updateFilm(Film updateFilm);

    List<Film> getAllFilms();

    Film getFilmById(Integer id);

    HashMap<Integer, Film> getFilms();

    void deleteAllFilms();

    void deleteFilm(Integer id);
}