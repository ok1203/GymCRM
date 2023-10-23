package com.example.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

@Entity
@Table(name = "trainee")
public class Trainee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int Id;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "address")
    private String address;

    @Column(name = "user_id")
    private int userId;

    @OneToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User gym_user;

    @ManyToMany(mappedBy = "trainees")
    private Map<Integer, Trainer> trainers;

    public Trainee( Date dateOfBirth, String address, int userId) {
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.userId = userId;
    }

    public Trainee() {

    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Trainee{" +
                "Id=" + Id +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                ", userId=" + userId +
                '}';
    }

    public User getGym_user() {
        return gym_user;
    }
}
