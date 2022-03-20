package com.app.model;

public class TrainingCard {
    private String exercises;
    private int level;
    private int emission;   //FIXME correggere con classe Date quando sarà creata
    private int expiration;
    private PersonalTrainer personalTrainer;

    TrainingCard(String exercises, int level, int emission, int expiration, PersonalTrainer personalTrainer){
        this.exercises = exercises;
        this.level = level;
        this.emission = emission;
        this.expiration = expiration;
        this.personalTrainer = personalTrainer;
    }

    public PersonalTrainer getPersonalTrainer() {
        return personalTrainer;
    }
}
