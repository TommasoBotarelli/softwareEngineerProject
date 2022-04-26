package businessLogic;

import dao.factoryClass.DaoFactory;
import dao.interfaceClass.*;
import domainModel.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class GymManagerControllerTest {

    private GymManagerController gymManagerController = new GymManagerController();

    private static CostumerDao costumerDao;
    private static ReceptionistDao receptionistDao;
    private static TypeOfAccessDao typeOfAccessDao;
    private static AccessDao accessDao;
    private static BillDao billDao;
    private static PersonalTrainerDao personalTrainerDao;
    
    @BeforeAll
    static void beforeAll(){
        costumerDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getCostumerDao();
        receptionistDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getReceptionistDao();
        typeOfAccessDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getTypeOfAccessDao();
        accessDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getAccessDao();
        billDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getBillDao();
        personalTrainerDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getPersonalTrainerDao();
    }

    @BeforeEach
    void setUp(){
        receptionistDao.deleteAll();
        personalTrainerDao.deleteAll();
        typeOfAccessDao.deleteAll();
        billDao.deleteAll();
        accessDao.deleteAll();
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

        personalTrainerDao.add(personalTrainer1);
        personalTrainerDao.add(personalTrainer2);
        personalTrainerDao.add(personalTrainer3);

        assertEquals(personalTrainers, gymManagerController.getAllPersonalTrainer());
    }

    @Test
    void addPersonalTrainer() {
        gymManagerController.addPersonalTrainer("Test", "Test", "87385723721");

        assertEquals(new PersonalTrainer("Test", "Test", "87385723721"), personalTrainerDao.getPersonalTrainer("Test", "Test", "87385723721"));
    }

    @Test
    void deletePersonalTrainer() {
        PersonalTrainer deletePersonalTrainer = new PersonalTrainer("Test", "Test", "87385723721");
        gymManagerController.deletePersonalTrainer(deletePersonalTrainer);
        ArrayList<PersonalTrainer> allPersonalTrainers = personalTrainerDao.getAllPersonalTrainers();
        assertFalse(allPersonalTrainers.contains(deletePersonalTrainer));
    }

    @Test
    void getAllBills() {
        Bill bill1 = new Bill(200.0f, LocalDateTime.now());
        Bill bill2 = new Bill(100.0f, LocalDateTime.now().plusDays(1));

        ArrayList<Bill> bills = new ArrayList<>();

        bills.add(bill1);
        bills.add(bill2);

        billDao.add(bill1);
        billDao.add(bill2);

        assertEquals(bills, gymManagerController.getAllBills());
    }

    @Test
    void getBillsOfTheDay() {
        Bill bill1 = new Bill(200.0f, LocalDateTime.now());
        Bill bill2 = new Bill(100.0f, LocalDateTime.now().plusDays(1));

        ArrayList<Bill> bills = new ArrayList<>();

        bills.add(bill1);

        billDao.add(bill1);
        billDao.add(bill2);

        assertEquals(bills, gymManagerController.getBillsOfTheDay(LocalDate.now()));
    }

    @Test
    void getAllAccess() {
        LocalDateTime actualDateTime = LocalDateTime.now();

        Costumer costumer1 = new Costumer("Prova", "Test", "863715361");
        Costumer costumer2 = new Costumer("Ludovico", "Siciliani", "976425842");

        Access access1 = new Access(costumer1, actualDateTime);
        Access access2 = new Access(costumer2, actualDateTime);
        Access access3 = new Access(costumer2, actualDateTime.plusDays(2));
        Access access4 = new Access(costumer2, actualDateTime.plusDays(1));

        accessDao.add(access1);
        accessDao.add(access2);
        accessDao.add(access3);
        accessDao.add(access4);

        ArrayList<Access> allAccess = gymManagerController.getAllAccess();

        assertEquals(access1, allAccess.get(0));
        assertEquals(access2, allAccess.get(1));
        assertEquals(access3, allAccess.get(2));
        assertEquals(access4, allAccess.get(3));
    }

    @Test
    void getAllAccessFromDate() {
        LocalDateTime actualDateTime = LocalDateTime.now();

        Costumer costumer1 = new Costumer("Prova", "Test", "863715361");
        Costumer costumer2 = new Costumer("Ludovico", "Siciliani", "976425842");

        Access access1 = new Access(costumer1, actualDateTime);
        Access access2 = new Access(costumer2, actualDateTime);
        Access access3 = new Access(costumer2, actualDateTime.plusDays(2));
        Access access4 = new Access(costumer2, actualDateTime.plusDays(1));

        accessDao.add(access1);
        accessDao.add(access2);
        accessDao.add(access3);
        accessDao.add(access4);

        assertEquals(2, gymManagerController.getAllAccessFromDate(actualDateTime.getYear(), actualDateTime.getMonthValue(), actualDateTime.getDayOfMonth()).size());
        assertEquals(access1, gymManagerController.getAllAccessFromDate(actualDateTime.getYear(), actualDateTime.getMonthValue(), actualDateTime.getDayOfMonth()).get(0));
        assertEquals(access2, gymManagerController.getAllAccessFromDate(actualDateTime.getYear(), actualDateTime.getMonthValue(), actualDateTime.getDayOfMonth()).get(1));

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

        Subscription subscription1 = new Subscription(LocalDate.of(2022, 4, 22), TypeOfSub.MONTHLY, costumer1);
        Daily subscription2 = new Daily(LocalDate.of(2022, 4, 22), costumer1);
        Subscription subscription3 = new Subscription(LocalDate.of(2022, 4, 22), TypeOfSub.HALFYEARLY, costumer1);

        subscriptions.add(subscription1);
        subscriptions.add(subscription2);
        subscriptions.add(subscription3);

        typeOfAccessDao.addWithBill(subscription1, 123);
        typeOfAccessDao.addWithBill(subscription2, 321);
        typeOfAccessDao.addWithBill(subscription3, 826);

        assertEquals(subscriptions, gymManagerController.getSubOfCostumer(costumer1));
    }

    @Test
    void getBillOfSub() throws Exception{
        Costumer costumer1 = new Costumer("Prova", "Test", "863715361");

        Bill bill = new Bill(200.0f, LocalDateTime.of(LocalDate.of(2022, 4, 22), LocalTime.now()));

        long id = billDao.add(bill);

        Subscription subscription = new Subscription(LocalDate.of(2022, 4, 22), TypeOfSub.MONTHLY, costumer1);

        typeOfAccessDao.addWithBill(subscription, id);

        assertEquals(bill, gymManagerController.getBillOfSub(subscription));
    }

    @Test
    void getAllReceptionist() {
        Receptionist receptionist1 = new Receptionist("Test", "Prova", "892165271");
        Receptionist receptionist2 = new Receptionist("AnotherTest", "Prova", "45235431");
        Receptionist receptionist3 = new Receptionist("AntotherReceptionist", "Receptionist", "892165271");

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
    void addReceptionist(){
        gymManagerController.addReceptionist("TestAdd", "Test", "7856749");
        assertEquals(new Receptionist("TestAdd", "Test", "7856749"),
                receptionistDao.getReceptionistFromNameSurnamePhoneNumber
                        ("TestAdd", "Test", "7856749"));
    }

    @Test
    void deleteReceptionist() {
        Receptionist receptionist = new Receptionist("Tommaso", "Botarelli", "123456759");

        receptionistDao.addReceptionist(receptionist);

        gymManagerController.deleteReceptionist(receptionist);

        assertFalse(receptionistDao.contains(receptionist));
    }
}