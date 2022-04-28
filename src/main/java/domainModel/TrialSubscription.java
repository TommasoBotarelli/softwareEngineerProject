package domainModel;

import java.time.LocalDate;

public class TrialSubscription {
    private LocalDate emission;
    private LocalDate expiration;

    private Costumer costumerTarget;

    private int nAccess;

    public TrialSubscription(Costumer costumer, LocalDate date){
        this.costumerTarget = costumer;
        this.emission = date;
        this.expiration = date.plusDays(TypeOfSub.TRIAL.getnDay());
        this.nAccess = 0;
    }

    public LocalDate getEmission() {
        return emission;
    }

    public LocalDate getExpiration() {
        return expiration;
    }

    public int getnAccess() {
        return nAccess;
    }

    public Costumer getCostumerTarget() {
        return costumerTarget;
    }
}
