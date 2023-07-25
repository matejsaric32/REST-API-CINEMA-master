package com.example.demo.repository;

import com.example.demo.domain.Seat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class FileSeatRepository implements SeatRepository {
    private static final Integer NUMBER_OF_SEAT_ROWS = 3;
    private static final String SEATS_FILE_LOCATION = "dat/seats.txt";

    @Override
    public List<Seat> getAllSeats() {
        List<Seat> seatList = new ArrayList<>();
        Path seatsFilePath = Path.of(SEATS_FILE_LOCATION);

        List<String> lines = null;
        try {
            lines = Files.readAllLines(seatsFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for(int i = 0; i < lines.size()/NUMBER_OF_SEAT_ROWS; i++) {
            String idString = lines.get(i * NUMBER_OF_SEAT_ROWS);
            Long id = Long.parseLong(idString);
            String rowName = lines.get(i * NUMBER_OF_SEAT_ROWS + 1);
            Integer rowPosition = Integer.parseInt(lines.get(i * NUMBER_OF_SEAT_ROWS + 2));
            Seat newSeat = new Seat(id, rowName, rowPosition);
            seatList.add(newSeat);
        }

        return seatList;
    }

    @Override
    public Optional<Seat> getSeatById(Long id) {
        return getAllSeats().stream().filter(s -> s.getId().equals(id)).findFirst();
    }

    @Override
    public void saveNewSeat(Seat newSeat) {
        List<Seat> allSeats = getAllSeats();
        Long highestId = allSeats.stream()
                .map(s -> s.getId())
                .max((id1, id2) -> id1.compareTo(id2))
                        .get();

        newSeat.setId(highestId + 1);
        allSeats.add(newSeat);

        saveAllSeatsToFile(allSeats);
    }

    private static void saveAllSeatsToFile(List<Seat> allSeats) {
        Path seatsFilePath = Path.of(SEATS_FILE_LOCATION);

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(seatsFilePath.toFile(), false))) {

            for(Seat seat : allSeats) {
                String seatId = String.valueOf(seat.getId());
                String rowName = seat.getRowName();
                String positionInRow = String.valueOf(seat.getPositionInRow());
                bw.write(seatId);
                bw.newLine();
                bw.write(rowName);
                bw.newLine();
                bw.write(positionInRow);
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Seat> updateSeat(Seat updatedSeat, Long id) {

        List<Seat> allSeats = getAllSeats();

        Optional updatedSeatOptional = Optional.empty();

        for(Seat seat : allSeats) {
            if(seat.getId().equals(id)) {
                seat.setRowName(updatedSeat.getRowName());
                seat.setPositionInRow(updatedSeat.getPositionInRow());
                updatedSeatOptional = Optional.of(seat);
                break;
            }
        }

        if(updatedSeatOptional.isPresent()) {
            saveAllSeatsToFile(allSeats);
        }

        return updatedSeatOptional;
    }

    @Override
    public void deleteSeat(Long id) {
        List<Seat> allSeats = getAllSeats();

        List<Seat> listWithoutTheDeletedSeat =
                allSeats.stream().filter(s -> !s.getId().equals(id))
                .collect(Collectors.toList());

        if(listWithoutTheDeletedSeat.size() != allSeats.size()) {
            saveAllSeatsToFile(listWithoutTheDeletedSeat);
        }
    }
}
