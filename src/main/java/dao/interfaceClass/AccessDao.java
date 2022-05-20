package dao.interfaceClass;

import domainModel.Access;
import domainModel.Customer;

import java.time.LocalDate;
import java.util.ArrayList;

public interface AccessDao {
    void add(Access access);
    void delete(Access access);
    ArrayList<Access> getAll();
    ArrayList<Access> getFromCustomer(Customer customer);
    ArrayList<Access> getFromDate(LocalDate date);
    void deleteAll();
}
