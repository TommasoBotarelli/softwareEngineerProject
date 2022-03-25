package domainModel;

import java.util.Calendar;

public class Subscription implements TypeOfAccess {

    private Calendar emission;
    private Calendar expiration;
    private final TypeOfSub type;
    private int nAccess;
    private Costumer myCostumer;


    Subscription(Calendar emission, TypeOfSub type){
        this.type = type;
        this.emission = emission;
        this.expiration = Calendar.getInstance();
        expiration.setLenient(true);

        if (type == TypeOfSub.PROVA)
            expiration.set(Calendar.DAY_OF_MONTH, emission.get(Calendar.DAY_OF_MONTH) + type.nDay);
        else
            expiration.set(Calendar.MONTH, emission.get(Calendar.MONTH) + type.nMonth);
    }


    private boolean isExpired(Calendar actualDate){
        return this.expiration.get(Calendar.MONTH) < actualDate.get(Calendar.MONTH) ||
                (this.expiration.get(Calendar.MONTH) == actualDate.get(Calendar.MONTH) &&
                        this.expiration.get(Calendar.DAY_OF_MONTH) < actualDate.get(Calendar.DAY_OF_MONTH));
    }


    @Override
    public boolean isValid(Calendar actualDate) {
        boolean isValid = !isExpired(actualDate);

        if(this.type == TypeOfSub.PROVA)
            isValid = isValid && this.nAccess < 3;

        return isValid;
    }


    @Override
    public void addAccess() {
        nAccess++;
    }


    @Override
    public Costumer getCostumer() {
        return myCostumer;
    }

    public void setCostumer(Costumer costumer) {
        this.myCostumer = costumer;
    }
}
