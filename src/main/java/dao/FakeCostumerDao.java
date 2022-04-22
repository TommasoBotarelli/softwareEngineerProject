package dao;

import domainModel.Costumer;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class FakeCostumerDao implements CostumerDao{

    private ArrayList<Costumer> costumers;
    private static FakeCostumerDao instance = null;

    public static FakeCostumerDao getInstance(){
        if (instance == null){
            instance = new FakeCostumerDao();
        }
        return instance;
    }

    private FakeCostumerDao(){
        costumers = new ArrayList<>();
    }

    @Override
    public void add(Costumer costumer) {
        if (!costumers.contains(costumer)){
            costumers.add(costumer);
        }
    }

    @Override
    public boolean delete(Costumer costumer) {
        if (costumers.contains(costumer)){
            costumers.remove(costumer);
            return true;
        }
        else
            return false;
    }

    @Override
    public ArrayList<Costumer> getAll() {
        return costumers;
    }

    @Override
    public ArrayList<Costumer> getFromNameSurname(String name, String surname) {
        ArrayList<Costumer> returnArray = new ArrayList<>();

        for (Costumer costumer : costumers){
            if(costumer.getName().equals(name) && costumer.getSurname().equals(surname))
                returnArray.add(costumer);
        }

        return returnArray;
    }

    @Override
    public Costumer getSelectedCostumer(String name, String surname, String phoneNumber) {
        Costumer costumerToFind = new Costumer(name, surname, phoneNumber);

        if (costumers.contains(costumerToFind))
            return costumers.stream().filter(costumer -> costumer.equals(costumerToFind)).collect(Collectors.toList()).get(0);
        else
            return null;
    }

    @Override
    public void deleteAll() {
        costumers.clear();
    }
}
