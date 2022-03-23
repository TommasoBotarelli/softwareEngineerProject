package dao;

import domainModel.Costumer;

import java.util.ArrayList;

public interface CostumerDao {
    void add(Costumer costumer);
    void delete(Costumer costumer);
    ArrayList<Costumer> getAll();
    Costumer getFromName(String name, String surname);
}
