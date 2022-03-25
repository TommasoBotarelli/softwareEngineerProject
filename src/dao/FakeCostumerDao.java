package dao;

import domainModel.Costumer;

import java.util.ArrayList;

public class FakeCostumerDao implements CostumerDao{

    private ArrayList<Costumer> costumers;


    public FakeCostumerDao(){
        costumers = new ArrayList<>();
    }


    @Override
    public void add(Costumer costumer) {
        costumers.add(costumer);
    }

    @Override
    public void delete(Costumer costumer) {
        costumers.remove(costumer);
    }

    @Override
    public ArrayList<Costumer> getAll() {
        return costumers;
    }

    @Override
    public ArrayList<Costumer> getFromName(String name, String surname) {
        ArrayList<Costumer> returnArray = new ArrayList<>();

        for (Costumer costumer : costumers){
            if(costumer.getInfo().name.equals(name) && costumer.getInfo().surname.equals(surname))
                returnArray.add(costumer);
        }

        return returnArray;
    }
}
