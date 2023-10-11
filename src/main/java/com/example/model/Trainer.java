package com.example.model;

public class Trainer {

    private int Id;
    private int specializationId;
    private int userId;

    public Trainer(int id, int specializationId, int userId) {
        Id = id;
        this.specializationId = specializationId;
        this.userId = userId;
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
