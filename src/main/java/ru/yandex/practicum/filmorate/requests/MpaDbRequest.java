package ru.yandex.practicum.filmorate.requests;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MpaDbRequest {
    public String sqlGetMpaById = "SELECT * FROM mpa WHERE id_mpa = ?";
    public String sqlGetAllMpa = "SELECT * FROM mpa";
}
