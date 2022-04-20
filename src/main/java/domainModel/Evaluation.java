package domainModel;

import java.time.LocalDate;

public class Evaluation {

    private final LocalDate date;
    private final Measurement measurement;
    private String comments;
    private int progressLevel;
    private Costumer costumer;


    public Evaluation(LocalDate date, Measurement measurement, Costumer costumer) {
        this.date = date;
        this.measurement = measurement;
        this.costumer = costumer;
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

    public Costumer getCostumer() {
        return costumer;
    }

    public void setCostumer(Costumer costumer) {
        this.costumer = costumer;
    }
}
