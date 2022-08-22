package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmInvalidUpdateId;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static ru.yandex.practicum.filmorate.tools.ModelTools.nextIndex;
import static ru.yandex.practicum.filmorate.tools.ModelTools.validateFilm;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    //реализация хранения, обновления и поиска объектов.

    private final HashMap<Integer, Film> films = new HashMap<>();
    private Integer filmIndex = 0;

    @Override
    public Film createFilm(Film film) {
        validateFilm(film);
        film.setId(nextIndex(filmIndex));
        films.put(film.getId(), film);
        log.debug("фильм добавлен");
        return films.get(film.getId());
    }

    @Override
    public Film updateFilm(Film updateFilm) {
        validateFilm(updateFilm);
        if (films.containsKey(updateFilm.getId())) {
            films.put(updateFilm.getId(), updateFilm);
            log.debug("Фильм обновлен");
            return films.get(updateFilm.getId());

        } else {
            throw new FilmInvalidUpdateId("Такого Id для обновления не существует");
        }
    }

    @Override
    public List<Film> getAllFilms() {
        log.debug("Текущее количество фильмов: {}", films.size());
        return new ArrayList<Film>(films.values());
    }

    @Override
    public Film getFilmById(Integer id) {
        log.debug("Получаем фильм");
        return films.get(id);
    }

    @Override
    public void deleteAllFilms() {
        films.clear();
        log.debug("Фильмы удалены");
    }

    @Override
    public void deleteFilm(Integer id) {
        films.remove(id);
        log.debug("Фильм c id{} удален", id);
    }


}
