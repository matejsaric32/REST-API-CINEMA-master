package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class Projection extends BaseEntity {

    private String name;
    private LocalDateTime dateTime;
    private Stage stage;

    public Projection(Long id, String name, LocalDateTime dateTime, Stage stage) {
        super(id);
        this.name = name;
        this.dateTime = dateTime;
        this.stage = stage;
    }
}
