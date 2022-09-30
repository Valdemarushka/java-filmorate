package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class FilmDbStorageTest {

    private final FilmDbStorage filmDbStorage;
    private Film film;
    private Film film2;
    private Film film3;
    private Film filmForEquals;
    private Film film2ForEquals;
    private Film film3ForEquals;
    private Film filmUpdate;
    private User user1;
    private final UserDbStorage userbStorage;


    @BeforeEach
    public void createFilmsTests() {

        film = new Film("Фильм1", "описание1",
                LocalDate.of(2000, 1, 1), 180, new Mpa(1));
        filmForEquals = new Film(1, "Фильм1", "описание1",
                LocalDate.of(2000, 1, 1), 180,
                new Mpa(1, "G"), new HashSet<>(), new ArrayList<>());
        filmUpdate = new Film(1, "Фильм1Апдейт", "описание1Апдейт",
                LocalDate.of(2001, 2, 3), 128,
                new Mpa(2, "PG"), new HashSet<>(), new ArrayList<>());
        user1 = new User(1, "as@mail.ru", "name", "login",
                LocalDate.of(2000, 1, 1), new HashSet<>(), new HashMap<>(), new HashSet<>());

        film2 = new Film("Фильм2", "описание2",
                LocalDate.of(2000, 1, 1), 180, new Mpa(1));
        Set<Integer> likes = new HashSet<>();
        likes.add(1);
        film2ForEquals = new Film(2, "Фильм2", "описание2",
                LocalDate.of(2000, 1, 1), 180,
                new Mpa(1, "G"), likes, new ArrayList<>());
        film3 = new Film("Фильм3", "описание3",
                LocalDate.of(2000, 1, 1), 180, new Mpa(1));

        film3ForEquals = new Film(3, "Фильм3", "описание3",
                LocalDate.of(2000, 1, 1), 180,
                new Mpa(1, "G"), new HashSet<>(), new ArrayList<>());


    }


    static void equalFilms(Film film1, Film film2) {
        assertEquals(film1.getId(), film2.getId());
        assertEquals(film1.getName(), film2.getName());
        assertEquals(film1.getDescription(), film2.getDescription());
        assertEquals(film1.getReleaseDate(), film2.getReleaseDate());
        assertEquals(film1.getMpa().getId(), film2.getMpa().getId());
        assertEquals(film1.getMpa().getName(), film2.getMpa().getName());
        assertEquals(film1.getLikes(), film2.getLikes());
        assertEquals(film1.getGenres(), film2.getGenres());
    }


    @Test
    void addFilm() {
        filmDbStorage.addFilm(film);
        equalFilms(filmForEquals, filmDbStorage.getFilmById(1));

    }

    @Test
    void updateFilm() {
        filmDbStorage.addFilm(film);
        filmDbStorage.updateFilm(filmUpdate);
        equalFilms(filmUpdate, filmDbStorage.getFilmById(1));
    }

    @Test
    void getAllFilms() {
        filmDbStorage.addFilm(film);
        filmDbStorage.addFilm(film);
        Collection<Film> films = filmDbStorage.getAllFilms();
        System.out.println(films);
        assertTrue(films.size() == 2);

    }

    @Test
    void deleteFilm() {
        filmDbStorage.addFilm(film);
        filmDbStorage.deleteFilm(1);
        Collection<Film> films1 = filmDbStorage.getAllFilms();
        assertTrue(films1.size() == 0);
    }

    @Test
    void deleteAllFilms() {
        filmDbStorage.addFilm(film);
        filmDbStorage.addFilm(film);
        filmDbStorage.deleteAllFilms();
        List<Film> films = filmDbStorage.getAllFilms();
        assertTrue(films.size() == 0);

    }


    @Test
    void getPopular() {
        filmDbStorage.addFilm(film);
        filmDbStorage.addFilm(film2);
        filmDbStorage.addFilm(film3);
        userbStorage.addUser(user1);
        filmDbStorage.addLike(2, 1);
        List<Film> popFilm = filmDbStorage.getPopular(3);
        List<Film> popFilmForEquals = new ArrayList<>();
        popFilmForEquals.add(film2ForEquals);
        popFilmForEquals.add(filmForEquals);
        popFilmForEquals.add(film3ForEquals);
        System.out.println(popFilm);
        System.out.println(popFilmForEquals);
        equalFilms(film2ForEquals, filmDbStorage.getPopular(3).get(0));


    }

    @Test
    void removeLike() {
        filmDbStorage.addFilm(film);
        userbStorage.addUser(user1);
        filmDbStorage.addLike(1, 1);
        Set<Integer> likes1 = filmDbStorage.getFilmById(1).getLikes();

        assertTrue(likes1.size() == 1);
        filmDbStorage.removeLike(1, 1);
        Set<Integer> likes2 = filmDbStorage.getFilmById(1).getLikes();

        assertTrue(likes2.size() == 0);


    }


    @Test
    void getFilmById() {
        filmDbStorage.addFilm(film);
        equalFilms(filmForEquals, filmDbStorage.getFilmById(1));
    }

    @Test
    void getFilms() {


    }


    @Test
    void addLike() {
        filmDbStorage.addFilm(film);
        userbStorage.addUser(user1);
        filmDbStorage.addLike(1, 1);
        Set<Integer> likes1 = filmDbStorage.getFilmById(1).getLikes();

        assertTrue(likes1.size() == 1);
    }

    @Test
    void getLikes() {
        filmDbStorage.addFilm(film);
        userbStorage.addUser(user1);
        filmDbStorage.addLike(1, 1);
        Set<Integer> likes1 = filmDbStorage.getFilmById(1).getLikes();

        assertTrue(likes1.contains(1));
    }


}