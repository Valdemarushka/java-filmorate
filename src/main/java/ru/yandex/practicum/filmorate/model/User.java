package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class User {
    Integer id;
    String email;
    String name;
    String login;
    LocalDate birthday;
    private Set<Integer> friends;

    public Set<Integer> getFriends() {
        if (friends == null) {
            return new HashSet<>();
        }
        return friends;
    }
}


