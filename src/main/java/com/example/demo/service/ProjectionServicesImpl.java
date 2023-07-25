package com.example.demo.service;

import com.example.demo.domain.Projection;
import com.example.demo.repository.ProjectionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProjectionServicesImpl implements ProjectionService {

    ProjectionRepository projectionRepository;

    @Override
    public List<Projection> getAllProjection() {
        return projectionRepository.getAllProjections();
    }

    @Override
    public Optional<Projection> getProjectionById(Long id) {
        return projectionRepository.getProjectionById(id);
    }

    @Override
    public void saveNewProjection(Projection newProjection) {
        projectionRepository.saveNewProjection(newProjection);
    }

    @Override
    public Optional<Projection> updateProjection(Projection updatedProjection, Long id) {
        return projectionRepository.updateProjection(updatedProjection, id);
    }

    @Override
    public void deleteProjection(Long id) {
        projectionRepository.deleteProjection(id);
    }
}
