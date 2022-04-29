package dao.concreteClass;

import dao.interfaceClass.DailyDao;
import domainModel.Costumer;
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
    public ArrayList<Daily> getFromCostumer(Costumer costumer) {
        ArrayList<Daily> dailyOfCostumer = new ArrayList<>();
        for (Daily d : dailySub){
            if (d.getMyCostumer().equals(costumer))
                dailyOfCostumer.add(d);
        }
        return dailyOfCostumer;
    }

    @Override
    public void deleteAll() {
        dailySub.clear();
    }
}
