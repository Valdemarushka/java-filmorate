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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class LikesDaoTest {

    private final FilmDbStorage filmDbStorage;
    private final LikesDao likesDao;
    private Film film;
    private User user1;
    private final UserDbStorage userbStorage;

    @BeforeEach
    public void createFilmsTests() {

        film = new Film("Фильм1", "описание1",
                LocalDate.of(2000, 1, 1), 180, new Mpa(1));

        user1 = new User(1, "as@mail.ru", "name", "login",
                LocalDate.of(2000, 1, 1), new HashSet<>(), new HashMap<>(), new HashSet<>());


    }

    @Test
    void addLike() {
        filmDbStorage.addFilm(film);
        userbStorage.addUser(user1);
        likesDao.addLike(1, 1);
        Set<Integer> likes1 = filmDbStorage.getFilmById(1).getLikes();

        assertTrue(likes1.size() == 1);
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
    void getLikes() {
        filmDbStorage.addFilm(film);
        userbStorage.addUser(user1);
        filmDbStorage.addLike(1, 1);
        Set<Integer> likes1 = filmDbStorage.getFilmById(1).getLikes();

        assertTrue(likes1.contains(1));

    }
}