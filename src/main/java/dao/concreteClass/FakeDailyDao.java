package dao.concreteClass;

import dao.interfaceClass.DailyDao;
import domainModel.Customer;
import domainModel.Daily;

import java.util.ArrayList;

public class FakeDailyDao implements DailyDao {

    private ArrayList<Daily> dailySub;
    private static FakeDailyDao instance = null;

    private FakeDailyDao(){
        dailySub = new ArrayList<>();
    }

    public static FakeDailyDao getInstance(){
        if (instance == null)
            instance = new FakeDailyDao();
        return instance;
    }

    @Override
    public boolean addDaily(Daily daily) {
        if (!dailySub.contains(daily)){
            dailySub.add(daily);
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<Daily> getAll() {
        return this.dailySub;
    }

    @Override
    public ArrayList<Daily> getFromCustomer(Customer customer) {
        ArrayList<Daily> dailyOfCustomer = new ArrayList<>();
        for (Daily d : dailySub){
            if (d.getMyCustomer().equals(customer))
                dailyOfCustomer.add(d);
        }
        return dailyOfCustomer;
    }

    @Override
    public void deleteAll() {
        dailySub.clear();
    }
}
