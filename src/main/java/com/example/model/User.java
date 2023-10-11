package com.example.model;

import com.example.RandomStringGenerator;

public class User {

    private int Id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private boolean isActive;

    public User(int id, String firstName, String lastName, boolean isActive) {
        Id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = firstName + "." + lastName;
        this.password = RandomStringGenerator.generateRandomString(10);
        this.isActive = isActive;
    }

    public User(int id, String firstName, String lastName, String userName, String password, boolean isActive) {
        Id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.isActive = isActive;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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
                "Id=" + Id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
