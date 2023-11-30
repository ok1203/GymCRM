package com.example.rest.response;

import com.example.entity.Trainee;
import com.example.entity.Trainer;

import java.util.List;

public class TrainerProfileResponse {

    private String username;
    private String firstName;
    private String lastName;
    private int specializationId;
    private boolean isActive;
    private List<Trainee> trainees;

    public TrainerProfileResponse(String username, String firstName, String lastName, int specializationId, boolean isActive, List<Trainee> trainees) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specializationId = specializationId;
        this.isActive = isActive;
        this.trainees = trainees;
    }

    public TrainerProfileResponse(Trainer trainer) {
        this.firstName = trainer.getGym_user().getFirstName();
        this.lastName = trainer.getGym_user().getLastName();
        this.isActive = trainer.getGym_user().isActive();
    }

    // Getters and setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<Trainee> getTrainees() {
        return trainees;
    }

    public void setTrainees(List<Trainee> trainees) {
        this.trainees = trainees;
    }
}

