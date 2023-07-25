package com.example.demo.repository;

import com.example.demo.domain.Stage;

import java.util.List;
import java.util.Optional;

public interface StageRepository {

  List<Stage> getAllStages();

  Optional<Stage> getStageById(Long id);

  void  saveNewStage(Stage newStage);

  Optional<Stage> updateStage(Stage updatedStage, Long id);

  void deleteStage(Long id);
}
