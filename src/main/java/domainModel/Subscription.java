package domainModel;

import java.time.LocalDate;

public class Subscription implements TypeOfAccess {

    private LocalDate emission;
    private LocalDate expiration;
    private TypeOfSub type;
    private Costumer myCostumer;

    private int nAccess;
    private long billID;


    public Subscription(LocalDate emission, TypeOfSub type, Costumer costumer){
        this.emission = emission;
        this.type = type;
        this.myCostumer = costumer;
        this.nAccess = 0;
    }

    @Override
    public Costumer getCostumer() {
        return this.myCostumer;
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

    @Override
    public void setCostumer(Costumer costumer) {
        this.myCostumer = costumer;
    }

    @Override
    public void addAccess() {
        this.nAccess++;
    }

    @Override
    public long getBillID() {
        return this.billID;
    }

    public void setBillID(long billID) {
        this.billID = billID;
    }

    public TypeOfSub getType() {
        return type;
    }

    public void setType(TypeOfSub type) {
        this.type = type;
    }
}
