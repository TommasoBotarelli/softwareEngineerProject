package domainModel;

import java.time.LocalDate;

public class Daily implements AccessType{
    private LocalDate emission;
    private Customer myCustomer;
    private boolean used;
    private Bill bill;

    public Daily(LocalDate emission, Customer customer, Bill bill){
        this.emission = emission;
        this.used = false;
        this.myCustomer = customer;
        this.bill = bill;
    }

    public LocalDate getEmission() {
        return emission;
    }

    public Customer getMyCustomer() {
        return myCustomer;
    }

    public boolean isUsed() {
        return used;
    }

    public Bill getBill() {
        return bill;
    }

    public void addAccess(){
        this.used = true;
    }

    @Override
    public boolean isValid(LocalDate date) {
        return date.isEqual(this.emission) && !this.used;
    }
}
