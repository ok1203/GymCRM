package com.example.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "training")
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int Id;

    @Column(name = "trainee_id")
    private int traineeId;

    @Column(name = "trainer_id")
    private int trainerId;

    @Column(name = "name")
    private String name;

    @Column(name = "training_type_id")
    private int trainingTypeId;

    @Column(name = "date")
    private Date date;

    @Column(name = "duration")
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

    public Training() {

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
