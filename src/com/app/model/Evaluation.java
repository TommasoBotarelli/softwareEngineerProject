package com.app.model;

import java.util.Calendar;

public class Evaluation {

    private final Calendar date;
    private final Measurement measurement;
    private String comments;
    private int progressLevel;


    public Evaluation(Calendar date, Measurement measurement) {
        this.date = date;
        this.measurement = measurement;
    }


    public void setComments(String comments) {
        this.comments = comments;
    }


    public void setProgressLevel(int progressLevel) {
        this.progressLevel = progressLevel;
    }

    public Calendar getDate() {
        return date;
    }

    public Measurement getMeasurement() {
        return measurement;
    }

    public String getComments() {
        return comments;
    }

    public int getProgressLevel() {
        return progressLevel;
    }
}
