package ru.yandex.practicum.filmorate.requests;

import lombok.experimental.UtilityClass;

@UtilityClass
public class GenreDbRequest {
    public String sqlGetGenreById = "SELECT * FROM genre WHERE id_genre = ?";
    public String sqlGetAllGenres = "SELECT * FROM genre";
}
