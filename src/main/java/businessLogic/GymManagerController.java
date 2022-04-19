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
    ReceptionistDao receptionistDao;

    public GymManagerController(){
        personalTrainerDao = new FakePersonalTrainerDao();
        billDao = new FakeBillDao();
        accessDao = new FakeAccessDao();
        costumerDao = new FakeCostumerDao();
        typeOfAccessDao = new FakeTypeOfAccessDao();
        receptionistDao = new FakeReceptionistDao();
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

    public ArrayList<Receptionist> getAllReceptionist(){
        return receptionistDao.getAllReceptionist();
    }

    public void addReceptionist(String name, String surname, String phoneNumber){
        receptionistDao.addReceptionist(new Receptionist(name, surname, phoneNumber));
    }

    public void deleteReceptionist(Receptionist receptionist){
        receptionistDao.deleteReceptionist(receptionist);
    }
}
