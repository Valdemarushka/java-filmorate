package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.tools.ModelTools.filmNotNull;
import static ru.yandex.practicum.filmorate.tools.ModelTools.usersContainsIdAndNotNull;

@Slf4j
@Service
public class FilmService {

    private final Integer AMOUNT_POPULAR_FILMS = 10;
    InMemoryFilmStorage inMemoryFilmStorage;
    InMemoryUserStorage inMemoryUserStorage;

    @Autowired
    public FilmService(InMemoryFilmStorage inMemoryFilmStorage, InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public void addLike(Integer idFilm, Integer idLiker) {
        usersContainsIdAndNotNull(inMemoryUserStorage.getUsers(), idLiker);
        Film film = inMemoryFilmStorage.getFilmById(idFilm);
        filmNotNull(film);
        Set<Integer> likes = new HashSet<>();
        if (film.getLikes() == null) {
            likes.add(idLiker);
        } else {
            likes = film.getLikes();
            likes.add(idLiker);
        }
        film.setLikes(likes);
        inMemoryFilmStorage.updateFilm(film);
    }

    public void deleteLike(Integer idFilm, Integer idLiker) {
        usersContainsIdAndNotNull(inMemoryUserStorage.getUsers(), idLiker);
        Film film = inMemoryFilmStorage.getFilmById(idFilm);
        filmNotNull(film);
        Set<Integer> likes = film.getLikes();
        likes.remove(idLiker);
        film.setLikes(likes);
        inMemoryFilmStorage.updateFilm(film);
    }

    public Collection<Film> getMostPopularFilm(Integer count) {
        return inMemoryFilmStorage
                .getFilms()
                .values()
                .stream()
                .sorted((f1, f2) -> Integer.compare(f2.getLikes().size(), f1.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }

    public Film createFilm(Film film) {
        return inMemoryFilmStorage.createFilm(film);
    }

    public Film updateFilm(Film updateFilm) {
        return inMemoryFilmStorage.updateFilm(updateFilm);
    }

    public ArrayList<Film> getAllFilms() {
        return inMemoryFilmStorage.getAllFilms();
    }

    public Film getFilmById(Integer id) {
        return inMemoryFilmStorage.getFilmById(id);
    }

    public void deleteAllFilms() {
        inMemoryFilmStorage.deleteAllFilms();
    }

}
