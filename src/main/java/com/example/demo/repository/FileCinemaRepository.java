package com.example.demo.repository;

import com.example.demo.domain.*;
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
public class FileCinemaRepository implements CinemaRepository {

    public static final Integer NUMBER_OF_CINEMA_ROWS = 8;

    private static final String CINEMA_FILE_LOCATION = "dat/cinemas.txt";

    @Override
    public List<Cinema> getAllCinema() {

        List<Cinema> cinemaList = new ArrayList<>();
        Path cinemaFilePath = Path.of("dat/cinemas.txt");
        FileProjectionRepository fileProjectionRepository = new FileProjectionRepository();
        FileStageRepository fileStageRepository = new FileStageRepository();

        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(cinemaFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < lines.size() / NUMBER_OF_CINEMA_ROWS; i++) {

            String idString = lines.get(i * NUMBER_OF_CINEMA_ROWS);
            Long id = Long.parseLong(idString);
            String cinemaName = lines.get(i * NUMBER_OF_CINEMA_ROWS + 1);
            String cinemaStreet = lines.get(i * NUMBER_OF_CINEMA_ROWS + 2);
            String cinemaHouseNumber = lines.get(i * NUMBER_OF_CINEMA_ROWS + 3);
            String cinemaPostalCode = lines.get(i * NUMBER_OF_CINEMA_ROWS + 4);
            String cinemaCity = lines.get(i * NUMBER_OF_CINEMA_ROWS + 5);
            String projectionsGroup = lines.get(i * NUMBER_OF_CINEMA_ROWS + 6);
            String[] projectionsIds = projectionsGroup.split("\\s+");

            List<Projection> projectionList = new ArrayList<>();

            for (String projectionIdString : projectionsIds) {

                Long projectionId = Long.parseLong(projectionIdString);
                Optional<Projection> projection = fileProjectionRepository.getProjectionById(projectionId);
                projection.ifPresent(projectionList::add);
            }

            String stagesGroup = lines.get(i * NUMBER_OF_CINEMA_ROWS + 7);
            String[] stagesIds = stagesGroup.split("\\s+");

            List<Stage> stagesList = new ArrayList<>();

            for (String stagesIdString : stagesIds) {

                Long stageId = Long.parseLong(stagesIdString);
                Optional<Stage> stage = fileStageRepository.getStageById(stageId);
                stage.ifPresent(stagesList::add);
            }

            cinemaList.add(new Cinema(id, cinemaName,
                new Address(cinemaStreet, cinemaHouseNumber, Integer.parseInt(cinemaPostalCode),
                    CITY.valueOf(cinemaCity)),
                projectionList, stagesList));
        }

        return cinemaList;
    }

    @Override
    public Optional<Cinema> getCinemaById(Long id) {
        return getAllCinema().stream().filter(cinema -> cinema.getId().equals(id)).findFirst();
    }

    @Override
    public void saveNewCinema(Cinema newCinema) {

        List<Cinema> cinemas = getAllCinema();
        Long highestId = cinemas.stream()
            .mapToLong(Cinema::getId)
            .max().orElse(0);

        newCinema.setId(++highestId);
        cinemas.add(newCinema);

        saveAllCinemasToFile(cinemas);
    }

    @Override
    public Optional<Cinema> updateCinema(Cinema updatedCinema, Long id) {

        List<Cinema> cinemas = getAllCinema();
        Optional<Cinema> cinema = cinemas.stream().filter(c -> c.getId().equals(id)).findFirst();

        cinema.ifPresent(value -> {
            value.setName(updatedCinema.getName());
            value.setAddress(updatedCinema.getAddress());
            value.setProjectionList(updatedCinema.getProjectionList());
            value.setStageList(updatedCinema.getStageList());
        });

        saveAllCinemasToFile(cinemas);

        return cinema;
    }

    @Override
    public void deleteCinema(Long id) {

        List<Cinema> cinemas = getAllCinema();
        Integer oldSize = cinemas.size();

        cinemas.removeIf(cinema -> cinema.getId().equals(id));
        if (oldSize != cinemas.size()) saveAllCinemasToFile(cinemas);
    }

    private void saveAllCinemasToFile(List<Cinema> cinemas) {

        Path cinemaFilePath = Path.of(CINEMA_FILE_LOCATION);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(cinemaFilePath.toFile(), false))) {

            for (Cinema cinema : cinemas) {

                String cinemaId = String.valueOf(cinema.getId());
                String cinemaName = cinema.getName();

                String cinemaStreet = cinema.getAddress().getStreet();
                String cinemaHouseNumber = cinema.getAddress().getHouseNumber();
                String cinemaPostalCode = cinema.getAddress().getPostalCode().toString();
                String cinemaCity = cinema.getAddress().getCity().toString();

                StringBuilder projectionIds = new StringBuilder();
                cinema.getProjectionList().stream().forEach(projection -> projectionIds.append(projection.getId()).append(" "));

                StringBuilder stageIds = new StringBuilder();
                cinema.getStageList().stream().forEach(stage -> stageIds.append(stage.getId()).append(" "));

                bw.write(cinemaId);
                bw.newLine();
                bw.write(cinemaName);
                bw.newLine();
                bw.write(cinemaStreet);
                bw.newLine();
                bw.write(cinemaHouseNumber);
                bw.newLine();
                bw.write(cinemaPostalCode);
                bw.newLine();
                bw.write(cinemaCity);
                bw.newLine();
                bw.write(projectionIds.toString());
                bw.newLine();
                bw.write(stageIds.toString());
                bw.newLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
