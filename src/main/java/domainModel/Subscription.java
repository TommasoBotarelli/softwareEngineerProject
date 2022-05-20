package domainModel;

import java.time.LocalDate;

public class Subscription implements AccessType{

    private LocalDate emission;
    private LocalDate expiration;
    private TypeOfSub type;
    private Customer myCustomer;
    private Bill bill;


    public Subscription(LocalDate emission, TypeOfSub type, Customer customer, Bill bill){
        this.emission = emission;
        this.type = type;
        this.myCustomer = customer;
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

    public Customer getMyCustomer() {
        return myCustomer;
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
