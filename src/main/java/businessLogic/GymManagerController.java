package businessLogic;

import dao.*;
import domainModel.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class GymManagerController {

    PersonalTrainerDao personalTrainerDao;
    BillDao billDao;
    AccessDao accessDao;
    CostumerDao costumerDao;
    TypeOfAccessDao typeOfAccessDao;

    public GymManagerController(){
        personalTrainerDao = new FakePersonalTrainerDao();
        billDao = new FakeBilldao();
        accessDao = new FakeAccessDao();
        costumerDao = new FakeCostumerDao();
        typeOfAccessDao = new FakeTypeOfAccessDao();
    }

    public ArrayList<PersonalTrainer> getAllPersonalTrainer(){
        return personalTrainerDao.getAllPersonalTrainers();
    }

    public boolean addPersonalTrainer(String name, String surname, String phoneNumber){
        boolean addComplete = false;

        PersonalTrainer newPersonalTrainer = new PersonalTrainer(name, surname, phoneNumber);

        if (!personalTrainerDao.getAllPersonalTrainers().contains(newPersonalTrainer)){
            personalTrainerDao.add(newPersonalTrainer);
            addComplete = true;
        }

        return addComplete;
    }

    public void deletePersonalTrainer(PersonalTrainer personalTrainer){
        personalTrainerDao.delete(personalTrainer);
    }

    public ArrayList<Bill> getAllBills(){
        return billDao.getAll();
    }

    public ArrayList<Bill> getBillsOfTheDay(int day, int month, int year){
        LocalDate date = LocalDate.of(year, month, day);
        return billDao.getFromDate(date);
    }

    public ArrayList<Access> getAllAccess(){
        return accessDao.getAll();
    }

    public ArrayList<Costumer> getAllCostumers(){
        return costumerDao.getAll();
    }

    public ArrayList<Costumer> searchCostumerFromNameSurname(String name, String surname){
        return costumerDao.getFromNameSurname(name, surname);
    }

    public ArrayList<TypeOfAccess> getSubOfCostumer(Costumer costumer){
        return typeOfAccessDao.getFromCostumer(costumer);
    }

    public Bill getBillOfSub(TypeOfAccess typeOfAccess){
        return billDao.getFromID(typeOfAccess.getBillID());
    }
}
