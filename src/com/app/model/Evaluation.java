package com.app.model;

import java.util.ArrayList;

public class Evaluation {

    private int date;   //FIXME creare classe Date
    private Measurement measurement;
    private String comments;
    private int progressLevel;


    public Evaluation(int date, Measurement measurement) {
        this.date = date;
        this.measurement = measurement;
    }


    public void setComments(String comments) {
        this.comments = comments;
    }


    public void setProgressLevel(int progressLevel) {
        this.progressLevel = progressLevel;
    }

    //TODO ricominciare da qua
}
