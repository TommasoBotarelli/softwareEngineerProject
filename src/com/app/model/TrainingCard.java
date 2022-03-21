package com.app.model;

import java.util.Calendar;

public class TrainingCard {

    private String exercises;
    private int level;
    private Calendar emission;
    private Calendar expiration;
    private PersonalTrainer personalTrainer;


    TrainingCard(String exercises, int level, Calendar emission, Calendar expiration, PersonalTrainer personalTrainer){
        this.exercises = exercises;
        this.level = level;
        this.emission = emission;
        this.expiration = expiration;
        this.personalTrainer = personalTrainer;
    }


    TrainingCard(TrainingCard tCard){
        this.exercises = tCard.exercises;
        this.level = tCard.level;
        this.emission = tCard.emission;
        this.expiration = tCard.expiration;
        this.personalTrainer = tCard.personalTrainer;
    }


    public void setEmission(Calendar emission) {
        this.emission = emission;
    }


    public void setExpiration(Calendar expiration) {
        this.expiration = expiration;
    }


    public PersonalTrainer getPersonalTrainer() {
        return personalTrainer;
    }
}
