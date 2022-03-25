package businessLogic;

import dao.CostumerDao;
import dao.FakeCostumerDao;
import dao.FakeTypeOfAccessDao;
import dao.TypeOfAccessDao;
import domainModel.Costumer;
import domainModel.TypeOfAccess;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ReceptionistController {

    CostumerDao costumerDao = new FakeCostumerDao();
    TypeOfAccessDao typeOfAccessDao = new FakeTypeOfAccessDao();

    public void addCostumer(String name, String surname, String email) {
        Costumer newCostumer = new Costumer(name, surname, email);
        costumerDao.add(newCostumer);
    }

    public void deleteCostumer(Costumer costumer){
        costumerDao.delete(costumer);
    }

    public ArrayList<Costumer> visualizeAllCostumer(){
        return costumerDao.getAll();
    }

    public ArrayList<TypeOfAccess> visualizeAllTypeOfAccess(){
        return typeOfAccessDao.getAll();
    }

    public ArrayList<TypeOfAccess> visualizeTypeOfAccessFromCostumer(Costumer costumer){
        ArrayList<TypeOfAccess> returnArray = new ArrayList<>();
        for (TypeOfAccess typeOfAccess : typeOfAccessDao.getAll()){
            if(typeOfAccess.getCostumer() == costumer)
                returnArray.add(typeOfAccess);
        }
        return returnArray;
    }
}
