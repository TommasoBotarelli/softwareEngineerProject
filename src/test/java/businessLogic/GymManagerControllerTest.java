package businessLogic;

import dao.factoryClass.DaoFactory;
import dao.interfaceClass.*;
import domainModel.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class GymManagerControllerTest {

    private GymManagerController gymManagerController = new GymManagerController();

    private static TrialSubscriptionDao trialSubscriptionDao;
    private static SubscriptionDao subscriptionDao;
    private static DailyDao dailyDao;
    private static CustomerDao customerDao;
    private static ReceptionistDao receptionistDao;
    private static AccessDao accessDao;
    private static BillDao billDao;
    private static PersonalTrainerDao personalTrainerDao;

    
    @BeforeAll
    static void beforeAll(){
        trialSubscriptionDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getTrialSubscriptionDao();
        subscriptionDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getSubscriptionDao();
        dailyDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getDailyDao();
        customerDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getCustomerDao();
        receptionistDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getReceptionistDao();
        accessDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getAccessDao();
        billDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getBillDao();
        personalTrainerDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getPersonalTrainerDao();
    }

    @BeforeEach
    void setUp(){
        receptionistDao.deleteAll();
        personalTrainerDao.deleteAll();
        billDao.deleteAll();
        accessDao.deleteAll();
        trialSubscriptionDao.deleteAll();
        subscriptionDao.deleteAll();
        dailyDao.deleteAll();
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

        Customer customer1 = new Customer("Prova", "Test", "863715361");
        Customer customer2 = new Customer("Ludovico", "Siciliani", "976425842");

        Access access1 = new Access(customer1, actualDateTime);
        Access access2 = new Access(customer2, actualDateTime);
        Access access3 = new Access(customer2, actualDateTime.plusDays(2));
        Access access4 = new Access(customer2, actualDateTime.plusDays(1));

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

        Customer customer1 = new Customer("Prova", "Test", "863715361");
        Customer customer2 = new Customer("Ludovico", "Siciliani", "976425842");

        Access access1 = new Access(customer1, actualDateTime);
        Access access2 = new Access(customer2, actualDateTime);
        Access access3 = new Access(customer2, actualDateTime.plusDays(2));
        Access access4 = new Access(customer2, actualDateTime.plusDays(1));

        accessDao.add(access1);
        accessDao.add(access2);
        accessDao.add(access3);
        accessDao.add(access4);

        assertEquals(2,
                gymManagerController.getAllAccessFromDate(actualDateTime.getYear(),
                        actualDateTime.getMonthValue(), actualDateTime.getDayOfMonth()).size()
        );
        assertEquals(access1,
                gymManagerController.getAllAccessFromDate(actualDateTime.getYear(),
                        actualDateTime.getMonthValue(), actualDateTime.getDayOfMonth()).get(0)
        );
        assertEquals(access2,
                gymManagerController.getAllAccessFromDate(
                        actualDateTime.getYear(),
                        actualDateTime.getMonthValue(), actualDateTime.getDayOfMonth()).get(1)
        );

    }

    @Test
    void getAllCustomers() {
        ArrayList<Customer> customersOfGym = new ArrayList<>();

        Customer customer1 = new Customer("Prova", "Test", "863715361");
        Customer customer2 = new Customer("Alessandro", "Siani", "976425842");
        Customer customer3 = new Customer("Lorenzo", "Massi", "4362545");
        Customer customer4 = new Customer("Francesco", "Corbini", "754752445");

        customersOfGym.add(customer1);
        customersOfGym.add(customer2);
        customersOfGym.add(customer3);
        customersOfGym.add(customer4);

        customerDao.add(customer1);
        customerDao.add(customer2);
        customerDao.add(customer3);
        customerDao.add(customer4);

        assertEquals(customersOfGym, gymManagerController.getAllCustomers());
    }

    @Test
    void getSubOfCustomer() {
        Customer customer1 = new Customer("Prova", "Test", "863715361");

        Bill genericBill = new Bill(20f, LocalDateTime.now());

        TrialSubscription trialSubscription1 = new TrialSubscription(customer1, LocalDate.now());
        Subscription subscription2 = new Subscription(LocalDate.now().plusMonths(1), TypeOfSub.MONTHLY, customer1, genericBill);
        Daily daily1 = new Daily(LocalDate.now().plusMonths(3), customer1, genericBill);

        ArrayList<AccessType> typeOfAccessOfCustomer = new ArrayList<>();

        typeOfAccessOfCustomer.add(trialSubscription1);
        typeOfAccessOfCustomer.add(daily1);
        typeOfAccessOfCustomer.add(subscription2);

        trialSubscriptionDao.add(trialSubscription1);
        dailyDao.addDaily(daily1);
        subscriptionDao.add(subscription2);

        assertEquals(typeOfAccessOfCustomer, gymManagerController.getSubOfCustomer(customer1));
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