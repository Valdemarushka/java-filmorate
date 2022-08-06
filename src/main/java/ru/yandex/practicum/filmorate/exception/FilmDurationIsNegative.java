package ru.yandex.practicum.filmorate.exception;

public class FilmDurationIsNegative extends RuntimeException {
    public FilmDurationIsNegative(String message) {
        super(message);
    }
}
