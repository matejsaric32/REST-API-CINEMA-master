package com.example.demo.service;

import com.example.demo.domain.Projection;

import java.util.List;
import java.util.Optional;

public interface ProjectionService {
    List<Projection> getAllProjection();

    Optional<Projection> getProjectionById(Long id);

    void saveNewProjection(Projection newProjection);

    Optional<Projection> updateProjection(Projection updatedProjection, Long id);

    void deleteProjection(Long id);
}
