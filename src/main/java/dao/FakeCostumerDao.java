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
    public void delete(Costumer costumer) {
        costumers.remove(costumer);
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
        return costumers.stream().filter(costumer -> costumer.getName().equals(name) &&
                costumer.getSurname().equals(surname) && costumer.getPhoneNumber().equals(phoneNumber)).collect(Collectors.toList()).get(0);
    }
}
