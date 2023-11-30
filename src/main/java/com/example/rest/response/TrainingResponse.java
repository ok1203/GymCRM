package com.example.rest.response;

import com.example.entity.Training;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class TrainingResponse {

    private String name;
    private Date date;
    private String type;
    private int duration;
    private String subName;

    public TrainingResponse(String name, Date date, String type, int duration, String subName) {
        this.name = name;
        this.date = date;
        this.type = type;
        this.duration = duration;
        this.subName = subName;
    }

    public TrainingResponse(Training training, String type, String subName) {
        this.name = training.getName();
        this.date = training.getDate();
        this.type = type;
        this.duration = training.getDuration();
        this.subName = subName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }
}
