package dao.concreteClass;

import dao.interfaceClass.TypeOfAccessDao;
import domainModel.*;

import java.util.ArrayList;

public class FakeTypeOfAccessDao implements TypeOfAccessDao {

    private ArrayList<TrialSubscription> trialSubscriptions;
    private ArrayList<Subscription> subscriptions;
    private ArrayList<Daily> dailies;

    private ArrayList<TypeOfAccess> typeOfAccesses;
    private static FakeTypeOfAccessDao instance = null;

    private FakeTypeOfAccessDao(){
        typeOfAccesses = new ArrayList<>();
        subscriptions = new ArrayList<>();
        dailies = new ArrayList<>();
    }

    public static FakeTypeOfAccessDao getInstance(){
        if (instance == null){
            instance = new FakeTypeOfAccessDao();
        }
        return instance;
    }

    @Override
    public void addWithBill(TypeOfAccess typeOfAccess, long id) {
        typeOfAccess.setBillID(id);
        typeOfAccesses.add(typeOfAccess);
    }

    @Override
    public void addSubscription(Subscription sub) {

    }

    @Override
    public void addDaily(Daily daily) {

    }

    @Override
    public void addTrialSubscription(TrialSubscription sub) {
        this.trialSubscriptions.add(sub);
    }

    @Override
    public void delete(TypeOfAccess typeOfAccess) {
        typeOfAccesses.remove(typeOfAccess);
    }

    @Override
    public ArrayList<TypeOfAccess> getAll() {
        return typeOfAccesses;
    }

    @Override
    public ArrayList<TypeOfAccess> getFromCostumer(Costumer costumer) {
        ArrayList<TypeOfAccess> returnArray = new ArrayList<>();

        for (TypeOfAccess typeOfAccess : typeOfAccesses){
            if (typeOfAccess.getCostumer().equals(costumer))
                returnArray.add(typeOfAccess);
        }

        return returnArray;
    }

    @Override
    public void deleteAllTypeOfAccessFromCostumer(Costumer costumer) {
        typeOfAccesses.removeIf(typeOfAccess -> typeOfAccess.getCostumer().equals(costumer));
    }

    @Override
    public void deleteAll() {
        typeOfAccesses.clear();
    }


}
