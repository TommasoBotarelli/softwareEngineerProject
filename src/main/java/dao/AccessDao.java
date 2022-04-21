package dao;

import domainModel.Access;
import domainModel.Costumer;

import java.time.LocalDate;
import java.util.ArrayList;

public interface AccessDao {
    void add(Access access);
    void delete(Access access);
    ArrayList<Access> getAll();
    ArrayList<Access> getFromCostumer(Costumer costumer);
    ArrayList<Access> getFromDate(LocalDate date);
    void deleteAll();
}
