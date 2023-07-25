package com.example.demo.controller;

import com.example.demo.domain.Stage;
import com.example.demo.service.StageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("cinema/stage")
@AllArgsConstructor
public class StageController {

    private StageService stageService;

    @GetMapping
    public ResponseEntity<?> getStages() {
        return ResponseEntity.status(HttpStatus.OK).body(stageService.getAllStages());
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> getStageById(@PathVariable Long id) {
        Optional<Stage> optionalStage = stageService.getStageById(id);

        if(optionalStage.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(optionalStage.get());
        }
        else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveNewStage(@RequestBody Stage newStage) {
        stageService.saveNewStage(newStage);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> updateStage(@RequestBody Stage updatedStage, @PathVariable Long id) {
        Optional<Stage> updatedStageOptional = stageService.updateStage(updatedStage, id);

        if(updatedStageOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(updatedStageOptional.get());
        }
        else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStageById(@PathVariable Long id) {
        stageService.deleteStage(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
