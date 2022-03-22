package dao;

import domainModel.Bill;

import java.util.ArrayList;
import java.util.Calendar;

public interface BillDao {
    void add(Bill bill);
    ArrayList<Bill> getAll();
    ArrayList<Bill> getFromDate(Calendar date);
}
