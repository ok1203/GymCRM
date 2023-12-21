package com.example.entity;

import com.example.RandomStringGenerator;
import jakarta.persistence.*;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "gym_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    @NonNull
    private String firstName;

    @Column(name = "last_name")
    @NonNull
    private String lastName;

    @Column(name = "user_name")
    @NonNull
    private String userName;

    @NonNull
    @Column(name = "password")
    private String password;

    @NonNull
    @Column(name = "is_active")
    private boolean isActive;

    //TODO rethink about connection
    @OneToOne(mappedBy = "gymUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Trainee trainee;

    @OneToOne(mappedBy = "gymUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Trainer trainer;

    public User(String firstName, String lastName, boolean isActive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = firstName + "." + lastName;
        this.password = RandomStringGenerator.generateRandomString(10);
        this.isActive = isActive;
    }

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "User{" +
                "Id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
