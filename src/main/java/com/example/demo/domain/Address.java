package com.example.demo.domain;


import lombok.Data;

@Data
public class Address {

    private String street;
    private String houseNumber;
    private Integer postalCode;
    private CITY city;

    public Address(String street, String houseNumber, Integer postalCode, CITY city) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.city = city;
    }
}
