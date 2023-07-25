package com.example.demo.service;

import com.example.demo.domain.Seat;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface SeatService {
    List<Seat> getAllSeats();

    Optional<Seat> getSeatById(Long id);

    void saveNewSeat(Seat newSeat);

    Optional<Seat> updateSeat(Seat updatedSeat, Long id);

    void deleteSeat(Long id);
}
