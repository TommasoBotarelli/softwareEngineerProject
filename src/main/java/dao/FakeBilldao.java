package dao;

import domainModel.Bill;
import java.time.LocalDate;
import java.util.ArrayList;

public class FakeBilldao implements BillDao{

    ArrayList<Bill> bills;
    long counter;


    public FakeBilldao(){
        bills = new ArrayList<>();
        this.counter = 0;
    }


    @Override
    public long add(Bill bill) {
        bills.add(bill);
        return ++counter;
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
    public Bill getFromID(long id) {
        return (Bill)bills.stream().filter(bill -> bill.getId() == id);
    }
}
