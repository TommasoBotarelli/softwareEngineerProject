package domainModel;

import java.time.LocalDate;

public class Subscription implements TypeOfAccess {

    private LocalDate emission;
    private LocalDate expiration;
    private TypeOfSub type;
    private Costumer myCostumer;

    private int nAccess;
    private long billID;


    public Subscription(LocalDate emission, TypeOfSub type, Costumer costumer, long billID){
        this.emission = emission;
        this.type = type;
        this.myCostumer = costumer;
        this.nAccess = 0;
        this.billID = billID;
    }

    @Override
    public boolean isValid(LocalDate actualDate) {
        boolean canAccess = this.type != TypeOfSub.PROVA || this.nAccess < 3;

        return (this.emission.isBefore(actualDate) || this.emission.isEqual(actualDate)) &&
                (this.expiration.isAfter(actualDate) || this.expiration.isEqual(actualDate)) && canAccess;
    }

    @Override
    public Costumer getCostumer() {
        return null;
    }

    @Override
    public LocalDate getEmission() {
        return null;
    }

    @Override
    public LocalDate getExpiration() {
        return null;
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

    public TypeOfSub getType() {
        return type;
    }

    public void setType(TypeOfSub type) {
        this.type = type;
    }
}
