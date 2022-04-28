package domainModel;

import java.time.LocalDate;

public class Daily implements AccessType{
    private LocalDate emission;
    private Costumer myCostumer;
    private boolean used;
    private Bill bill;

    public Daily(LocalDate emission, Costumer costumer, Bill bill){
        this.emission = emission;
        this.used = false;
        this.myCostumer = costumer;
        this.bill = bill;
    }

    public LocalDate getEmission() {
        return emission;
    }

    public Costumer getMyCostumer() {
        return myCostumer;
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
