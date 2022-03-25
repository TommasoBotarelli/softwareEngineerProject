package domainModel;

import java.util.Calendar;

public class Daily implements TypeOfAccess {
    private boolean used;
    private Costumer myCostumer;

    Daily(){
        this.used = false;
    }


    @Override
    public boolean isValid(Calendar actualDate) {
        return !used;
    }


    @Override
    public void addAccess() {
        used = true;
    }

    @Override
    public Costumer getCostumer(){
        return myCostumer;
    }

    public void setCostumer(Costumer costumer) {
        this.myCostumer = costumer;
    }
}
