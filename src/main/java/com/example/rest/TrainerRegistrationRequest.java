package com.example.rest;

public class TrainerRegistrationRequest {

    private String firstName;
    private String lastName;
    private int specializationId;

    public TrainerRegistrationRequest() {
    }

    public TrainerRegistrationRequest(String firstName, String lastName, int specializationId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.specializationId = specializationId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getSpecializationId() {
        return specializationId;
    }

    public void setSpecializationId(int specializationId) {
        this.specializationId = specializationId;
    }
}

