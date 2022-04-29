package domainModel;

import java.time.LocalDate;

public class TrainingCard {

    private String exercises;
    private String name;
    private int level;
    private LocalDate emission;
    private LocalDate expiration;
    private PersonalTrainer personalTrainer;
    private Costumer costumer;
    private boolean standard;


    public TrainingCard(String exercises, int level, boolean standard, PersonalTrainer personalTrainer){
        this.name = "TrainingCard";
        this.exercises = exercises;
        this.level = level;
        this.personalTrainer = personalTrainer;
        this.standard = standard;
        this.emission = LocalDate.MIN;
        this.expiration = LocalDate.MAX;
        this.costumer = null;
    }

    public TrainingCard(TrainingCard trainingCard){
        this.name = trainingCard.name;
        this.exercises = trainingCard.exercises;
        this.level = trainingCard.level;
        this.personalTrainer = trainingCard.personalTrainer;
        this.standard = trainingCard.standard;
        this.emission = trainingCard.emission;
        this.expiration = trainingCard.expiration;
        this.costumer = null;
    }

    public String getExercises() {
        return exercises;
    }

    public Costumer getCostumer() {
        return costumer;
    }

    public void setCostumer(Costumer costumer) {
        this.costumer = costumer;
    }

    public void setEmission(LocalDate emission) {
        this.emission = emission;
    }

    public void setExpiration(LocalDate expiration) {
        this.expiration = expiration;
    }

    public PersonalTrainer getPersonalTrainer() {
        return personalTrainer;
    }

    public LocalDate getEmission() {
        return emission;
    }

    public LocalDate getExpiration() {
        return expiration;
    }

    public boolean isStandard() {
        return standard;
    }

    public void setExercises(String exercises){
        this.exercises = exercises;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getLevel() {
        return level;
    }
}
