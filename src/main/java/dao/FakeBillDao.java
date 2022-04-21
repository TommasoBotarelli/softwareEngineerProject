package dao;

import domainModel.Bill;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class FakeBillDao implements BillDao{

    ArrayList<Bill> bills;
    long counter;
    private static FakeBillDao instance = null;

    private FakeBillDao(){
        bills = new ArrayList<>();
        this.counter = 0;
    }

    public static FakeBillDao getInstance(){
        if (instance == null){
            instance = new FakeBillDao();
        }
        return instance;
    }

    @Override
    public long add(Bill bill) {
        bills.add(bill);
        bill.setId(counter);
        return counter++;
    }

    @Override
    public ArrayList<Bill> getAll() {
        return bills;
    }

    @Override
    public ArrayList<Bill> getFromDate(LocalDate date) {
        ArrayList<Bill> billOfDay = new ArrayList<>();

        for (Bill bill : bills){
            if(bill.getDate().isEqual(date))
                billOfDay.add(bill);
        }

        return billOfDay;
    }

    @Override
    public Bill getFromID(long id) throws Exception{
        if (bills.stream().anyMatch(bill -> bill.getId() == id)){
            return bills.stream().filter(bill -> bill.getId() == id).collect(Collectors.toList()).get(0);
        }
        else
            throw new Exception("A bill with this ID doesn't exist");
    }

    @Override
    public void deleteAll() {
        bills.clear();
    }
}
