package com.example.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SeatControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void shouldReturnAllSeatsFromFile() throws Exception {
        this.mockMvc.perform(get("/cinema/seat"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(16)));
    }

    @Test
    public void shouldReturnSeatById() throws Exception {

        final String SEAT_ID = "1";
        final String row = "A";
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
}
