package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {

    private final Integer AMOUNT_POPULAR_FILMS = 10;
    InMemoryFilmStorage inMemoryFilmStorage;

    @Autowired

    public FilmService(InMemoryFilmStorage inMemoryFilmStorage) {

        this.inMemoryFilmStorage = inMemoryFilmStorage;
    }

    public void addLike(Integer idFilm, Integer idLiker) {
        Film film = inMemoryFilmStorage.getFilmById(idFilm);
        Set<Integer> likes = film.getLikes();
        likes.add(idLiker);
        film.setLikes(likes);
        inMemoryFilmStorage.updateFilm(film);
    }

    public void deleteLike(Integer idFilm, Integer idLiker) {
        Film film = inMemoryFilmStorage.getFilmById(idFilm);
        Set<Integer> likes = film.getLikes();
        likes.remove(idLiker);
        film.setLikes(likes);
        inMemoryFilmStorage.updateFilm(film);
    }

    public Set<Film> getMostPopularFilm(Integer count) {
        return inMemoryFilmStorage.getAllFilms().stream()
                .sorted(Comparator.comparing((Film f) -> f.getLikes().size()))
                .limit(count)
                .collect(Collectors.toSet());
    }
}
