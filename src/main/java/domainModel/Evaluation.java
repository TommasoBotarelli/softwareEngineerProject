package domainModel;

import java.time.LocalDate;

public class Evaluation {

    private final LocalDate date;
    private final Measurement measurement;
    private String comments;
    private int progressLevel;
    private Customer customer;
    private PersonalTrainer personalTrainer;


    public Evaluation(LocalDate date, Measurement measurement, Customer customer) {
        this.date = date;
        this.measurement = measurement;
        this.customer = customer;
        this.progressLevel = 0;
        this.comments = "";
    }

    public void setPersonalTrainer(PersonalTrainer personalTrainer){
        this.personalTrainer = personalTrainer;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setProgressLevel(int progressLevel) {
        this.progressLevel = progressLevel;
    }

    public LocalDate getDate() {
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object obj) {
        Evaluation otherEvaluation = (Evaluation) obj;
        return this.measurement.equals(otherEvaluation.getMeasurement()) &&
                this.date.isEqual(otherEvaluation.getDate()) &&
                this.customer.equals(otherEvaluation.getCustomer()) &&
                this.comments.equals(otherEvaluation.getComments()) &&
                this.progressLevel == otherEvaluation.getProgressLevel();
    }
}
