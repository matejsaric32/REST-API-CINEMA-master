package com.example.demo.repository;

import com.example.demo.domain.Projection;
import com.example.demo.domain.Stage;
import com.example.demo.utils.DateTimeUtils;
import org.springframework.stereotype.Repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FileProjectionRepository implements ProjectionRepository {

    private static final Integer NUMBER_OF_STAGE_PROJECTION = 4;
    private static final String PROJECTIONS_FILE_LOCATION = "dat/projections.txt";

    @Override
    public List<Projection> getAllProjections() {

        List<Projection> projections = new ArrayList<>();
        Path projectionFilePath = Path.of(PROJECTIONS_FILE_LOCATION);
        FileStageRepository fileStageRepository = new FileStageRepository();

        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(projectionFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < lines.size() / NUMBER_OF_STAGE_PROJECTION; i++) {

            String idString = lines.get(i * NUMBER_OF_STAGE_PROJECTION);
            Long id = Long.parseLong(idString);
            String projectionName = lines.get(i * NUMBER_OF_STAGE_PROJECTION + 1);
            LocalDateTime projectionDateAndTime = LocalDateTime.parse(lines.get(i * NUMBER_OF_STAGE_PROJECTION + 2), DateTimeUtils.formatter);

            Optional<Stage> stage = fileStageRepository.getStageById(Long.parseLong(lines.get(i * NUMBER_OF_STAGE_PROJECTION + 3)));

            stage.ifPresent(value -> projections.add(new Projection(id, projectionName, projectionDateAndTime, value)));
        }

        return projections;
    }

    @Override
    public Optional<Projection> getProjectionById(Long id) {
        return getAllProjections().stream().filter(projection -> projection.getId().equals(id)).findFirst();
    }

    @Override
    public void saveNewProjection(Projection newProjection) {

        List<Projection> projections = getAllProjections();
        Long highestId = projections.stream()
            .mapToLong(Projection::getId)
            .max().orElse(0);

        newProjection.setId(highestId + 1);
        projections.add(newProjection);

        saveAllProjectionsToFile(projections);
    }

    @Override
    public Optional<Projection> updateProjection(Projection updatedProjection, Long id) {

        List<Projection>projections = getAllProjections();
        Optional<Projection> projection = projections.stream().filter(p -> p.getId().equals(id)).findFirst();

        projection.ifPresent(value -> {
            value.setName(updatedProjection.getName());
            value.setDateTime(updatedProjection.getDateTime());
            value.setStage(updatedProjection.getStage());
        });

        saveAllProjectionsToFile(projections);

        return projection;
    }

    @Override
    public void deleteProjection(Long id) {

        List<Projection> projections = getAllProjections();
        Integer oldSize = projections.size();

        projections.removeIf(projection -> projection.getId().equals(id));
        if (oldSize != projections.size()) saveAllProjectionsToFile(projections);

        saveAllProjectionsToFile(projections);
    }

    private void saveAllProjectionsToFile(List<Projection> projections) {

        Path projectionFilePath = Path.of(PROJECTIONS_FILE_LOCATION);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(projectionFilePath.toFile(), false))) {

            for (Projection projection : projections) {

                String projectionId = String.valueOf(projection.getId());
                String projectionName = projection.getName();
                String projectionDateAndTime = projection.getDateTime().format(DateTimeUtils.formatter);
                String stageId = String.valueOf(projection.getStage().getId());

                bw.write(projectionId);
                bw.newLine();
                bw.write(projectionName);
                bw.newLine();
                bw.write(projectionDateAndTime);
                bw.newLine();
                bw.write(stageId);
                bw.newLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
