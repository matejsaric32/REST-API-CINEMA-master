package com.example.demo.controller;

import com.example.demo.domain.Projection;
import com.example.demo.service.ProjectionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("cinema/projection")
@AllArgsConstructor
public class ProjectionController {

    private ProjectionService projectionService;

    @GetMapping
    public ResponseEntity<?> getProjections() {
        return ResponseEntity.status(HttpStatus.OK).body(projectionService.getAllProjection());
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> getProjectionById(@PathVariable Long id) {
        Optional<Projection> optionalProjection = projectionService.getProjectionById(id);

        if(optionalProjection.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(optionalProjection.get());
        }
        else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveNewProjection(@RequestBody Projection newProjection) {
        projectionService.saveNewProjection(newProjection);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> updateProjection(@RequestBody Projection updatedProjection, @PathVariable Long id) {
        Optional<Projection> updatedProjectionOptional = projectionService.updateProjection(updatedProjection, id);

        if(updatedProjectionOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(updatedProjectionOptional.get());
        }
        else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSeatById(@PathVariable Long id) {
        projectionService.deleteProjection(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
