package ru.yandex.practicum.filmorate.exception;

public class UserInvalidEmail extends RuntimeException {
    public UserInvalidEmail(String message) {
        super(message);
    }
}
