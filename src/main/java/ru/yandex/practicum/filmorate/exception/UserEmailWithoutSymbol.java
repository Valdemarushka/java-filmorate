package ru.yandex.practicum.filmorate.exception;

public class UserEmailWithoutSymbol extends RuntimeException {
    public UserEmailWithoutSymbol(String message) {
        super(message);
    }
}
