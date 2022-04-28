package domainModel;

import java.time.LocalDate;

public class Daily{
    private LocalDate emission;
    private Costumer myCostumer;
    private boolean validity;
    private Bill bill;

    public Daily(LocalDate emission, Costumer costumer, Bill bill){
        this.emission = emission;
        this.validity = true;
        this.myCostumer = costumer;
        this.bill = bill;
    }

    public LocalDate getEmission() {
        return emission;
    }

    public Costumer getMyCostumer() {
        return myCostumer;
    }

    public boolean isValidity() {
        return validity;
    }

    public Bill getBill() {
        return bill;
    }
}
