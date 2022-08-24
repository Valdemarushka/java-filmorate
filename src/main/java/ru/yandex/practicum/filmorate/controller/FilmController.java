package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.IncorrectParameterException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

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
        return inMemoryFilmStorage.getAllFilms();
    }

    @GetMapping("/films/{id}")
    public Film getFilmById(@PathVariable("id") Integer id) {
        return inMemoryFilmStorage.getFilmById(id);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void addLike(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteLike(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        filmService.deleteLike(id, userId);
    }

    @GetMapping("films/popular")
    public Collection<Film> getMostPopularFilm(
            @RequestParam(defaultValue = "10", required = false) Integer count
    ) {
        if (count < 0) {
            throw new IncorrectParameterException("count");
        }
        return filmService.getMostPopularFilm(count);
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
