package com.example.rest.response;

import com.example.entity.Trainee;
import com.example.entity.Trainer;

import java.util.Date;
import java.util.List;

public class TraineeProfileResponse {

    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String address;
    private boolean isActive;
    private List<Trainer> trainers;

    public TraineeProfileResponse() {
    }

    public TraineeProfileResponse(String firstName, String lastName, Date dateOfBirth, String address, boolean isActive, List<Trainer> trainers) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.isActive = isActive;
        this.trainers = trainers;
    }

    public TraineeProfileResponse(Trainee trainee) {
        this.firstName = trainee.getGym_user().getFirstName();
        this.lastName = trainee.getGym_user().getLastName();
        this.dateOfBirth = trainee.getDateOfBirth();
        this.address = trainee.getAddress();
        this.isActive = trainee.getGym_user().isActive();
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<Trainer> getTrainers() {
        return trainers;
    }

    public void setTrainers(List<Trainer> trainers) {
        this.trainers = trainers;
    }
}

