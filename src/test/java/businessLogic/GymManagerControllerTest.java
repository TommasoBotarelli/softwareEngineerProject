package businessLogic;

import dao.*;
import domainModel.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GymManagerControllerTest {

    private GymManagerController gymManagerController = new GymManagerController();

    @BeforeEach
    void setUp(){
        FakeReceptionistDao.getInstance().deleteAll();
        FakePersonalTrainerDao.getInstance().deleteAll();
        FakeTypeOfAccessDao.getInstance().deleteAll();
        FakeBillDao.getInstance().deleteAll();
        FakeAccessDao.getInstance().deleteAll();
    }

    @Test
    void getAllPersonalTrainer() {
        PersonalTrainer personalTrainer1 = new PersonalTrainer("Tommaso", "Botarelli", "12753726");
        PersonalTrainer personalTrainer2 = new PersonalTrainer("Gianluca", "Botarelli", "8637235628");
        PersonalTrainer personalTrainer3 = new PersonalTrainer("Lorenzo", "Botarelli", "82673526738");

        ArrayList<PersonalTrainer> personalTrainers = new ArrayList<>();

        personalTrainers.add(personalTrainer1);
        personalTrainers.add(personalTrainer2);
        personalTrainers.add(personalTrainer3);

        FakePersonalTrainerDao.getInstance().add(personalTrainer1);
        FakePersonalTrainerDao.getInstance().add(personalTrainer2);
        FakePersonalTrainerDao.getInstance().add(personalTrainer3);

        assertEquals(personalTrainers, gymManagerController.getAllPersonalTrainer());
    }

    @Test
    void addPersonalTrainer() {
        gymManagerController.addPersonalTrainer("Test", "Test", "87385723721");
        PersonalTrainerDao personalTrainerDao = FakePersonalTrainerDao.getInstance();
        assertEquals(new PersonalTrainer("Test", "Test", "87385723721"), personalTrainerDao.getPersonalTrainer("Test", "Test", "87385723721"));
    }

    @Test
    void deletePersonalTrainer() {
        PersonalTrainer deletePersonalTrainer = new PersonalTrainer("Test", "Test", "87385723721");
        gymManagerController.deletePersonalTrainer(deletePersonalTrainer);
        PersonalTrainerDao personalTrainerDao = FakePersonalTrainerDao.getInstance();
        ArrayList<PersonalTrainer> allPersonalTrainers = personalTrainerDao.getAllPersonalTrainers();
        assertFalse(allPersonalTrainers.contains(deletePersonalTrainer));
    }

    @Test
    void getAllBills() {
        Bill bill1 = new Bill(200.0f, LocalDate.of(2022, 4, 22));
        Bill bill2 = new Bill(100.0f, LocalDate.of(2022, 4, 22));

        ArrayList<Bill> bills = new ArrayList<>();

        bills.add(bill1);
        bills.add(bill2);

        FakeBillDao.getInstance().add(bill1);
        FakeBillDao.getInstance().add(bill2);

        assertEquals(bills, gymManagerController.getAllBills());
    }

    @Test
    void getBillsOfTheDay() {
        Bill bill1 = new Bill(200.0f, LocalDate.of(2022, 4, 22));
        Bill bill2 = new Bill(100.0f, LocalDate.of(2022, 4, 22));

        ArrayList<Bill> bills = new ArrayList<>();

        bills.add(bill1);
        bills.add(bill2);

        FakeBillDao.getInstance().add(bill1);
        FakeBillDao.getInstance().add(bill2);

        assertEquals(bills, gymManagerController.getBillsOfTheDay(22, 4, 2022));
    }

    @Test
    void getAllAccess() {
        LocalDate actualDate = LocalDate.of(2022, 4, 22);

        Costumer costumer1 = new Costumer("Prova", "Test", "863715361");
        Costumer costumer2 = new Costumer("Ludovico", "Siciliani", "976425842");

        Access access1 = new Access(costumer1, actualDate, true);
        Access access2 = new Access(costumer2, actualDate, true);
        Access access3 = new Access(costumer2, actualDate.plusDays(2), false);
        Access access4 = new Access(costumer2, actualDate.plusDays(1), true);

        FakeAccessDao.getInstance().add(access1);
        FakeAccessDao.getInstance().add(access2);
        FakeAccessDao.getInstance().add(access3);
        FakeAccessDao.getInstance().add(access4);

        ArrayList<Access> allAccess = gymManagerController.getAllAccess();

        assertEquals(access1, allAccess.get(0));
        assertEquals(access2, allAccess.get(1));
        assertEquals(access3, allAccess.get(2));
        assertEquals(access4, allAccess.get(3));
    }

    @Test
    void getAllAccessFromDate() {
        LocalDate actualDate = LocalDate.of(2022, 4, 22);

        Costumer costumer1 = new Costumer("Prova", "Test", "863715361");
        Costumer costumer2 = new Costumer("Ludovico", "Siciliani", "976425842");

        Access access1 = new Access(costumer1, actualDate, true);
        Access access2 = new Access(costumer2, actualDate, true);
        Access access3 = new Access(costumer2, actualDate.plusDays(2), false);
        Access access4 = new Access(costumer2, actualDate.plusDays(1), true);

        AccessDao accessDao = FakeAccessDao.getInstance();
        accessDao.add(access1);
        accessDao.add(access2);
        accessDao.add(access3);
        accessDao.add(access4);

        assertEquals(2, gymManagerController.getAllAccessFromDate(2022, 4, 22).size());
        assertEquals(access1, gymManagerController.getAllAccessFromDate(2022, 4, 22).get(0));
        assertEquals(access2, gymManagerController.getAllAccessFromDate(2022, 4, 22).get(1));

    }

    @Test
    void getAllCostumers() {
        ArrayList<Costumer> costumersOfGym = new ArrayList<>();

        Costumer costumer1 = new Costumer("Prova", "Test", "863715361");
        Costumer costumer2 = new Costumer("Alessandro", "Siani", "976425842");
        Costumer costumer3 = new Costumer("Lorenzo", "Massi", "4362545");
        Costumer costumer4 = new Costumer("Francesco", "Corbini", "754752445");

        costumersOfGym.add(costumer1);
        costumersOfGym.add(costumer2);
        costumersOfGym.add(costumer3);
        costumersOfGym.add(costumer4);

        CostumerDao costumerDao = FakeCostumerDao.getInstance();
        costumerDao.add(costumer1);
        costumerDao.add(costumer2);
        costumerDao.add(costumer3);
        costumerDao.add(costumer4);

        assertEquals(costumersOfGym, gymManagerController.getAllCostumers());
    }

    @Test
    void getSubOfCostumer() {
        Costumer costumer1 = new Costumer("Prova", "Test", "863715361");

        ArrayList<TypeOfAccess> subscriptions = new ArrayList<>();

        Subscription subscription1 = new Subscription(LocalDate.of(2022, 4, 22), TypeOfSub.MENSILE, costumer1);
        Daily subscription2 = new Daily(LocalDate.of(2022, 4, 22), costumer1);
        Subscription subscription3 = new Subscription(LocalDate.of(2022, 4, 22), TypeOfSub.SEMESTRALE, costumer1);

        subscriptions.add(subscription1);
        subscriptions.add(subscription2);
        subscriptions.add(subscription3);

        TypeOfAccessDao typeOfAccessDao = FakeTypeOfAccessDao.getInstance();

        typeOfAccessDao.add(subscription1, 123);
        typeOfAccessDao.add(subscription2, 321);
        typeOfAccessDao.add(subscription3, 826);

        assertEquals(subscriptions, gymManagerController.getSubOfCostumer(costumer1));
    }

    @Test
    void getBillOfSub() throws Exception{
        Costumer costumer1 = new Costumer("Prova", "Test", "863715361");

        Bill bill = new Bill(200.0f, LocalDate.of(2022, 4, 22));

        BillDao billDao = FakeBillDao.getInstance();

        long id = billDao.add(bill);

        Subscription subscription = new Subscription(LocalDate.of(2022, 4, 22), TypeOfSub.MENSILE, costumer1);

        TypeOfAccessDao typeOfAccessDao = FakeTypeOfAccessDao.getInstance();

        typeOfAccessDao.add(subscription, id);

        assertEquals(bill, gymManagerController.getBillOfSub(subscription));
    }

    @Test
    void getAllReceptionist() {
        Receptionist receptionist1 = new Receptionist("Test", "Prova", "892165271");
        Receptionist receptionist2 = new Receptionist("AnotherTest", "Prova", "45235431");
        Receptionist receptionist3 = new Receptionist("AntotherReceptionist", "Receptionist", "892165271");

        ReceptionistDao receptionistDao = FakeReceptionistDao.getInstance();

        receptionistDao.addReceptionist(receptionist1);
        receptionistDao.addReceptionist(receptionist2);
        receptionistDao.addReceptionist(receptionist3);

        ArrayList<Receptionist> allReceptionist = new ArrayList<>();

        allReceptionist.add(receptionist1);
        allReceptionist.add(receptionist2);
        allReceptionist.add(receptionist3);

        assertEquals(allReceptionist, gymManagerController.getAllReceptionist());
    }

    @Test
    void addReceptionist() throws Exception{
        gymManagerController.addReceptionist("TestAdd", "Test", "7856749");
        assertEquals(new Receptionist("TestAdd", "Test", "7856749"),
                FakeReceptionistDao.getInstance().getReceptionistFromNameSurnamePhoneNumber
                        ("TestAdd", "Test", "7856749"));
    }

    @Test
    void deleteReceptionist() {
        Receptionist receptionist = new Receptionist("Tommaso", "Botarelli", "123456759");

        FakeReceptionistDao.getInstance().addReceptionist(receptionist);

        gymManagerController.deleteReceptionist(receptionist);

        assertFalse(FakeReceptionistDao.getInstance().contains(receptionist));
    }
}