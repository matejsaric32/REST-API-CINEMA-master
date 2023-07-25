package com.example.demo.repository;

import com.example.demo.domain.Cinema;
import com.example.demo.domain.Seat;

import java.util.List;
import java.util.Optional;

public interface CinemaRepository {
    List<Cinema> getAllCinema();

    Optional<Cinema> getCinemaById(Long id);

    void saveNewCinema(Cinema newCinema);

    Optional<Cinema> updateCinema(Cinema updatedCinema, Long id);

    void deleteCinema(Long id);
}
