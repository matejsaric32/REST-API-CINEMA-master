package com.example.demo.repository;

import com.example.demo.domain.Projection;
import com.example.demo.domain.Seat;

import java.util.List;
import java.util.Optional;


public interface ProjectionRepository {

    List<Projection> getAllProjections();

    Optional<Projection> getProjectionById(Long id);

    void saveNewProjection(Projection newProjection);

    Optional<Projection> updateProjection(Projection updatedProjection, Long id);

    void deleteProjection(Long id);
}
