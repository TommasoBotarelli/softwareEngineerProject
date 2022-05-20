package dao.interfaceClass;

import domainModel.Customer;
import domainModel.TrialSubscription;

import java.util.ArrayList;

public interface TrialSubscriptionDao {
    boolean add(TrialSubscription sub);
    ArrayList<TrialSubscription> getAll();
    TrialSubscription getFromCustomer(Customer customer);
    void deleteAll();
}
