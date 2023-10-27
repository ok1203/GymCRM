package com.example.entity;

import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "training_type")
public class TrainingType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "type_name")
    @NonNull
    private String typeName;

    public TrainingType(String typeName) {
        this.typeName = typeName;
    }

    public TrainingType() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
                "Id=" + id +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
