package com.example.model;

import javax.persistence.*;

@Entity
@Table(name = "training_type")
public class TrainingType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int Id;

    @Column(name = "type_name")
    private String typeName;

    public TrainingType(int id, String typeName) {
        Id = id;
        this.typeName = typeName;
    }

    public TrainingType() {

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
