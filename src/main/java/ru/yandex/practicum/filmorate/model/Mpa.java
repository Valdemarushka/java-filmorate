package ru.yandex.practicum.filmorate.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class Mpa {
    private int id;
    private String name;

    public Mpa(int id) {
        this.id = id;
    }
}
