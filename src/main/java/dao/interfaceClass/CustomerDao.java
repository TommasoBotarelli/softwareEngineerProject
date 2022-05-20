package dao.interfaceClass;

import domainModel.Customer;

import java.util.ArrayList;

public interface CustomerDao {
    void add(Customer customer);
    boolean delete(Customer customer);
    ArrayList<Customer> getAll();
    ArrayList<Customer> getFromNameSurname(String name, String surname);
    Customer getSelectedCustomer(String name, String surname, String mobilePhone);
    void deleteAll();
}
