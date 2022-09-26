package ru.yandex.practicum.filmorate.requests;

public class GenreDbRequest {
    public static String sqlGetGenreById = "SELECT * FROM genre WHERE id_genre = ?";
    public static String sqlGetAllGenres = "SELECT * FROM genre";
}
