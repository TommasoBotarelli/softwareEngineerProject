package dao.interfaceClass;

import domainModel.Customer;
import domainModel.Subscription;

import java.util.ArrayList;

public interface SubscriptionDao {
    boolean add(Subscription sub);
    ArrayList<Subscription> getFromCustomer(Customer customer);
    ArrayList<Subscription> getAll();
    void deleteAll();
}
