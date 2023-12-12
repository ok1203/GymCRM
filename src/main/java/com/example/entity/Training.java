package com.example.entity;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.util.Date;

@Entity
@Table(name = "training")
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "trainee_id")
    private int traineeId;

    @Column(name = "trainer_id")
    private int trainerId;

    @Column(name = "name")
    @NonNull
    private String name;

    @Column(name = "training_type_id")
    private int trainingTypeId;

    @Column(name = "date")
    @NonNull
    private Date date;

    @Column(name = "duration")
    @NonNull
    private int duration;

    @ManyToOne
    @JoinColumn(name = "trainee_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Trainee trainee;

    @ManyToOne
    @JoinColumn(name = "trainer_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Trainer trainer;

    public Training( int traineeId, int trainerId, String name, int trainingTypeId, Date date, int duration) {
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
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Trainee getTrainee() {
        return trainee;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    @Override
    public String toString() {
        return "Training{" +
                "Id=" + id +
                ", traineeId=" + traineeId +
                ", trainerId=" + trainerId +
                ", name='" + name + '\'' +
                ", trainingTypeId=" + trainingTypeId +
                ", date=" + date +
                ", duration=" + duration +
                '}';
    }
}
