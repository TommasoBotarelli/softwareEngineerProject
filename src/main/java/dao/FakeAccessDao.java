package dao;

import domainModel.Access;
import domainModel.Costumer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class FakeAccessDao implements AccessDao{

    ArrayList<Access> acceses;
    private static FakeAccessDao instance = null;

    private FakeAccessDao(){
        acceses = new ArrayList<>();
    }

    public static FakeAccessDao getInstance(){
        if (instance == null){
            instance = new FakeAccessDao();
        }
        return instance;
    }

    @Override
    public void add(Access access) {
        acceses.add(access);
    }

    @Override
    public void delete(Access access) {
        acceses.remove(access);
    }

    @Override
    public ArrayList<Access> getAll() {
        return acceses;
    }

    @Override
    public ArrayList<Access> getFromCostumer(Costumer costumer) {
        ArrayList<Access> costumerAccess = new ArrayList<>();

        for (Access access : acceses){
            if (access.getCostumer() == costumer)
                costumerAccess.add(access);
        }

        return costumerAccess;
    }

    @Override
    public ArrayList<Access> getFromDate(LocalDate date) {
        ArrayList<Access> accessOfDate = new ArrayList<>();

        for (Access a : acceses){
            if(a.getDate().getYear() == date.getYear() &&
                a.getDate().getMonth() == date.getMonth() &&
                a.getDate().getDayOfMonth() == date.getDayOfMonth())
                accessOfDate.add(a);
        }

        return accessOfDate;
    }

    @Override
    public void deleteAll() {
        acceses.clear();
    }
}
