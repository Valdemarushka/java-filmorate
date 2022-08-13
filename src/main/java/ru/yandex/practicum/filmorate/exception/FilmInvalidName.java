package ru.yandex.practicum.filmorate.exception;

public class FilmInvalidName extends RuntimeException {
    public FilmInvalidName(String message) {
        super(message);
    }
}
