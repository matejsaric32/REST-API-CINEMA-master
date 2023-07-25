package com.example.demo.repository;

import com.example.demo.domain.Seat;

import java.util.List;
import java.util.Optional;

public interface SeatRepository {
    List<Seat> getAllSeats();

    Optional<Seat> getSeatById(Long id);

    void saveNewSeat(Seat newSeat);

    Optional<Seat> updateSeat(Seat updatedSeat, Long id);

    void deleteSeat(Long id);
}
