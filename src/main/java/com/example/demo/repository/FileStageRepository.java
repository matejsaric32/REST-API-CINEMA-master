package com.example.demo.repository;

import com.example.demo.domain.Seat;
import com.example.demo.domain.Stage;
import org.springframework.stereotype.Repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FileStageRepository implements StageRepository {
    private static final Integer NUMBER_OF_STAGE_ROWS = 3;
    private static final String SEATS_FILE_LOCATION = "dat/stages.txt";

    @Override
    public List<Stage> getAllStages() {

        List<Stage> stageList = new ArrayList<>();
        Path stageFilePath = Path.of(SEATS_FILE_LOCATION);
        FileSeatRepository fileSeatRepository = new FileSeatRepository();


        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(stageFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < lines.size()/NUMBER_OF_STAGE_ROWS; i++) {

            String idString = lines.get(i * NUMBER_OF_STAGE_ROWS);
            Long id = Long.parseLong(idString);
            String stageName = lines.get(i * NUMBER_OF_STAGE_ROWS + 1);
            String seatsGroup = lines.get(i * NUMBER_OF_STAGE_ROWS + 2);
            String[] seatsIds = seatsGroup.split("\\s+");

            List<Seat> seatList = new ArrayList<>();

            for(String seatIdString : seatsIds) {
                Long seatId = Long.parseLong(seatIdString);
                Optional<Seat> seat = fileSeatRepository.getSeatById(seatId);
                seat.ifPresent(seatList::add);
            }

            Stage newStage = new Stage(id, stageName, seatList);
            stageList.add(newStage);
        }

        return stageList;
    }

    @Override
    public Optional<Stage> getStageById(Long id) {
        return getAllStages().stream().filter(stage -> stage.getId().equals(id)).findFirst();
    }

    @Override
    public void saveNewStage(Stage newStage) {

        List<Stage>stages = getAllStages();
        Long highestId = stages.stream()
            .mapToLong(Stage::getId)
            .max().orElse(0);

        newStage.setId(highestId + 1);
        stages.add(newStage);

        saveAllStagesToFile(stages);
    }

    @Override
    public Optional<Stage> updateStage(Stage updatedStage, Long id) {

        List<Stage>stages = getAllStages();
        Optional<Stage> stage = stages.stream().filter(s -> s.getId().equals(id)).findFirst();

        stage.ifPresent(value -> {
            value.setId(value.getId());
            value.setName(value.getName());
        });

        if (stage.isPresent()){
            stage.get().setName(updatedStage.getName());
            stage.get().setSeats(updatedStage.getSeats());
            saveAllStagesToFile(stages);
        }

        return stage;
    }

    @Override
    public void deleteStage(Long id) {

        List<Stage>stages = getAllStages();
        Integer oldSize = stages.size();

        stages.removeIf(stage -> stage.getId().equals(id));

        if (oldSize != stages.size()) saveAllStagesToFile(stages);
    }

    private void saveAllStagesToFile(List<Stage> stages) {

        Path stageFilePath = Path.of(SEATS_FILE_LOCATION);

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(stageFilePath.toFile(), false))) {

            for(Stage stage : stages) {

                String stageId = String.valueOf(stage.getId());
                String stageName = stage.getName();
                StringBuilder seatsGroup = new StringBuilder();
                stage.getSeats().forEach(seat ->
                    seatsGroup.append(seat.getId()).append(" "));
                String seats = seatsGroup.toString();

                bw.write(stageId);
                bw.newLine();
                bw.write(stageName);
                bw.newLine();
                bw.write(seats);
                bw.newLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
