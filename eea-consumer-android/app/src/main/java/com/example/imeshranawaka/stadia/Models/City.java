package com.example.imeshranawaka.stadia.Models;


import java.io.Serializable;

public class City {
    private int cityId;
    private int stateId;
    private String name;

    public City(){

    }

    public City(int cityId, int stateId, String name) {
        this.cityId = cityId;
        this.stateId = stateId;
        this.name = name;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCityId() {
        return cityId;
    }

    public String getName() {
        return name;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }
}
