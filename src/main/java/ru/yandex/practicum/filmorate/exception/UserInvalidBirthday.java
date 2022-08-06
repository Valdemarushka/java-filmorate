package ru.yandex.practicum.filmorate.exception;

public class UserInvalidBirthday extends RuntimeException {
    public UserInvalidBirthday(String message) {
        super(message);
    }
}
