package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.RatingService;

import java.util.Collection;

@RequestMapping
@RestController
public class MpaController {
    private final RatingService ratingService;

    @Autowired
    public MpaController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping("/mpa/{id}")
    public Mpa getMpaById(@PathVariable Integer id) {
        return ratingService.getRatingById(id);
    }

    @GetMapping("/mpa")
    public Collection<Mpa> getAllMpa() {
        return ratingService.getAllRatings();
    }
}
