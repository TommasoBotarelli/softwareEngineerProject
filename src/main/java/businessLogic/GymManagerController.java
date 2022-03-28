package businessLogic;

import dao.*;
import domainModel.Access;
import domainModel.Bill;
import domainModel.Costumer;
import domainModel.PersonalTrainer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class GymManagerController {

    PersonalTrainerDao personalTrainerDao;
    BillDao billDao;
    AccessDao accessDao;
    CostumerDao costumerDao;

    public GymManagerController(){
        personalTrainerDao = new FakePersonalTrainerDao();
        billDao = new FakeBilldao();
        accessDao = new FakeAccessDao();
        costumerDao = new FakeCostumerDao();
    }

    public ArrayList<PersonalTrainer> getAllPersonalTrainer(){
        return personalTrainerDao.getAllPersonalTrainers();
    }

    public void addPersonalTrainer(String name, String surname, String phoneNumber){
        PersonalTrainer personalTrainer = new PersonalTrainer(name, surname, phoneNumber);
        personalTrainerDao.add(personalTrainer);
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

    public ArrayList<Costumer> searchCostumer(String name, String surname){
        return costumerDao.getFromNameSurname(name, surname);
    }

}
