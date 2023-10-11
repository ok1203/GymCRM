package com.example.model;

public class TrainingType {

    private int Id;
    private String typeName;

    public TrainingType(int id, String typeName) {
        Id = id;
        this.typeName = typeName;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "TrainingType{" +
                "Id=" + Id +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
