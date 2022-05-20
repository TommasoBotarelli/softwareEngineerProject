package businessLogic;

import dao.concreteClass.FakeAccessDao;
import dao.concreteClass.FakeBillDao;
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

class ReceptionistControllerTest {

    private ReceptionistController receptionistController = new ReceptionistController();
    private static CustomerDao customerDao;
    private static ReceptionistDao receptionistDao;
    private static BadgeDao badgeDao;
    private static AccessDao accessDao;
    private static BillDao billDao;
    private static TrialSubscriptionDao trialSubscriptionDao;
    private static SubscriptionDao subscriptionDao;
    private static DailyDao dailyDao;

    @BeforeAll
    static void beforeAll(){
        trialSubscriptionDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getTrialSubscriptionDao();
        subscriptionDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getSubscriptionDao();
        dailyDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getDailyDao();
        customerDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getCustomerDao();
        receptionistDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getReceptionistDao();
        badgeDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getBadgeDao();
        accessDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getAccessDao();
        billDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getBillDao();
    }

    @BeforeEach
    void setUp(){
        trialSubscriptionDao.deleteAll();
        subscriptionDao.deleteAll();
        dailyDao.deleteAll();
        customerDao.deleteAll();
        FakeBillDao.getInstance().deleteAll();
        FakeAccessDao.getInstance().deleteAll();
    }

    @Test
    void addCustomer() {
        Customer customer1 = new Customer("Tommaso", "Botarelli", "71638723");

        receptionistController.addCustomer("Tommaso", "Botarelli", "71638723");

        assertEquals(customer1, customerDao.getAll().get(0));
    }

    @Test
    void setCurrentReceptionist(){
        Receptionist receptionist = new Receptionist("Tommaso", "Botarelli", "71638723");

        receptionistController.setCurrentReceptionist(receptionist);

        assertEquals(receptionist, receptionistController.getCurrentReceptionist());
    }

    @Test
    void deleteCustomer() {
        Customer customer1 = new Customer("Giulio", "Cesare", "78254821");
        Customer customer2 = new Customer("Cice", "Rone", "45235314");
        Customer customer3 = new Customer("Giulio", "Camillo", "4214214214");

        boolean firstDelete = receptionistController.deleteCustomer(customer1);

        assertFalse(firstDelete);

        customerDao.add(customer1);
        customerDao.add(customer2);
        customerDao.add(customer3);

        boolean secondDelete = receptionistController.deleteCustomer(customer1);

        ArrayList<Customer> customers = new ArrayList<>();

        customers.add(customer2);
        customers.add(customer3);

        assertEquals(customers, customerDao.getAll());
    }

    @Test
    void visualizeAllCustomer() {
        ArrayList<Customer> allCustomers = new ArrayList<>();

        Customer customer1 = new Customer("Giulio", "Cesare", "78254821");
        Customer customer2 = new Customer("Cice", "Rone", "45235314");
        Customer customer3 = new Customer("Giulio", "Camillo", "4214214214");

        allCustomers.add(customer1);
        allCustomers.add(customer2);
        allCustomers.add(customer3);

        customerDao.add(customer1);
        customerDao.add(customer2);
        customerDao.add(customer3);

        assertEquals(allCustomers, receptionistController.visualizeAllCustomer());
    }

    @Test
    void visualizeAllTypeOfAccess() {
        LocalDate actualDate = LocalDate.of(2022, 4, 21);

        Customer customer1 = new Customer("Giulio", "Cesare", "78254821");
        Customer customer2 = new Customer("Cice", "Rone", "45235314");
        Customer customer3 = new Customer("Giulio", "Camillo", "4214214214");
        Customer customer4 = new Customer("Tommaso", "Botarelli", "8723521631");

        Bill genericBill = new Bill(20f, LocalDateTime.now());

        TrialSubscription trialSubscription1 = new TrialSubscription(customer1, LocalDate.now());
        Subscription subscription2 = new Subscription(LocalDate.now().plusMonths(1), TypeOfSub.MONTHLY, customer2, genericBill);
        Daily daily1 = new Daily(LocalDate.now().plusMonths(3), customer3, genericBill);
        Daily daily2 = new Daily(LocalDate.now().plusMonths(3), customer4, genericBill);

        ArrayList<AccessType> allSubscriptions = new ArrayList<>();

        allSubscriptions.add(trialSubscription1);
        allSubscriptions.add(daily1);
        allSubscriptions.add(daily2);
        allSubscriptions.add(subscription2);

        trialSubscriptionDao.add(trialSubscription1);
        subscriptionDao.add(subscription2);
        dailyDao.addDaily(daily1);
        dailyDao.addDaily(daily2);

        assertEquals(allSubscriptions, receptionistController.getAllAccessType());
    }

    @Test
    void visualizeTypeOfAccessFromCustomer() {
        LocalDate actualDate = LocalDate.of(2022, 4, 21);

        Customer customer1 = new Customer("Giulio", "Cesare", "78254821");
        Customer customer2 = new Customer("Cice", "Rone", "45235314");
        Customer customer3 = new Customer("Giulio", "Camillo", "4214214214");
        Customer customer4 = new Customer("Tommaso", "Botarelli", "8723521631");

        Bill genericBill = new Bill(20f, LocalDateTime.now());

        TrialSubscription trialSubscription1 = new TrialSubscription(customer1, LocalDate.now());
        Subscription subscription2 = new Subscription(LocalDate.now().plusMonths(1), TypeOfSub.MONTHLY, customer2, genericBill);
        Daily daily1 = new Daily(LocalDate.now().plusMonths(3), customer3, genericBill);
        Daily daily2 = new Daily(LocalDate.now().plusMonths(3), customer4, genericBill);

        ArrayList<AccessType> subscriptionOfCustomer1 = new ArrayList<>();

        subscriptionOfCustomer1.add(trialSubscription1);

        trialSubscriptionDao.add(trialSubscription1);
        subscriptionDao.add(subscription2);
        dailyDao.addDaily(daily1);
        dailyDao.addDaily(daily2);

        assertEquals(subscriptionOfCustomer1, receptionistController.getAllAccessTypeFromCustomer(customer1));
    }

    @Test
    void findCustomer() {
        Customer customer1 = new Customer("Giulio", "Cesare", "78254821");
        Customer customer2 = new Customer("Cice", "Rone", "45235314");
        Customer customer3 = new Customer("Giulio", "Camillo", "4214214214");
        Customer customer4 = new Customer("Tommaso", "Botarelli", "8723521631");
        Customer customer4Bis = new Customer("Tommaso", "Botarelli", "452341324214");

        ArrayList<Customer> customersWithSameNameSurname = new ArrayList<>();
        customersWithSameNameSurname.add(customer4);
        customersWithSameNameSurname.add(customer4Bis);

        customerDao.add(customer1);
        customerDao.add(customer2);
        customerDao.add(customer3);
        customerDao.add(customer4);
        customerDao.add(customer4Bis);

        assertEquals(0, receptionistController.findCustomer("Giulio", "Giuli").size());
        assertEquals(customersWithSameNameSurname, receptionistController.findCustomer("Tommaso", "Botarelli"));
    }

    @Test
    void selectCustomer() {
        Customer customer1 = new Customer("Giulio", "Cesare", "78254821");
        Customer customer2 = new Customer("Cice", "Rone", "45235314");
        Customer customer3 = new Customer("Giulio", "Camillo", "4214214214");
        Customer customer4 = new Customer("Tommaso", "Botarelli", "8723521631");
        Customer customer4Bis = new Customer("Tommaso", "Botarelli", "452341324214");

        customerDao.add(customer1);
        customerDao.add(customer2);
        customerDao.add(customer3);
        customerDao.add(customer4);
        customerDao.add(customer4Bis);

        assertNull(receptionistController.selectCustomer("Gianluca", "Rossi", "65725181"));
        assertEquals(customer4Bis, receptionistController.selectCustomer("Tommaso", "Botarelli", "452341324214"));
    }

    @Test
    void addAccessForCustomerFromBadge() {
        Customer customer1 = new Customer("Giulio", "Cesare", "78254821");
        Customer customer2 = new Customer("Cice", "Rone", "45235314");

        customerDao.add(customer1);
        customerDao.add(customer2);

        Badge badge1 = new Badge(customer1);
        Badge badge2 = new Badge(customer2);

        long id1 = badgeDao.addBadge(badge1);
        //Il badge di Customer 2 non esiste nel sistema.
        long id2 = id1 + 1;

        Bill genericBill = new Bill(20f, LocalDateTime.now());

        TrialSubscription trialSubscription1 = new TrialSubscription(customer1, LocalDate.now());
        Subscription subscription2 = new Subscription(LocalDate.now(), TypeOfSub.MONTHLY, customer2, genericBill);
        Daily daily1 = new Daily(LocalDate.now().plusMonths(2), customer1, genericBill);
        Daily daily2 = new Daily(LocalDate.now().plusMonths(2), customer2, genericBill);

        trialSubscriptionDao.add(trialSubscription1);
        subscriptionDao.add(subscription2);
        dailyDao.addDaily(daily1);
        dailyDao.addDaily(daily2);

        boolean result = false;

        try{
            receptionistController.addAccessForCustomerFromBadge(id1, LocalDateTime.now().plusDays(1));
            result = true;
            assertEquals(LocalDateTime.now().plusDays(1), accessDao.getFromCustomer(customer1).get(0).getAccessTime());
        }
        catch (Exception e){
            System.out.println(e.getMessage() + " non dovrebbe essere stampato");
        }

        assertTrue(result);
        result = false;

        try{
            receptionistController.addAccessForCustomerFromBadge(id1, LocalDateTime.now().plusMonths(1));
            result = true;
        }
        catch (Exception e){
            System.out.println(e.getMessage() + " (STAMPA OK)");
        }

        assertFalse(result);

        try{
            receptionistController.addAccessForCustomerFromBadge(id2, LocalDateTime.now().plusDays(1));
        }
        catch(Exception e){
            assertEquals("A Customer with this id doesn't exist", e.getMessage());
        }

        result = false;
        id2 = badgeDao.addBadge(new Badge(customer2));

        try{
            receptionistController.addAccessForCustomerFromBadge(id2, LocalDateTime.now().plusMonths(2));
            result = true;
        }
        catch (Exception e){
            System.out.println(e.getMessage() + " non dovrebbe essere stampato");
        }

        assertTrue(result);
        result = false;

        try{
            receptionistController.addAccessForCustomerFromBadge(id2, LocalDateTime.now().plusMonths(10));
            result = true;
        }
        catch (Exception e){
            System.out.println(e.getMessage() + " (STAMPA OK)");
        }
        assertFalse(result);
    }

    @Test
    void addAccessForCustomer() {
        LocalDateTime actualDatetime = LocalDateTime.now();

        Customer customer1 = new Customer("Giulio", "Cesare", "78254821");
        Customer customer2 = new Customer("Cice", "Rone", "45235314");

        customerDao.add(customer1);
        customerDao.add(customer2);

        Bill genericBill = new Bill(20f, LocalDateTime.now());

        TrialSubscription trialSubscription1 = new TrialSubscription(customer1, actualDatetime.toLocalDate());
        Subscription subscription2 = new Subscription(actualDatetime.toLocalDate(), TypeOfSub.MONTHLY, customer2, genericBill);
        Daily daily1 = new Daily(actualDatetime.toLocalDate().plusMonths(2), customer1, genericBill);
        Daily daily2 = new Daily(actualDatetime.toLocalDate().plusMonths(2), customer2, genericBill);

        trialSubscriptionDao.add(trialSubscription1);
        subscriptionDao.add(subscription2);
        dailyDao.addDaily(daily1);
        dailyDao.addDaily(daily2);

        boolean result = false;
        try{
            receptionistController.addAccessForCustomer(customer1, LocalDateTime.now().plusDays(1));
            result = true;
        }
        catch (Exception e){
            System.out.println(e.getMessage() + " non dovrebbe essere stampato");
        }

        assertTrue(result);
        assertEquals(LocalDateTime.now().plusDays(1), accessDao.getFromCustomer(customer1).get(0).getAccessTime());

        result = false;

        try{
            receptionistController.addAccessForCustomer(customer2, LocalDateTime.now().plusMonths(2));
            result = true;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        assertTrue(result);

        result = false;
        try {
            receptionistController.addAccessForCustomer(customer2, LocalDateTime.now().plusMonths(2));
            result = true;
        }
        catch(Exception e){
            System.out.println(e.getMessage() + " (STAMPA OK)");
        }

        assertFalse(result);
        result = false;

        try{
            receptionistController.addAccessForCustomer(customer1, LocalDateTime.now().plusDays(14));
            result = true;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        assertTrue(result);
        result = false;

        try{
            receptionistController.addAccessForCustomer(customer1, LocalDateTime.now());
            result = true;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        assertTrue(result);
    }

    @Test
    void addBill() {
        LocalDateTime time1 = LocalDateTime.now();
        receptionistController.addBill(200.0f, time1);
        assertEquals(time1, billDao.getAll().get(0).getDateTime());
    }

    @Test
    void addAccessType() {
        Customer customer1 = new Customer("Tommaso", "Botarelli", "8926735");
        customerDao.add(customer1);

        Bill genericBill = new Bill(20f, LocalDateTime.now());
        long genericBillID = billDao.add(genericBill);

        receptionistController.addAccessType("subscription", "trial", genericBillID, LocalDate.now(), customer1);

        assertEquals(LocalDate.now(), trialSubscriptionDao.getAll().get(0).getEmission());
        assertEquals(LocalDate.now().plusDays(14), trialSubscriptionDao.getAll().get(0).getExpiration());
        assertEquals(customer1, trialSubscriptionDao.getAll().get(0).getCustomerTarget());

        receptionistController.addAccessType("subscription", "monthly", genericBillID, LocalDate.now(), customer1);

        assertEquals(LocalDate.now(), subscriptionDao.getAll().get(0).getEmission());
        assertEquals(LocalDate.now().plusMonths(1), subscriptionDao.getAll().get(0).getExpiration());
        assertEquals(customer1, subscriptionDao.getAll().get(0).getMyCustomer());
        assertEquals(TypeOfSub.MONTHLY, subscriptionDao.getAll().get(0).getTypeOfSub());

        subscriptionDao.deleteAll();

        receptionistController.addAccessType("subscription", "quarterly", genericBillID, LocalDate.now(), customer1);

        assertEquals(LocalDate.now(), subscriptionDao.getAll().get(0).getEmission());
        assertEquals(LocalDate.now().plusMonths(3), subscriptionDao.getAll().get(0).getExpiration());
        assertEquals(customer1, subscriptionDao.getAll().get(0).getMyCustomer());
        assertEquals(TypeOfSub.QUARTERLY, subscriptionDao.getAll().get(0).getTypeOfSub());

        subscriptionDao.deleteAll();

        receptionistController.addAccessType("subscription", "halfyearly", genericBillID, LocalDate.now(), customer1);

        assertEquals(LocalDate.now(), subscriptionDao.getAll().get(0).getEmission());
        assertEquals(LocalDate.now().plusMonths(6), subscriptionDao.getAll().get(0).getExpiration());
        assertEquals(customer1, subscriptionDao.getAll().get(0).getMyCustomer());
        assertEquals(TypeOfSub.HALFYEARLY, subscriptionDao.getAll().get(0).getTypeOfSub());

        subscriptionDao.deleteAll();

        receptionistController.addAccessType("subscription", "yearly", genericBillID, LocalDate.now(), customer1);

        assertEquals(LocalDate.now(), subscriptionDao.getAll().get(0).getEmission());
        assertEquals(LocalDate.now().plusMonths(12), subscriptionDao.getAll().get(0).getExpiration());
        assertEquals(customer1, subscriptionDao.getAll().get(0).getMyCustomer());
        assertEquals(TypeOfSub.YEARLY, subscriptionDao.getAll().get(0).getTypeOfSub());

        subscriptionDao.deleteAll();

        receptionistController.addAccessType("daily", "", genericBillID, LocalDate.now(), customer1);

        assertEquals(LocalDate.now(), dailyDao.getAll().get(0).getEmission());
        assertEquals(customer1, dailyDao.getAll().get(0).getMyCustomer());
    }

    @Test
    void releaseBadge(){
        Customer customer1 = new Customer("Tommaso", "Botarelli", "8926735");
        customerDao.add(customer1);

        receptionistController.releaseBadge(customer1);

        Badge badge = new Badge(customer1);
        badge.setId(0);

        assertEquals(badge, badgeDao.getFromCustomer(customer1));
    }

    @Test
    void scanBadgeForGetCustomer(){
        Customer customer1 = new Customer("Tommaso", "Botarelli", "8926735");
        customerDao.add(customer1);

        Badge badge = new Badge(customer1);
        long id = badgeDao.addBadge(badge);

        try{
            Customer customer = receptionistController.scanBadgeForGetCustomer(id);
            assertEquals(customer1, customer);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        try{
            Customer customer = receptionistController.scanBadgeForGetCustomer(9);
        }
        catch (Exception e){
            assertEquals("A Customer with this id doesn't exist", e.getMessage());
        }
    }
}