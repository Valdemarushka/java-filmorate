package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ObjectIsNull;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.yandex.practicum.filmorate.tools.ModelTools.validateFilm;
import static ru.yandex.practicum.filmorate.tools.ModelTools.validateUser;

class UserControllerTest {
    @Test
    void nullObjectTest() {
        assertThrows(ObjectIsNull.class, () -> validateFilm(null));
    }

    @Test
    void wrongEmailWithoutSymbolTest() {
        User user = User.builder()
                .id(null)
                .name("имя")
                .email("emailmail.ru")
                .login("login")
                .birthday(LocalDate.of(1988, 11, 11))
                .build();
        assertThrows(ValidateException.class, () -> validateUser(user));
    }

    @Test
    void emptyEmailTest() {
        User user = User.builder()
                .id(null)
                .name("имя")
                .email("")
                .login("login")
                .birthday(LocalDate.of(1988, 11, 11))
                .build();
        assertThrows(ValidateException.class, () -> validateUser(user));
    }

    @Test
    void emptyLoginTest() {
        User user = User.builder()
                .id(null)
                .name("имя")
                .email("sdaf@sdaf.ru")
                .login("")
                .birthday(LocalDate.of(1988, 11, 11))
                .build();
        assertThrows(ValidateException.class, () -> validateUser(user));
    }

    @Test
    void loginWithSpaceTest() {
        User user = User.builder()
                .id(null)
                .name("имя")
                .email("sdaf@sdaf.ru")
                .login("dsf sadf")
                .birthday(LocalDate.of(1988, 11, 11))
                .build();
        assertThrows(ValidateException.class, () -> validateUser(user));
    }

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

    @Test
    void wrongDateBirthTest() {
        User user = User.builder()
                .id(null)
                .name("asd")
                .email("sdaf@sdaf.ru")
                .login("Login")
                .birthday(LocalDate.of(3000, 11, 11))
                .build();
        assertThrows(ValidateException.class, () -> validateUser(user));
    }

}