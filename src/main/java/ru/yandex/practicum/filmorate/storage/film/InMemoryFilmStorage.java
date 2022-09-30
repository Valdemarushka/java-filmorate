package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.tools.ModelTools.*;

@Component("inMemoryFilmStorage")
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private final HashMap<Integer, Film> films = new HashMap<>();
    private Integer filmIndex = 0;

    private int nextIndex() {
        return ++filmIndex;
    }

    @Override
    public Film addFilm(Film film) {
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

    @Override
    public void addLike(Integer filmId, Integer userId) {
        idValidator(userId);
        idValidator(filmId);
        Film film = getFilmById(filmId);
        filmNotNull(film);
        Set<Integer> likes = new HashSet<>();
        if (film.getLikes() == null) {
            likes.add(userId);
        } else {
            likes = film.getLikes();
            likes.add(userId);
        }
        film.setLikes(likes);
        updateFilm(film);
    }

    @Override
    public void removeLike(Integer filmId, Integer userId) {
        idValidator(userId);
        idValidator(filmId);
        Film film = getFilmById(filmId);
        filmNotNull(film);
        Set<Integer> likes = film.getLikes();
        likes.remove(userId);
        film.setLikes(likes);
        updateFilm(film);
    }

    @Override
    public Collection<Film> getPopular(Integer count) {
        return getFilms()
                .values()
                .stream()
                .sorted((f1, f2) -> Integer.compare(f2.getLikes().size(), f1.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }
}
