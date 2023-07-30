package com.example.demo.controller;

import com.example.demo.domain.Seat;
import com.example.demo.repository.SeatRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SeatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SeatRepository seatRepository;

    ObjectMapper objectMapper = new ObjectMapper();


    @Test
    @Order(1)
    public void shouldReturnAllSeatsFromFile() throws Exception {
        this.mockMvc.perform(get("/cinema/seat"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(16)));
    }

    @Test
    @Order(2)
    public void shouldReturnSeatById() throws Exception {

        final String SEAT_ID = "1";
        final String row = "ZZ";
        final Integer positionInRow = 1;

        this.mockMvc.perform(get("/cinema/seat/" + SEAT_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.rowName").value(row))
                .andExpect(jsonPath("$.positionInRow").value(positionInRow));

    }

    @Test
    public void shouldReturn204IfSeatIdDoesNotExist() throws Exception {

        final String SEAT_ID = "1111111111";

        this.mockMvc.perform(get("/cinema/seat/" + SEAT_ID))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(3)
    public void shouldSaveNewSeatSuccess() throws Exception {

        Seat newSeat = new Seat(17L, "ZZ", 3);

        String content = objectMapper.writeValueAsString(newSeat);

        MockHttpServletRequestBuilder mockRequest = post("/cinema/seat")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
            .andExpect(status().isCreated());

        seatRepository.deleteSeat(
            seatRepository.getAllSeats().get(seatRepository.getAllSeats().size() - 1).getId());

        seatRepository.deleteSeat(
            seatRepository.getAllSeats().get(seatRepository.getAllSeats().size() - 1).getId());

        seatRepository.deleteSeat(
            seatRepository.getAllSeats().get(seatRepository.getAllSeats().size() - 1).getId());
    }
}
