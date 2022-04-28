package dao.interfaceClass;

import domainModel.Costumer;
import domainModel.Subscription;

import java.util.ArrayList;

public interface SubscriptionDao {
    boolean add(Subscription sub);
    ArrayList<Subscription> getFromCostumer(Costumer costumer);
    ArrayList<Subscription> getAll();
}
