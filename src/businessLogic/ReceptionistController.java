package businessLogic;

import dao.CostumerDao;
import dao.FakeCostumerDao;
import domainModel.Costumer;

import java.util.ArrayList;

public class ReceptionistController {

    CostumerDao costumerDao = new FakeCostumerDao();

    void addCostumer(String name, String surname, String email) {
        Costumer newCostumer = new Costumer(name, surname, email);
        costumerDao.add(newCostumer);
    }

    ArrayList<Costumer> visualizeAllCostumer(){
        return costumerDao.getAll();
    }

    Costumer selectCostumer(String name, String surname){
        return costumerDao.getFromName(name, surname);
    }
}
