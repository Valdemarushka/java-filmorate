package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class GenreDaoTest {
    private final GenreDao genreDao;
    private final FilmDbStorage filmDbStorage;
    private Film film;
    private Film filmForEquals;
    private Film filmUpdate;

    @BeforeEach
    public void createGenreTests() {
        film = new Film("Фильм1", "описание1",
                LocalDate.of(2000, 1, 1), 180, new Mpa(1));
        filmForEquals = new Film(1, "Фильм1", "описание1",
                LocalDate.of(2000, 1, 1), 180,
                new Mpa(1, "G"), new HashSet<>(), new ArrayList<>());
        Collection<Genre> newGenre = new ArrayList<>();
        Genre genre1 = new Genre(1, "Комедия");
        Genre genre2 = new Genre(2, "Драма");
        newGenre.add(genre1);
        newGenre.add(genre2);
        filmUpdate = new Film(1, "Фильм1Апдейт", "описание1Апдейт",
                LocalDate.of(2001, 2, 3), 128,
                new Mpa(2, "PG"), new HashSet<>(), newGenre);
    }

    @Test
    void getGenreById() {
        System.out.println(genreDao.getGenreById(1).getName());
        assertTrue(genreDao.getGenreById(1).getName().equals("Комедия"));
    }

    @Test
    void getAllGenres() {
        assertTrue(genreDao.getAllGenres().size() == 6);
    }

    @Test
    void updateGenreInFilm() {
        filmDbStorage.addFilm(film);
        genreDao.updateGenreInFilm(filmUpdate);
        assertTrue(filmDbStorage.getFilmById(1).getGenres().size() == 2);
    }
}