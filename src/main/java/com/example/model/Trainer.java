package com.example.model;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "trainer")
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int Id;

    @Column(name = "specialization_id")
    private int specializationId;

    @Column(name = "user_id")
    private int userId;

    @OneToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false  )
    private User _user;

    @ManyToMany
    @JoinTable(
            name = "trainer_trainee",
            joinColumns = @JoinColumn(name = "trainer_id"),
            inverseJoinColumns = @JoinColumn(name = "trainee_id")
    )
    private Map<Integer, Trainee> trainees;

    public Trainer(int id, int specializationId, int userId) {
        Id = id;
        this.specializationId = specializationId;
        this.userId = userId;
    }

    public Trainer() {

    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getSpecializationId() {
        return specializationId;
    }

    public void setSpecializationId(int specializationId) {
        this.specializationId = specializationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "Id=" + Id +
                ", specializationId=" + specializationId +
                ", userId=" + userId +
                '}';
    }
}
