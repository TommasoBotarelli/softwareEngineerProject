package domainModel;

import java.time.LocalDate;

public class Daily implements TypeOfAccess {
    private LocalDate emission;
    private LocalDate expiration;
    private Costumer myCostumer;

    private boolean validity;
    private long billID;

    public Daily(LocalDate emission, Costumer costumer){
        this.emission = emission;
        this.expiration = LocalDate.MAX;
        this.validity = true;
        this.myCostumer = costumer;
    }

    @Override
    public Costumer getCostumer(){
        return myCostumer;
    }

    @Override
    public LocalDate getEmission() {
        return this.emission;
    }

    @Override
    public LocalDate getExpiration() {
        return this.expiration;
    }

    @Override
    public void setExpiration(LocalDate date) {
        this.expiration = date;
    }

    @Override
    public void setEmission(LocalDate date) {
        this.emission = date;
    }

    public void setCostumer(Costumer costumer) {
        this.myCostumer = costumer;
    }

    @Override
    public void addAccess() {
        this.validity = false;
    }

    @Override
    public long getBillID() {
        return this.billID;
    }

    @Override
    public void setBillID(long id) {
        this.billID = id;
    }

    public boolean getValidity(){
        return this.validity;
    }
}
