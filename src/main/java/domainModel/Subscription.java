package domainModel;

import java.time.LocalDate;

public class Subscription implements AccessType{

    private LocalDate emission;
    private LocalDate expiration;
    private TypeOfSub type;
    private Costumer myCostumer;
    private Bill bill;


    public Subscription(LocalDate emission, TypeOfSub type, Costumer costumer, Bill bill){
        this.emission = emission;
        this.type = type;
        this.myCostumer = costumer;
        this.expiration = emission.plusMonths(type.getnMonth());
        this.bill = bill;
    }

    public LocalDate getEmission() {
        return emission;
    }

    public LocalDate getExpiration() {
        return expiration;
    }

    public TypeOfSub getTypeOfSub() {
        return type;
    }

    public Costumer getMyCostumer() {
        return myCostumer;
    }

    public Bill getBill() {
        return bill;
    }

    @Override
    public boolean isValid(LocalDate date) {
        return date.isEqual(this.emission) || date.isEqual(this.expiration) ||
                date.isAfter(this.emission) && date.isBefore(this.expiration);
    }
}
