package businessLogic;

import dao.concreteClass.*;
import dao.interfaceClass.*;
import domainModel.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class GymManagerController {

    private GymManager thisGymManager;

    private PersonalTrainerDao personalTrainerDao;
    private BillDao billDao;
    private AccessDao accessDao;
    private CostumerDao costumerDao;
    private TypeOfAccessDao typeOfAccessDao;
    private ReceptionistDao receptionistDao;

    public GymManagerController(){
        personalTrainerDao = FakePersonalTrainerDao.getInstance();
        billDao = FakeBillDao.getInstance();
        accessDao = FakeAccessDao.getInstance();
        costumerDao = FakeCostumerDao.getInstance();
        typeOfAccessDao = FakeTypeOfAccessDao.getInstance();
        receptionistDao = FakeReceptionistDao.getInstance();
    }

    public void setThisGymManager(GymManager thisGymManager) {
        this.thisGymManager = thisGymManager;
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

    public ArrayList<Bill> getBillsOfTheDay(LocalDate date){
        return billDao.getFromDate(date);
    }

    public ArrayList<Access> getAllAccess(){
        return accessDao.getAll();
    }

    public ArrayList<Access> getAllAccessFromDate(int year, int month, int day){
        return accessDao.getFromDate(LocalDate.of(year, month, day));
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
