package dao.concreteClass;

import dao.interfaceClass.TrialSubscriptionDao;
import domainModel.Costumer;
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
    public TrialSubscription getFromCostumer(Costumer costumer) {
        if (trialSubscriptions.stream().anyMatch(trialSubscription -> trialSubscription.getCostumerTarget().equals(costumer))){
            return trialSubscriptions.stream().filter(trialSubscription -> trialSubscription.getCostumerTarget().equals(costumer)).collect(Collectors.toList()).get(0);
        }
        return null;
    }
}
