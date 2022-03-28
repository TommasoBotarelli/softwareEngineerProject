package dao;

import domainModel.Bill;

import java.time.LocalDate;
import java.util.ArrayList;

public interface BillDao {
    long add(Bill bill);
    ArrayList<Bill> getAll();
    ArrayList<Bill> getFromDate(LocalDate date);
    Bill getFromID(long id);
}
