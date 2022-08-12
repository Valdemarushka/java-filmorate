package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.yandex.practicum.filmorate.tools.ModelTools.validateFilm;

class FilmControllerTest {

    @Test
    void nullObjectTest() {
        assertThrows(ObjectIsNull.class, () -> validateFilm(null));
    }

    @Test
    void emptyNameTest() {
        Film film = Film.builder()
                .name("")
                .id(null)
                .description("описание")
                .releaseDate(LocalDate.of(1999, 12, 11))
                .duration(100)
                .build();
        assertThrows(FilmInvalidName.class, () -> validateFilm(film));
    }

    @Test
    void tooLongDescriptionTest() {
        Film film = Film.builder()
                .name("Шматрица")
                .id(null)
                .description("Группа буйно-помешанных, бежит из дурдома особого режима, вообразив себя не кем" +
                        " иными как, советскими партизанами, попавшими во враждебную обстановку, а местные санитары," +
                        " по их мнению – это самые что ни на есть гестаповцы. Возглавляет всю эту шайку матёрых " +
                        "пациентов дурдома и рецидивистов шизоид Матвей Матвеевич по кличке - Шоколадный заяц " +
                        "(Морфиус в оригинале).")
                .releaseDate(LocalDate.of(1999, 12, 11))
                .duration(100)
                .build();
        assertThrows(FilmLengthDescriptionTooLong.class, () -> validateFilm(film));
    }

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

    @Test
    void WrongDurationTest() {
        Film film = Film.builder()
                .name("Шматрица")
                .id(null)
                .description("Группа буйно-помешанных, бежит из дурдома особого режима, вообразив себя не кем" +
                        " иными как, советскими партизанами.")
                .releaseDate(LocalDate.of(1999, 12, 11))
                .duration(-100)
                .build();
        assertThrows(FilmDurationIsNegative.class, () -> validateFilm(film));
    }


}