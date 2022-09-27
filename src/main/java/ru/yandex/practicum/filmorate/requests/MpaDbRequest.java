package ru.yandex.practicum.filmorate.requests;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MpaDbRequest {
    public static String sqlGetMpaById = "SELECT * FROM mpa WHERE id_mpa = ?";
    public static String sqlGetAllMpa = "SELECT * FROM mpa";
}
