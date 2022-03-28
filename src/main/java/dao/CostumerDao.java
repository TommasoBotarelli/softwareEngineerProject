package dao;

import domainModel.Costumer;

import java.util.ArrayList;

public interface CostumerDao {
    void add(Costumer costumer);
    void delete(Costumer costumer);
    ArrayList<Costumer> getAll();
    ArrayList<Costumer> getFromNameSurname(String name, String surname);
    Costumer getSelectedCostumer(String name, String surname, String mobilePhone);
}
