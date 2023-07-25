package com.example.demo.domain;

public enum CITY {

    ZAGREB("ZAGREB"), SPLIT("SPLIT"),
    RIJEKA("RIJEKA"), OSIJEK("OSIJEK"),
    VARAZDIN("VARAŽDIN"), CAKOVEC("ČAKOVEC");

    private final String cityName;

    CITY(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return this.cityName;
    }
}
