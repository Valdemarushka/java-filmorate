package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@Builder
public class User {
    Integer id;

    @Email(message = "Email не верно")
    String email;

    String name;

    @NotEmpty
    @NotNull
    String login;

    @Past(message = "Дата рождения в будущем")
    LocalDate birthday;
}
