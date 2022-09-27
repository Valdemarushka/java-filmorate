package ru.yandex.practicum.filmorate.requests;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FilmDbRequest {
    public static String sqlAddFilm = "INSERT INTO Film(name, description, release_date, duration, id_mpa) " +
            "VALUES (?, ?, ?, ?, ?)";
    public static String sqlGetFilmId = "SELECT id_film FROM film WHERE name = ? AND " +
            "description = ?";
    public static String sqlUpdateFilm = "UPDATE film SET name = ?, description = ?, release_date = ?, " +
            "duration = ?, id_mpa = ? WHERE id_film = ?";
    public static String sqlDeleteAllGenresInFilm = "DELETE FROM filmgenre WHERE id_film = ?";
    public static String sqlGetLikes = "SELECT id_user FROM likes WHERE id_film = ?";
    public static String sqlGetIdGenresOfFilm = "SELECT id_genre FROM filmgenre WHERE id_film = ?";
    public static String sqlAddGenreInFilm = "INSERT INTO filmgenre(id_film, id_genre) VALUES(?, ?)";
    public static String sqlGetAllFilms = "SELECT * FROM film";
    public static String sqlGetFilmById = "SELECT * FROM film WHERE id_film = ?";
    public static String sqlDeleteFilm = "DELETE FROM film WHERE id_film = ?";
}
