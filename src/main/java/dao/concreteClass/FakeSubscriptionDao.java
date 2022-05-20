package dao.concreteClass;

import dao.interfaceClass.SubscriptionDao;
import domainModel.Customer;
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
    public ArrayList<Subscription> getFromCustomer(Customer customer) {
        ArrayList<Subscription> subOfCustomer = new ArrayList<>();
        for(Subscription sub : this.subscriptions){
            if (sub.getMyCustomer().equals(customer))
                subOfCustomer.add(sub);
        }
        return subOfCustomer;
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
