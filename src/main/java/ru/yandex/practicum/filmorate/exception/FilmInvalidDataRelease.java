package ru.yandex.practicum.filmorate.exception;

public class FilmInvalidDataRelease extends RuntimeException {
    public FilmInvalidDataRelease(String message) {
        super(message);
    }
}
