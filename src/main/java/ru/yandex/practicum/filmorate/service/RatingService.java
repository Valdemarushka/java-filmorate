package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.MpaDao;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

import static ru.yandex.practicum.filmorate.tools.ModelTools.idValidator;

@Service
public class RatingService {
    private final MpaDao mpaDao;

    @Autowired
    public RatingService(MpaDao mpaDao) {
        this.mpaDao = mpaDao;
    }

    public Mpa getRatingById(Integer id) {
        idValidator(id);
        return mpaDao.getMpaById(id);
    }

    public Collection<Mpa> getAllRatings() {
        return mpaDao.getAllMpa();
    }
}