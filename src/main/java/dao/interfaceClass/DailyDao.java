package dao.interfaceClass;

import domainModel.Customer;
import domainModel.Daily;

import java.util.ArrayList;

public interface DailyDao {
    boolean addDaily(Daily daily);
    ArrayList<Daily> getAll();
    ArrayList<Daily> getFromCustomer(Customer customer);
    void deleteAll();
}
