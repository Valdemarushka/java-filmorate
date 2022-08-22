package ru.yandex.practicum.filmorate.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ObjectIsNull;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.HashMap;

public class ModelTools {
    static final LocalDate MIN_RELEASE_DATE = LocalDate.of(1895, 11, 28);

    //общие методы
    public static int nextIndex(int userIndex) {
        return ++userIndex;
    }

    //методы для фильмов
    private final static Logger log = LoggerFactory.getLogger(ModelTools.class);


    public static void filmsNotNull(HashMap films) {
        if (films == null) {
            log.error("films - null");
            throw new ObjectIsNull("films - null");
        }
    }

    public static void filmsContainsId(HashMap films, Integer id) {
        filmsNotNull(films);
        if (!films.containsKey(id)) {
            log.error("Фильма с таким id нет");
            throw new NotFoundException("Фильма с таким id нет");
        }
    }


    public static void validateFilm(Film film) {
        filmNotNull(film);
        filmNameValidator(film);
        lengthDescriptionValidator(film);
        releaseDateValidator(film);
        durationValidator(film);
        log.trace("Фильм соответствует требованиям");
    }

    public static void filmNotNull(Film film) {
        if (film == null) {
            log.error("Вместо объекта юзера передан null");
            throw new ObjectIsNull("Вместо объекта фильма передан null");
        }
    }

    public static void filmNameValidator(Film film) {
        if (film.getName() == null || film.getName().isEmpty()) {
            log.error("Пустое название фильма");
            throw new ValidateException("Пустое название фильма");
        }
    }

    public static void lengthDescriptionValidator(Film film) {
        final int MAX_LENGTH_DESCRIPTION = 200;
        if (film.getDescription().length() > MAX_LENGTH_DESCRIPTION) {
            log.error("Пустое название фильма");
            throw new ValidateException("Пустое название фильма");
        }
    }

    public static void releaseDateValidator(Film film) {
        if (film.getReleaseDate().isBefore(MIN_RELEASE_DATE)) {
            log.error("Неверная дата релиза");
            throw new ValidateException("Неверная дата релиза");
        }
    }

    public static void durationValidator(Film film) {
        if (film.getDuration() < 0) {
            log.error("Продолжительность фильма отрицательна");
            throw new ValidateException("Продолжительность фильма отрицательна");
        }
    }

    //методы для юзеров


    public static void usersNotNull(HashMap users) {
        if (users == null) {
            log.error("users - null");
            throw new ObjectIsNull("users - null");
        }
    }

    public static void usersContainsId(HashMap users, Integer id) {
        usersNotNull(users);
        if (!users.containsKey(id)) {
            log.error("Юзера с таким id нет");
            throw new NotFoundException("Юзера с таким id нет");
        }
    }

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
            throw new ValidateException("Пустой email");
        }
        if (!user.getEmail().contains("@")) {
            log.error("Email не содержит @");
            throw new ValidateException("Email не содержит @");
        }
    }

    public static void userLoginValidator(User user) {
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            log.error("Пустой логин");
            throw new ValidateException("Пустой логин");
        }
        if (user.getLogin().contains(" ")) {
            log.error("Логин содержит пробелы");
            throw new ValidateException("Логин содержит пробелы");
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
            throw new ValidateException("Неверная дата рождения");
        }
    }
}
