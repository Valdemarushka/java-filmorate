package ru.yandex.practicum.filmorate.exception;

public class FilmInvalidUpdateId extends RuntimeException {
    public FilmInvalidUpdateId(String message) {
        super(message);
    }
}
