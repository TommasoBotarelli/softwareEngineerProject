package dao;

import domainModel.Costumer;

import java.util.ArrayList;

public class FakeCostumerDao implements CostumerDao{

    private ArrayList<Costumer> costumers;


    FakeCostumerDao(){
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
    public Costumer getFromId(long id) {
        return (Costumer) costumers.stream().filter(costumer -> id == costumer.getId());
    }
}
