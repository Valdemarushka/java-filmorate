package ru.yandex.practicum.filmorate.requests;

public class FriendsDbRequest {
    public static String sqlAddInFriends = "INSERT INTO friendship(id_user, id_friend, id_status) VALUES (?, ?, ?)";
    public static String sqlChangeStatus = "UPDATE friendship SET id_status = ? WHERE id_user = ? AND id_friend = ?";

    public static String sqlRemoveFromFriends = "DELETE FROM friendship WHERE id_user = ? AND id_friend = ?";
    public static String sqlGetUserFriends = "SELECT f.id_friend, u.id_user, u.email, u.login, u.name, u.birthday " +
            "FROM friendship AS f INNER JOIN users AS u ON f.id_friend = u.id_user  " +
            "WHERE f.id_user = ? AND f.id_status = ?  GROUP BY f.id_friend";
    public static String sqlGetСommonFriends = "SELECT f1.id_friend, u.id_user, u.email, u.login, u.name, u.birthday " +
            "FROM friendship f1 JOIN friendship f2 ON f2.id_user = ? AND f2.id_friend = f1.id_friend " +
            "JOIN users AS u ON f1.id_friend = u.id_user WHERE f1.id_user = ?";
    public static String sqlGetFriends = "SELECT id_friend FROM friendship WHERE id_user = ?";
    public static String sqlGetFriendsStatus = "SELECT * FROM friendship WHERE id_user = ?";
}
