package ru.yandex.practicum.filmorate.requests;

public class UserDbRequest {
    public static String sqlCreateUser = "INSERT INTO users (email, name, login, birthday) VALUES (?, ?, ?, ?)";
    public static String sqlGetUserId = "SELECT id_user FROM users WHERE email = ?";
    public static String sqlUpdateUser = "UPDATE users SET email = ?, name = ?, login = ?, " +
            "birthday = ? WHERE id_user = ?";
    public static String sqlGetAllUser = "SELECT * FROM users";

    public static String sqlGetLike = "SELECT id_film FROM likes WHERE id_user = ?";
    public static String sqlGetUserById = "SELECT* FROM users WHERE id_user = ?";
    public static String sqlDeleteUser = ("DELETE FROM users WHERE id_user = ?");

}
