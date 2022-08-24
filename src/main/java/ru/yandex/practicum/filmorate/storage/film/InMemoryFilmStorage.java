package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;

import static ru.yandex.practicum.filmorate.tools.ModelTools.*;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    //реализация хранения, обновления и поиска объектов.
    private final HashMap<Integer, Film> films = new HashMap<>();
    private Integer filmIndex = 0;

    private int nextIndex() {
        return ++filmIndex;
    }

    @Override
    public Film createFilm(Film film) {
        validateFilm(film);
        filmsNotNull(films);
        film.setId(nextIndex());
        films.put(film.getId(), film);
        log.debug("фильм добавлен");
        return films.get(film.getId());
    }

    @Override
    public Film updateFilm(Film updateFilm) {
        validateFilm(updateFilm);
        filmsContainsIdAndNotNull(films, updateFilm.getId());
        films.put(updateFilm.getId(), updateFilm);
        log.debug("Фильм обновлен");
        return films.get(updateFilm.getId());
    }

    @Override
    public ArrayList<Film> getAllFilms() {
        log.debug("Получаем все фильмы");
        filmsNotNull(films);
        log.debug("Текущее количество фильмов: {}", films.size());
        return new ArrayList<Film>(films.values());
    }

    @Override
    public Film getFilmById(Integer id) {
        log.debug("Получаем фильм");
        filmsContainsIdAndNotNull(films, id);
        return films.get(id);
    }

    @Override
    public void deleteAllFilms() {
        log.debug("Удаляем все фильмы");
        filmsNotNull(films);
        films.clear();
        log.debug("Фильмы удалены");
    }

    @Override
    public HashMap<Integer, Film> getFilms() {
        filmsNotNull(films);
        return films;
    }

    @Override
    public void deleteFilm(Integer id) {
        log.debug("Удаляем фильм");
        filmsContainsIdAndNotNull(films, id);
        films.remove(id);
        log.debug("Фильм c id{} удален", id);
    }
}
