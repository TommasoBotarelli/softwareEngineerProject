package dao;

import domainModel.Costumer;
import domainModel.TypeOfAccess;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

public class FakeTypeOfAccessDao implements TypeOfAccessDao{

    ArrayList<TypeOfAccess> typeOfAccesses;

    public FakeTypeOfAccessDao(){
        typeOfAccesses = new ArrayList<>();
    }


    @Override
    public void add(TypeOfAccess typeOfAccess) {
        typeOfAccesses.add(typeOfAccess);
    }

    @Override
    public void delete(TypeOfAccess typeOfAccess) {
        typeOfAccesses.remove(typeOfAccess);
    }

    @Override
    public ArrayList<TypeOfAccess> getAll() {
        return typeOfAccesses;
    }

    @Override
    public ArrayList<TypeOfAccess> getFromCostumer(Costumer costumer) {
        ArrayList<TypeOfAccess> returnArray = new ArrayList<>();

        for (TypeOfAccess typeOfAccess : typeOfAccesses){
            if (typeOfAccess.getCostumer() == costumer)
                returnArray.add(typeOfAccess);
        }

        return returnArray;
    }

    @Override
    public void deleteAllTypeOfAccessFromCostumer(Costumer costumer) {
        typeOfAccesses.removeIf(typeOfAccess -> typeOfAccess.getCostumer() == costumer);

    }


}
