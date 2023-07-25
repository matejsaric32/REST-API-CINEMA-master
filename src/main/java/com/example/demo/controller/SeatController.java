package com.example.demo.controller;

import com.example.demo.domain.Seat;
import com.example.demo.service.SeatService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("cinema/seat")
@AllArgsConstructor
public class SeatController {

    private SeatService seatService;

    @GetMapping
    public ResponseEntity<?> getSeats() {
        return ResponseEntity.status(HttpStatus.OK).body(seatService.getAllSeats());
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> getSeatById(@PathVariable Long id) {
        Optional<Seat> optionalSeat = seatService.getSeatById(id);

        if(optionalSeat.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(optionalSeat.get());
        }
        else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveNewSeat(@RequestBody Seat newSeat) {
        seatService.saveNewSeat(newSeat);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> saveNewSeat(@RequestBody Seat updatedSeat, @PathVariable Long id) {
        Optional<Seat> updatedSeatOptional = seatService.updateSeat(updatedSeat, id);

        if(updatedSeatOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(updatedSeatOptional.get());
        }
        else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSeatById(@PathVariable Long id) {
        seatService.deleteSeat(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
