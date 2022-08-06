package ru.yandex.practicum.filmorate.exception;

public class UserInvalidUpdateId extends RuntimeException {
    public UserInvalidUpdateId(String message) {
        super(message);
    }
}
