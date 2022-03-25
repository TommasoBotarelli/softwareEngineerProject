package dao;

import domainModel.Bill;

import java.util.ArrayList;
import java.util.Calendar;

public class FakeBilldao implements BillDao{

    ArrayList<Bill> bills;


    public FakeBilldao(){
        bills = new ArrayList<>();
    }


    @Override
    public void add(Bill bill) {
        bills.add(bill);
    }

    @Override
    public ArrayList<Bill> getAll() {
        return bills;
    }

    @Override
    public ArrayList<Bill> getFromDate(Calendar date) {
        ArrayList<Bill> billOfDay = new ArrayList<>();

        for (Bill bill : bills){
            if(bill.getDate().get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH) &&
                    bill.getDate().get(Calendar.MONTH) == date.get(Calendar.MONTH) &&
                        bill.getDate().get(Calendar.YEAR) == date.get(Calendar.YEAR))
                billOfDay.add(bill);
        }

        return billOfDay;
    }
}
