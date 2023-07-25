package com.example.demo.service;


import com.example.demo.domain.Cinema;
import com.example.demo.repository.CinemaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CinemaServicesImpl implements CinemaServices {

    CinemaRepository cinemaRepository;

    @Override
    public List<Cinema> getAllCinema() {
        return cinemaRepository.getAllCinema();
    }

    @Override
    public Optional<Cinema> getCinemaById(Long id) {
        return cinemaRepository.getCinemaById(id);
    }

    @Override
    public void saveNewCinema(Cinema newCinema) {
        cinemaRepository.saveNewCinema(newCinema);
    }

    @Override
    public Optional<Cinema> updateCinema(Cinema updatedCinema, Long id) {
        return cinemaRepository.updateCinema(updatedCinema, id);
    }

    @Override
    public void deleteCinema(Long id) {
        cinemaRepository.deleteCinema(id);
    }
}
