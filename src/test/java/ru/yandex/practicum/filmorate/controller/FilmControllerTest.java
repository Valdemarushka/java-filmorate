package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.FilmInvalidDataRelease;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.yandex.practicum.filmorate.tools.ModelTools.validateFilm;

class FilmControllerTest {

    @Test
    void WrongDateReleaseTest() {
        Film film = Film.builder()
                .name("Шматрица")
                .id(null)
                .description("Группа буйно-помешанных, бежит из дурдома особого режима, вообразив себя не кем" +
                        " иными как, советскими партизанами.")
                .releaseDate(LocalDate.of(1700, 12, 11))
                .duration(100)
                .build();
        assertThrows(FilmInvalidDataRelease.class, () -> validateFilm(film));
    }


}