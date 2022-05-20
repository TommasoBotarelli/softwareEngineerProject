package dao.concreteClass;

import dao.interfaceClass.CustomerDao;
import domainModel.Customer;

import java.util.ArrayList;

public class FakeCustomerDao implements CustomerDao {

    private ArrayList<Customer> customers;
    private static FakeCustomerDao instance = null;

    public static FakeCustomerDao getInstance(){
        if (instance == null){
            instance = new FakeCustomerDao();
        }
        return instance;
    }

    private FakeCustomerDao(){
        customers = new ArrayList<>();
    }

    @Override
    public void add(Customer customer) {
        if (!customers.contains(customer)){
            customers.add(customer);
        }
    }

    @Override
    public boolean delete(Customer customer) {
        if (customers.contains(customer)){
            customers.remove(customer);
            return true;
        }
        else
            return false;
    }

    @Override
    public ArrayList<Customer> getAll() {
        return customers;
    }

    @Override
    public ArrayList<Customer> getFromNameSurname(String name, String surname) {
        ArrayList<Customer> returnArray = new ArrayList<>();

        for (Customer customer : customers){
            if(customer.getName().equals(name) && customer.getSurname().equals(surname))
                returnArray.add(customer);
        }

        return returnArray;
    }

    @Override
    public Customer getSelectedCustomer(String name, String surname, String phoneNumber) {
        Customer returnCustomer = null;
        for (Customer c : customers){
            if (c.getName().equals(name) && c.getSurname().equals(surname) && c.getPhoneNumber().equals(phoneNumber))
                returnCustomer = c;
        }
        return returnCustomer;
    }

    @Override
    public void deleteAll() {
        customers.clear();
    }
}
