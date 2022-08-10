package ru.yandex.practicum.filmorate.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.filmorate.exception.FilmInvalidDataRelease;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class ModelTools {
    static final LocalDate MIN_RELEASE_DATE = LocalDate.of(1895, 11, 28);

    //общие методы
    public static int nextIndex(int userIndex) {
        return ++userIndex;
    }

    //методы для фильмов
    private final static Logger log = LoggerFactory.getLogger(ModelTools.class);

    public static void validateFilm(@NotNull Film film) {


        releaseDateValidator(film);

        log.trace("Фильм соответствует требованиям");
    }

    public static void releaseDateValidator(Film film) {
        if (film.getReleaseDate().isBefore(MIN_RELEASE_DATE)) {
            log.error("Неверная дата релиза");
            throw new FilmInvalidDataRelease("Неверная дата релиза");
        }
    }

    //методы для юзеров
    public static void validateUser(@NotNull(message = "юзер = null") User user) {
        userNameValidator(user);
        log.trace("Юзер соответствует требованиям");
    }

    public static void userNameValidator(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            log.debug("Имя пусто,пробуем заполнить логином");

            user.setName(user.getLogin());
            log.trace("Имя заменено логином");
        }
    }
}
