package com.example.demo.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
public class Seat extends BaseEntity {
    private String rowName;
    private Integer positionInRow;

    public Seat(Long id, String rowName, Integer positionInRow) {
        super(id);
        this.rowName = rowName;
        this.positionInRow = positionInRow;
    }
}
