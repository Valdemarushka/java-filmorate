package ru.yandex.practicum.filmorate.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class ModelTools {
    static final LocalDate MIN_RELEASE_DATE = LocalDate.of(1895, 11, 28);

    //общие методы
    public static int nextIndex(int userIndex) {
        return ++userIndex;
    }


    //методы для фильмов
    private final static Logger log = LoggerFactory.getLogger(ModelTools.class);

    public static void validateFilm(Film film) {
        notNull(film);
        filmNameValidator(film);
        lengthDescriptionValidator(film);
        releaseDateValidator(film);
        durationValidator(film);
        log.trace("Фильм соответствует требованиям");
    }

    public static void notNull(Film film) {
        if (film == null) {
            log.error("Вместо объекта фильма передан null");
            throw new ObjectIsNull("Вместо объекта фильма передан null");
        }
    }

    public static void filmNameValidator(Film film) {
        if (film.getName() == null || film.getName().isEmpty()) {
            log.error("Пустое название фильма");
            throw new FilmInvalidName("Пустое название фильма");
        }
    }

    public static void lengthDescriptionValidator(Film film) {
        final int MAX_LENGTH_DESCRIPTION = 200;
        if (film.getDescription().length() > MAX_LENGTH_DESCRIPTION) {
            log.error("Пустое название фильма");
            throw new FilmLengthDescriptionTooLong("Пустое название фильма");
        }
    }

    public static void releaseDateValidator(Film film) {
        if (film.getReleaseDate().isBefore(MIN_RELEASE_DATE)) {
            log.error("Неверная дата релиза");
            throw new FilmInvalidDataRelease("Неверная дата релиза");
        }
    }

    public static void durationValidator(Film film) {
        if (film.getDuration() < 0) {
            log.error("Продолжительность фильма отрицательна");
            throw new FilmDurationIsNegative("Продолжительность фильма отрицательна");
        }
    }

    //методы для юзеров
    public static void validateUser(User user) {
        userNotNull(user);
        userEmailValidator(user);
        userLoginValidator(user);
        userNameValidator(user);
        userBirthdayValidator(user);
        log.trace("Юзер соответствует требованиям");
    }

    public static void userNotNull(User user) {
        if (user == null) {
            log.error("Вместо объекта юзера передан null");
            throw new ObjectIsNull("Вместо юзера фильма передан null");
        }
    }

    public static void userEmailValidator(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            log.error("Пустой email");
            throw new UserInvalidEmail("Пустой email");
        }
        if (!user.getEmail().contains("@")) {
            log.error("Email не содержит @");
            throw new UserEmailWithoutSymbol("Email не содержит @");
        }
    }

    public static void userLoginValidator(User user) {
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            log.error("Пустой логин");
            throw new UserInvalidLogin("Пустой логин");
        }
        if (user.getLogin().contains(" ")) {
            log.error("Логин содержит пробелы");
            throw new UserInvalidLogin("Логин содержит пробелы");
        }
    }

    public static void userNameValidator(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            log.debug("Имя пусто,пробуем заполнить логином");
            userLoginValidator(user);
            user.setName(user.getLogin());
            log.trace("Имя заменено логином");
        }
    }

    public static void userBirthdayValidator(User user) {

        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Неверная дата рождения");
            throw new UserInvalidBirthday("Неверная дата рождения");
        }
    }

}
