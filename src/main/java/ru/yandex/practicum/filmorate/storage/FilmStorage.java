package ru.yandex.practicum.filmorate.storage;


import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {


    //Создать фильм
    public Film createFilm(Film film);


    //Обновить фильм
    public Film updateFilm(Film updateFilm);

    //Получить все фильмы
    public List<Film> findAllFilms();

}
