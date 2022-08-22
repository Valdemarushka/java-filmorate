package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    public User createUser(User user);

    public User updateUser(User updateUser);

    public List<User> getAllUser();

    public User getUserById(Integer id);

    public void deleteAllUser();

    public void deleteUser(Integer id);


}
