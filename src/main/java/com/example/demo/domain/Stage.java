package com.example.demo.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter

public class Stage extends BaseEntity {
    private String name;
    private List<Seat> seats;

    public Stage(Long id, String name, List<Seat> seats) {
        super(id);
        this.name = name;
        this.seats = seats;
    }

    public Stage(Long id) {
        super(id);
    }
}
