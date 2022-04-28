package businessLogic;

import dao.concreteClass.*;
import dao.factoryClass.DaoFactory;
import dao.interfaceClass.*;
import domainModel.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class GymManagerController {

    private GymManager thisGymManager;

    private PersonalTrainerDao personalTrainerDao;
    private BillDao billDao;
    private AccessDao accessDao;
    private CostumerDao costumerDao;
    private ReceptionistDao receptionistDao;
    private GymManagerDao gymManagerDao;
    private TrialSubscriptionDao trialSubscriptionDao;
    private SubscriptionDao subscriptionDao;
    private DailyDao dailyDao;

    public GymManagerController(){
        trialSubscriptionDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getTrialSubscriptionDao();
        subscriptionDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getSubscriptionDao();
        dailyDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getDailyDao();
        personalTrainerDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getPersonalTrainerDao();
        billDao = FakeBillDao.getInstance();
        accessDao = FakeAccessDao.getInstance();
        costumerDao = FakeCostumerDao.getInstance();
        receptionistDao = FakeReceptionistDao.getInstance();
        gymManagerDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getGymManagerDao();
    }

    public boolean addGymManager(String name, String surname, String phoneNumber){
        GymManager gymManager = new GymManager(name, surname, phoneNumber);
        return gymManagerDao.addGymManager(gymManager);
    }

    public boolean setCurrentUser(String name, String surname, String phoneNumber){
        GymManager gymManager = gymManagerDao.getGymManager(name, surname, phoneNumber);

        if (gymManager != null)
            this.thisGymManager = gymManager;

        return gymManager != null;
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

    public ArrayList<AccessType> getSubOfCostumer(Costumer costumer){
        ArrayList<AccessType> allAccessTypeOfCostumer = new ArrayList<>();
        allAccessTypeOfCostumer.add(trialSubscriptionDao.getFromCostumer(costumer));
        allAccessTypeOfCostumer.addAll(dailyDao.getFromCostumer(costumer));
        allAccessTypeOfCostumer.addAll(subscriptionDao.getFromCostumer(costumer));
        return allAccessTypeOfCostumer;
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
