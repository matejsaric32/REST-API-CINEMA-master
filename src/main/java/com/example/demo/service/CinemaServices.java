package com.example.demo.service;

import com.example.demo.domain.Cinema;

import java.util.List;
import java.util.Optional;

public interface CinemaServices {
    List<Cinema> getAllCinema();

    Optional<Cinema> getCinemaById(Long id);

    void saveNewCinema(Cinema newCinema);

    Optional<Cinema> updateCinema(Cinema updatedCinema, Long id);

    void deleteCinema(Long id);
}
