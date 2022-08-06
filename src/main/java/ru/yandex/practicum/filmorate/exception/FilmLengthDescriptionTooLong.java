package ru.yandex.practicum.filmorate.exception;

public class FilmLengthDescriptionTooLong extends RuntimeException {
    public FilmLengthDescriptionTooLong(String message) {
        super(message);
    }
}
