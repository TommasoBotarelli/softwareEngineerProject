package domainModel;

import java.time.LocalDate;
import java.util.Objects;

public class TrainingCard {

    private String exercises;
    private String name;
    private int level;
    private LocalDate emission;
    private LocalDate expiration;
    private PersonalTrainer personalTrainer;
    private Customer customer;
    private boolean standard;


    public TrainingCard(String exercises, int level, boolean standard, PersonalTrainer personalTrainer){
        this.name = "TrainingCard";
        this.exercises = exercises;
        this.level = level;
        this.personalTrainer = personalTrainer;
        this.standard = standard;
        this.emission = LocalDate.MIN;
        this.expiration = LocalDate.MAX;
        this.customer = null;
    }

    public TrainingCard(TrainingCard trainingCard){
        this.name = trainingCard.name;
        this.exercises = trainingCard.exercises;
        this.level = trainingCard.level;
        this.personalTrainer = trainingCard.personalTrainer;
        this.standard = trainingCard.standard;
        this.emission = trainingCard.emission;
        this.expiration = trainingCard.expiration;
        this.customer = null;
    }

    public void setStandard(boolean standard) {
        this.standard = standard;
    }

    public String getExercises() {
        return exercises;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        TrainingCard trainingCard = (TrainingCard) obj;

        return Objects.equals(this.exercises, trainingCard.exercises) &&
                Objects.equals(this.name, trainingCard.name) &&
                this.level == trainingCard.level &&
                this.emission.isEqual(trainingCard.emission) &&
                this.expiration.isEqual(trainingCard.expiration) &&
                this.personalTrainer.equals(trainingCard.personalTrainer) &&
                this.customer.equals(trainingCard.customer) &&
                this.standard == trainingCard.standard;
    }
}
