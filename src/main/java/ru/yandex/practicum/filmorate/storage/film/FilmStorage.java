package ru.yandex.practicum.filmorate.storage.film;


import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    // Хранение, обновление и поиск объектов.
    //Создать фильм
    public Film createFilm(Film film);


    //Обновить фильм
    public Film updateFilm(Film updateFilm);

    //Получить все фильмы
    public List<Film> getAllFilms();

    public Film getFilmById(Integer id);

    //Удалить все фильмы
    public void deleteAllFilms();

    //Удалить фильм
    public void deleteFilm(Integer id);

}
