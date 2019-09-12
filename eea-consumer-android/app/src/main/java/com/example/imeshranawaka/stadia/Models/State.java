package com.example.imeshranawaka.stadia.Models;


import java.io.Serializable;

public class State{
    private int stateId;
    private int countryId;
    private String name;

    public State(){

    }

    public State(int stateId, int countryId, String name) {
        this.stateId = stateId;
        this.countryId = countryId;
        this.name = name;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStateId() {
        return stateId;
    }

    public String getName() {
        return name;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
}
