package dao.concreteClass;

import dao.interfaceClass.SubscriptionDao;
import domainModel.Costumer;
import domainModel.Subscription;

import java.util.ArrayList;

public class FakeSubscriptionDao implements SubscriptionDao {

    private ArrayList<Subscription> subscriptions;
    private static FakeSubscriptionDao instance = null;

    private FakeSubscriptionDao(){
        this.subscriptions = new ArrayList<>();
    }

    public static FakeSubscriptionDao getInstance(){
        if (instance == null)
            instance = new FakeSubscriptionDao();
        return instance;
    }

    @Override
    public boolean add(Subscription sub) {
        if (!subscriptions.contains(sub)){
            subscriptions.add(sub);
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<Subscription> getFromCostumer(Costumer costumer) {
        ArrayList<Subscription> subOfCostumer = new ArrayList<>();
        for(Subscription sub : this.subscriptions){
            if (sub.getMyCostumer().equals(costumer))
                subOfCostumer.add(sub);
        }
        return subOfCostumer;
    }

    @Override
    public ArrayList<Subscription> getAll() {
        return this.subscriptions;
    }

    @Override
    public void deleteAll() {
        subscriptions.clear();
    }
}
