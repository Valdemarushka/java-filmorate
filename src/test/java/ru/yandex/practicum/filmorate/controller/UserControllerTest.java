package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.yandex.practicum.filmorate.tools.ModelTools.validateUser;

class UserControllerTest {
    @Test
    void emptyNameTest() {
        User user = User.builder()
                .id(null)
                .name("")
                .email("sdaf@sdaf.ru")
                .login("Login")
                .birthday(LocalDate.of(1988, 11, 11))
                .build();
        validateUser(user);
        assertEquals("Login", user.getName());
    }
}