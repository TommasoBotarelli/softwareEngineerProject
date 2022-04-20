package domainModel;

import java.time.LocalDate;

public class TrainingCard {

    private String exercises;
    private int level;
    private LocalDate emission;
    private LocalDate expiration;
    private PersonalTrainer personalTrainer;
    private Costumer costumer;
    private boolean standard;


    public TrainingCard(String exercises, int level, LocalDate emission, LocalDate expiration, boolean standard, PersonalTrainer personalTrainer){
        this.exercises = exercises;
        this.level = level;
        this.emission = emission;
        this.expiration = expiration;
        this.personalTrainer = personalTrainer;
        this.standard = standard;
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
}
