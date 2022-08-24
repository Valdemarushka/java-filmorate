package ru.yandex.practicum.filmorate.storage.film;


import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.List;

public interface FilmStorage {

    // Хранение, обновление и поиск объектов.
    public Film createFilm(Film film);

    public Film updateFilm(Film updateFilm);

    public List<Film> getAllFilms();

    public Film getFilmById(Integer id);

    public HashMap<Integer, Film> getFilms();

    public void deleteAllFilms();

    public void deleteFilm(Integer id);
}