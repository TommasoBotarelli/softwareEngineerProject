package domainModel;

import java.util.Calendar;

public class Daily implements TypeOfAccess {
    private boolean used;

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
}
