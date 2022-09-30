package ru.yandex.practicum.filmorate.requests;

import lombok.experimental.UtilityClass;

@UtilityClass
public class LikesDbRequest {
    public static String sqlAddLike = "INSERT INTO likes(id_film, id_user) VALUES (?, ?)";
    public static String sqlRemoveLike = "DELETE FROM likes WHERE id_film = ? AND id_user = ?";
    public static String sqlGetIdUserWhoLikesFilm = "SELECT id_user FROM likes WHERE id_film = ?";
}
