package dao.interfaceClass;

import domainModel.Costumer;
import domainModel.TrialSubscription;

import java.util.ArrayList;

public interface TrialSubscriptionDao {
    boolean add(TrialSubscription sub);
    ArrayList<TrialSubscription> getAll();
    TrialSubscription getFromCostumer(Costumer costumer);
    void deleteAll();
}
