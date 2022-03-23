package dao;

import domainModel.Costumer;
import domainModel.TypeOfAccess;
import java.util.ArrayList;
import java.util.Calendar;

public interface TypeOfAccessDao {
    void add (TypeOfAccess typeOfAccess);
    void delete(TypeOfAccess typeOfAccess);
    ArrayList<TypeOfAccess> getAll();
    ArrayList<TypeOfAccess> getFromCostumer(Costumer costumer);
    ArrayList<TypeOfAccess> getFromDate(Calendar date);
}
