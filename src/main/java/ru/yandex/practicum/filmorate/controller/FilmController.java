package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmInvalidUpdateId;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;

import static ru.yandex.practicum.filmorate.tools.ModelTools.nextIndex;
import static ru.yandex.practicum.filmorate.tools.ModelTools.validateFilm;

@RestController
@Slf4j
public class FilmController {
    private final HashMap<Integer, Film> films = new HashMap<>();
    private Integer filmIndex = 0;

    @GetMapping("/films")
    public Collection<Film> findAllFilms() {
        log.debug("Текущее количество фильмов: {}", films.size());
        return films.values();
    }

    @PostMapping(value = "/films")
    public Film create(@RequestBody Film film) {

        film.setId(nextIndex(filmIndex));
        films.put(film.getId(), film);
        log.debug(film.toString());
        return films.get(film.getId());
    }

    @PutMapping(value = "/films")
    public Film updateFilm(@RequestBody Film updateFilm) {
        validateFilm(updateFilm);

        if (films.containsKey(updateFilm.getId())) {
            films.put(updateFilm.getId(), updateFilm);
            log.debug("Фильм не обновлен, а создан новый", updateFilm.toString());
            return films.get(updateFilm.getId());

        } else {
            throw new FilmInvalidUpdateId("Такого Id для обновления не существует");
        }
    }
}

