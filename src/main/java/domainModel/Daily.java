package domainModel;

import java.time.LocalDate;

public class Daily implements TypeOfAccess {
    private LocalDate emission;
    private LocalDate expiration;
    private Costumer myCostumer;

    private boolean canAccess;
    private long billID;

    public Daily(LocalDate emission, Costumer costumer){
        this.emission = emission;
        this.expiration = LocalDate.MAX;
        this.canAccess = true;
        this.myCostumer = costumer;
    }


    @Override
    public boolean isValid(LocalDate actualDate) {
        return (this.expiration.isAfter(actualDate) || this.expiration.isEqual(actualDate)) &&
                (this.emission.isBefore(actualDate) || this.emission.isEqual(actualDate)) && this.canAccess;
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
        this.canAccess = false;
    }

    @Override
    public long getBillID() {
        return this.billID;
    }

    @Override
    public void setBillID(long id) {
        this.billID = id;
    }
}
