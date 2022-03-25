package dao;

import domainModel.Access;
import domainModel.Costumer;

import java.util.ArrayList;

public class FakeAccessDao implements AccessDao{

    ArrayList<Access> acceses;

    public FakeAccessDao(){
        acceses = new ArrayList<>();
    }

    /*
    TODO mettere un metodo che crea un accesso e lo rende come parametro di ritorno, oppure inserirlo nella business logic?
     */


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
}
