package domainModel;

import java.time.LocalDate;

public class TrialSubscription implements AccessType{
    private LocalDate emission;
    private LocalDate expiration;

    private Customer customerTarget;

    private int nAccess;

    public TrialSubscription(Customer customer, LocalDate date){
        this.customerTarget = customer;
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

    public Customer getCustomerTarget() {
        return customerTarget;
    }

    public void addAccess(){
        this.nAccess++;
    }

    @Override
    public boolean isValid(LocalDate date){
        return (date.isEqual(this.emission) || date.isEqual(this.expiration) ||
                date.isAfter(this.emission) && date.isBefore(this.expiration)) &&
                this.nAccess < 3;
    }
}
