package ru.yandex.practicum.filmorate.exception;

public class UserInvalidLogin extends RuntimeException {
    public UserInvalidLogin(String message) {
        super(message);
    }
}
