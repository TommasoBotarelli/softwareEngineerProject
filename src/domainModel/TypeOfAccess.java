package domainModel;

import java.util.Calendar;

public interface TypeOfAccess {
    boolean isValid(Calendar actualDate);
    void addAccess();
    Costumer getCostumer();
}
