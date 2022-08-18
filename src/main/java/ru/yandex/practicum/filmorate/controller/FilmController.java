package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.Collection;
import java.util.HashMap;

@RestController
@Slf4j
public class FilmController {
    private final HashMap<Integer, Film> films = new HashMap<>();
    private Integer filmIndex = 0;
    private final FilmService filmService;
    private final InMemoryFilmStorage inMemoryFilmStorage;

    public FilmController(FilmService filmService, InMemoryFilmStorage inMemoryFilmStorage) {
        this.filmService = filmService;
        this.inMemoryFilmStorage = inMemoryFilmStorage;
    }

    @GetMapping("/films")
    public Collection<Film> findAllFilms() {
        return inMemoryFilmStorage.findAllFilms();
    }


    @PostMapping(value = "/films")
    public Film createFilm(@RequestBody Film film) {
        return inMemoryFilmStorage.createFilm(film);
    }

    @PutMapping(value = "/films")
    public Film updateFilm(@RequestBody Film updateFilm) {
        return inMemoryFilmStorage.updateFilm(updateFilm);

    }


}

