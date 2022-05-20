package dao.concreteClass;

import dao.interfaceClass.TrialSubscriptionDao;
import domainModel.Customer;
import domainModel.TrialSubscription;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class FakeTrialSubscriptionDao implements TrialSubscriptionDao {

    private ArrayList<TrialSubscription> trialSubscriptions;
    private static FakeTrialSubscriptionDao instance = null;

    private FakeTrialSubscriptionDao(){
        trialSubscriptions = new ArrayList<>();
    }

    public static FakeTrialSubscriptionDao getInstance(){
        if (instance == null)
            instance = new FakeTrialSubscriptionDao();
        return instance;
    }

    @Override
    public boolean add(TrialSubscription sub) {
        if (!trialSubscriptions.contains(sub)){
            trialSubscriptions.add(sub);
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<TrialSubscription> getAll() {
        return trialSubscriptions;
    }

    @Override
    public TrialSubscription getFromCustomer(Customer customer) {
        if (trialSubscriptions.stream().anyMatch(trialSubscription -> trialSubscription.getCustomerTarget().equals(customer))){
            return trialSubscriptions.stream().filter(trialSubscription -> trialSubscription.getCustomerTarget().equals(customer)).collect(Collectors.toList()).get(0);
        }
        return null;
    }

    @Override
    public void deleteAll() {
        trialSubscriptions.clear();
    }
}
