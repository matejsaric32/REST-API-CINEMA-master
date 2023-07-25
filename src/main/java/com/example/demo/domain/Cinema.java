package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Cinema extends BaseEntity {

    private String name;
    private Address address;
    private List<Projection> projectionList;
    private List<Stage> stageList;

    public Cinema(Long id, String name, Address address, List<Projection> projectionList, List<Stage> stageList) {
        super(id);
        this.name = name;
        this.address = address;
        this.projectionList = projectionList;
        this.stageList = stageList;
    }
}
