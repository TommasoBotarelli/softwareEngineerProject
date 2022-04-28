package dao.interfaceClass;

import domainModel.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public interface TypeOfAccessDao {
    void addWithBill(TypeOfAccess typeOfAccess, long id);
    void addSubscription(Subscription sub);
    void addDaily(Daily daily);
    void addTrialSubscription(TrialSubscription sub);
    void delete(TypeOfAccess typeOfAccess);
    ArrayList<TypeOfAccess> getAll();
    ArrayList<TypeOfAccess> getFromCostumer(Costumer costumer);
    void deleteAllTypeOfAccessFromCostumer(Costumer costumer);
    void deleteAll();
}
