package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class User {
    private Integer id;
    private String email;
    private String name;
    private String login;
    private LocalDate birthday;
    private Set<Integer> friends;
    private HashMap<Integer, Integer> friendsStatus;
    private Set<Integer> likedFilms;

    public User() {
        super();
    }

    public User(String email, String name, String login, LocalDate birthday) {
        this.email = email;
        if (name == null || name.isEmpty() || name.isBlank()) {
            this.name = login;
        } else {
            this.name = name;
        }
        this.login = login;
        this.birthday = birthday;
    }

    public User(Integer id, String email, String name, String login, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        if (name == null || name.isEmpty() || name.isBlank()) {
            this.name = login;
        } else {
            this.name = name;
        }
        this.birthday = birthday;
    }

    public User(Integer id, String email, String name, String login, LocalDate birthday, Set<Integer> friends,
                HashMap<Integer, Integer> friendsStatus, Set<Integer> likedFilms) {
        this.id = id;
        this.email = email;
        this.login = login;
        if (name == null || name.isEmpty() || name.isBlank()) {
            this.name = login;
        } else {
            this.name = name;
        }

        this.birthday = birthday;
        this.friends = friends;
        this.friendsStatus = friendsStatus;
        this.likedFilms = likedFilms;
    }

    public Set<Integer> getFriends() {
        if (friends == null) {
            return new HashSet<>();
        }
        return friends;
    }
}


