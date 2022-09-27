package ru.yandex.practicum.filmorate.requests;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserDbRequest {
    public String sqlCreateUser = "INSERT INTO users (email, name, login, birthday) VALUES (?, ?, ?, ?)";
    public String sqlGetUserId = "SELECT id_user FROM users WHERE email = ?";
    public String sqlUpdateUser = "UPDATE users SET email = ?, name = ?, login = ?, " +
            "birthday = ? WHERE id_user = ?";
    public String sqlGetAllUser = "SELECT * FROM users";
    public String sqlGetLike = "SELECT id_film FROM likes WHERE id_user = ?";
    public String sqlGetUserById = "SELECT* FROM users WHERE id_user = ?";
    public String sqlDeleteUser = ("DELETE FROM users WHERE id_user = ?");

}
