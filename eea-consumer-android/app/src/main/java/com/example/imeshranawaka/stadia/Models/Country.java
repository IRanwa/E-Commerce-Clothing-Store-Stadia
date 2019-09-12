package com.example.imeshranawaka.stadia.Models;

import java.io.Serializable;

public class Country{
    private int countryId;
    private String name;

    public Country(){

    }

    public Country(int countryId, String name) {
        this.countryId = countryId;
        this.name = name;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCountryId() {
        return countryId;
    }

    public String getName() {
        return name;
    }
}
