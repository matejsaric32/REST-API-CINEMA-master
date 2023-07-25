package com.example.demo.controller;


import com.example.demo.domain.Cinema;
import com.example.demo.domain.Stage;
import com.example.demo.service.CinemaServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("cinema")
@AllArgsConstructor
public class CinemaController {

    private CinemaServices cinemaServices;

    @GetMapping
    public ResponseEntity<?> getCinemas() {
        return ResponseEntity.status(HttpStatus.OK).body(cinemaServices.getAllCinema());
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> getCinemaById(@PathVariable Long id) {
        Optional<Cinema> optionalStage = cinemaServices.getCinemaById(id);

        if(optionalStage.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(optionalStage.get());
        }
        else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveNewStage(@RequestBody Cinema newCinema) {
        cinemaServices.saveNewCinema(newCinema);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> updateStage(@RequestBody Cinema updatedCinema, @PathVariable Long id) {
        Optional<Cinema> updatedCinemaOptional = cinemaServices.updateCinema(updatedCinema, id);

        if(updatedCinemaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(updatedCinemaOptional.get());
        }
        else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStageById(@PathVariable Long id) {
        cinemaServices.deleteCinema(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
