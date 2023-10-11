package com.example.model;

import java.util.Date;

public class Training {

    private int Id;
    private int traineeId;
    private int trainerId;
    private String name;
    private int trainingTypeId;
    private Date date;
    private int duration;

    public Training(int id, int traineeId, int trainerId, String name, int trainingTypeId, Date date, int duration) {
        Id = id;
        this.traineeId = traineeId;
        this.trainerId = trainerId;
        this.name = name;
        this.trainingTypeId = trainingTypeId;
        this.date = date;
        this.duration = duration;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getTraineeId() {
        return traineeId;
    }

    public void setTraineeId(int traineeId) {
        this.traineeId = traineeId;
    }

    public int getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTrainingTypeId() {
        return trainingTypeId;
    }

    public void setTrainingTypeId(int trainingTypeId) {
        this.trainingTypeId = trainingTypeId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Training{" +
                "Id=" + Id +
                ", traineeId=" + traineeId +
                ", trainerId=" + trainerId +
                ", name='" + name + '\'' +
                ", trainingTypeId=" + trainingTypeId +
                ", date=" + date +
                ", duration=" + duration +
                '}';
    }
}
