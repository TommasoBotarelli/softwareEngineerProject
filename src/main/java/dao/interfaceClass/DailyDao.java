package dao.interfaceClass;

import domainModel.Costumer;
import domainModel.Daily;

import java.util.ArrayList;

public interface DailyDao {
    boolean addDaily(Daily daily);
    ArrayList<Daily> getAll();
    ArrayList<Daily> getFromCostumer(Costumer costumer);
    void deleteAll();
}
