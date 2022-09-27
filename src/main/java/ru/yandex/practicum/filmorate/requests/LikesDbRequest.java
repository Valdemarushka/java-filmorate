package ru.yandex.practicum.filmorate.requests;

import lombok.experimental.UtilityClass;

@UtilityClass
public class LikesDbRequest {
    public String sqlAddLike = "INSERT INTO likes(id_film, id_user) VALUES (?, ?)";
    public String sqlRemoveLike = "DELETE FROM likes WHERE id_film = ? AND id_user = ?";
    public String sqlGetIdUserWhoLikesFilm = "SELECT id_user FROM likes WHERE id_film = ?";
}
