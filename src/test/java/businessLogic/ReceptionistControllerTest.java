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
    private static CostumerDao costumerDao;
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
        costumerDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getCostumerDao();
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
        costumerDao.deleteAll();
        FakeBillDao.getInstance().deleteAll();
        FakeAccessDao.getInstance().deleteAll();
    }

    @Test
    void addCostumer() {
        Costumer costumer1 = new Costumer("Tommaso", "Botarelli", "71638723");

        receptionistController.addCostumer("Tommaso", "Botarelli", "71638723");

        assertEquals(costumer1, costumerDao.getAll().get(0));
    }

    @Test
    void setCurrentReceptionist(){
        Receptionist receptionist = new Receptionist("Tommaso", "Botarelli", "71638723");

        receptionistController.setCurrentReceptionist(receptionist);

        assertEquals(receptionist, receptionistController.getCurrentReceptionist());
    }

    @Test
    void deleteCostumer() {
        Costumer costumer1 = new Costumer("Giulio", "Cesare", "78254821");
        Costumer costumer2 = new Costumer("Cice", "Rone", "45235314");
        Costumer costumer3 = new Costumer("Giulio", "Camillo", "4214214214");

        boolean firstDelete = receptionistController.deleteCostumer(costumer1);

        assertFalse(firstDelete);

        costumerDao.add(costumer1);
        costumerDao.add(costumer2);
        costumerDao.add(costumer3);

        boolean secondDelete = receptionistController.deleteCostumer(costumer1);

        ArrayList<Costumer> costumers = new ArrayList<>();

        costumers.add(costumer2);
        costumers.add(costumer3);

        assertEquals(costumers, costumerDao.getAll());
    }

    @Test
    void visualizeAllCostumer() {
        ArrayList<Costumer> allCostumers = new ArrayList<>();

        Costumer costumer1 = new Costumer("Giulio", "Cesare", "78254821");
        Costumer costumer2 = new Costumer("Cice", "Rone", "45235314");
        Costumer costumer3 = new Costumer("Giulio", "Camillo", "4214214214");

        allCostumers.add(costumer1);
        allCostumers.add(costumer2);
        allCostumers.add(costumer3);

        costumerDao.add(costumer1);
        costumerDao.add(costumer2);
        costumerDao.add(costumer3);

        assertEquals(allCostumers, receptionistController.visualizeAllCostumer());
    }

    @Test
    void visualizeAllTypeOfAccess() {
        LocalDate actualDate = LocalDate.of(2022, 4, 21);

        Costumer costumer1 = new Costumer("Giulio", "Cesare", "78254821");
        Costumer costumer2 = new Costumer("Cice", "Rone", "45235314");
        Costumer costumer3 = new Costumer("Giulio", "Camillo", "4214214214");
        Costumer costumer4 = new Costumer("Tommaso", "Botarelli", "8723521631");

        Bill genericBill = new Bill(20f, LocalDateTime.now());

        TrialSubscription trialSubscription1 = new TrialSubscription(costumer1, LocalDate.now());
        Subscription subscription2 = new Subscription(LocalDate.now().plusMonths(1), TypeOfSub.MONTHLY, costumer2, genericBill);
        Daily daily1 = new Daily(LocalDate.now().plusMonths(3), costumer3, genericBill);
        Daily daily2 = new Daily(LocalDate.now().plusMonths(3), costumer4, genericBill);

        ArrayList<AccessType> allSubscriptions = new ArrayList<>();

        allSubscriptions.add(trialSubscription1);
        allSubscriptions.add(subscription2);
        allSubscriptions.add(daily1);
        allSubscriptions.add(daily2);

        trialSubscriptionDao.add(trialSubscription1);
        subscriptionDao.add(subscription2);
        dailyDao.addDaily(daily1);
        dailyDao.addDaily(daily2);

        assertEquals(allSubscriptions, receptionistController.getAllAccessType());
    }

    @Test
    void visualizeTypeOfAccessFromCostumer() {
        LocalDate actualDate = LocalDate.of(2022, 4, 21);

        Costumer costumer1 = new Costumer("Giulio", "Cesare", "78254821");
        Costumer costumer2 = new Costumer("Cice", "Rone", "45235314");
        Costumer costumer3 = new Costumer("Giulio", "Camillo", "4214214214");
        Costumer costumer4 = new Costumer("Tommaso", "Botarelli", "8723521631");

        Bill genericBill = new Bill(20f, LocalDateTime.now());

        TrialSubscription trialSubscription1 = new TrialSubscription(costumer1, LocalDate.now());
        Subscription subscription2 = new Subscription(LocalDate.now().plusMonths(1), TypeOfSub.MONTHLY, costumer2, genericBill);
        Daily daily1 = new Daily(LocalDate.now().plusMonths(3), costumer3, genericBill);
        Daily daily2 = new Daily(LocalDate.now().plusMonths(3), costumer4, genericBill);

        ArrayList<AccessType> subscriptionOfCostumer1 = new ArrayList<>();

        subscriptionOfCostumer1.add(trialSubscription1);

        trialSubscriptionDao.add(trialSubscription1);
        subscriptionDao.add(subscription2);
        dailyDao.addDaily(daily1);
        dailyDao.addDaily(daily2);

        assertEquals(subscriptionOfCostumer1, receptionistController.getAllAccessTypeFromCostumer(costumer1));
    }

    @Test
    void findCostumer() {
        Costumer costumer1 = new Costumer("Giulio", "Cesare", "78254821");
        Costumer costumer2 = new Costumer("Cice", "Rone", "45235314");
        Costumer costumer3 = new Costumer("Giulio", "Camillo", "4214214214");
        Costumer costumer4 = new Costumer("Tommaso", "Botarelli", "8723521631");
        Costumer costumer4bis = new Costumer("Tommaso", "Botarelli", "452341324214");

        ArrayList<Costumer> costumersWithSameNameSurname = new ArrayList<>();
        costumersWithSameNameSurname.add(costumer4);
        costumersWithSameNameSurname.add(costumer4bis);

        costumerDao.add(costumer1);
        costumerDao.add(costumer2);
        costumerDao.add(costumer3);
        costumerDao.add(costumer4);
        costumerDao.add(costumer4bis);

        assertEquals(0, receptionistController.findCostumer("Giulio", "Giuli").size());
        assertEquals(costumersWithSameNameSurname, receptionistController.findCostumer("Tommaso", "Botarelli"));
    }

    @Test
    void selectCostumer() {
        Costumer costumer1 = new Costumer("Giulio", "Cesare", "78254821");
        Costumer costumer2 = new Costumer("Cice", "Rone", "45235314");
        Costumer costumer3 = new Costumer("Giulio", "Camillo", "4214214214");
        Costumer costumer4 = new Costumer("Tommaso", "Botarelli", "8723521631");
        Costumer costumer4bis = new Costumer("Tommaso", "Botarelli", "452341324214");

        costumerDao.add(costumer1);
        costumerDao.add(costumer2);
        costumerDao.add(costumer3);
        costumerDao.add(costumer4);
        costumerDao.add(costumer4bis);

        assertNull(receptionistController.selectCostumer("Gianluca", "Rossi", "65725181"));
        assertEquals(costumer4bis, receptionistController.selectCostumer("Tommaso", "Botarelli", "452341324214"));
    }

    @Test
    void addAccessForCostumerFromBadge() {
        Costumer costumer1 = new Costumer("Giulio", "Cesare", "78254821");
        Costumer costumer2 = new Costumer("Cice", "Rone", "45235314");

        costumerDao.add(costumer1);
        costumerDao.add(costumer2);

        Badge badge1 = new Badge(costumer1);
        Badge badge2 = new Badge(costumer2);

        long id1 = badgeDao.addBadge(badge1);
        //Il badge di costumer 2 non esiste nel sistema.
        long id2 = id1 + 1;

        Bill genericBill = new Bill(20f, LocalDateTime.now());

        TrialSubscription trialSubscription1 = new TrialSubscription(costumer1, LocalDate.now());
        Subscription subscription2 = new Subscription(LocalDate.now(), TypeOfSub.MONTHLY, costumer2, genericBill);
        Daily daily1 = new Daily(LocalDate.now().plusMonths(2), costumer1, genericBill);
        Daily daily2 = new Daily(LocalDate.now().plusMonths(2), costumer2, genericBill);

        trialSubscriptionDao.add(trialSubscription1);
        subscriptionDao.add(subscription2);
        dailyDao.addDaily(daily1);
        dailyDao.addDaily(daily2);

        try{
            boolean response = receptionistController.addAccessForCostumerFromBadge(id1, LocalDateTime.now().plusDays(1));
            assertTrue(response);
            assertEquals(LocalDateTime.now().plusDays(1), accessDao.getFromCostumer(costumer1).get(0).getAccessTime());
        }
        catch (Exception e){
            System.out.println(e.getMessage() + " non dovrebbe essere stampato");
        }

        try{
            boolean response = receptionistController.addAccessForCostumerFromBadge(id1, LocalDateTime.now().plusMonths(1));
            assertFalse(response);
        }
        catch (Exception e){
            System.out.println(e.getMessage() + " non dovrebbe essere stampato");
        }

        try{
            receptionistController.addAccessForCostumerFromBadge(id2, LocalDateTime.now().plusDays(1));
        }
        catch(Exception e){
            assertEquals("A costumer with this id doesn't exist", e.getMessage());
        }

        id2 = badgeDao.addBadge(new Badge(costumer2));

        try{
            boolean response1 = receptionistController.addAccessForCostumerFromBadge(id2, LocalDateTime.now().plusMonths(2));
            assertTrue(response1);
        }
        catch (Exception e){
            System.out.println(e.getMessage() + " non dovrebbe essere stampato");
        }

        try{
            boolean response2 = receptionistController.addAccessForCostumerFromBadge(id2, LocalDateTime.now().plusMonths(10));
            assertFalse(response2);
        }
        catch (Exception e){
            System.out.println(e.getMessage() + " non dovrebbe essere stampato");
        }
    }

    @Test
    void addAccessForCostumer() {
        Costumer costumer1 = new Costumer("Giulio", "Cesare", "78254821");
        Costumer costumer2 = new Costumer("Cice", "Rone", "45235314");

        costumerDao.add(costumer1);
        costumerDao.add(costumer2);

        Bill genericBill = new Bill(20f, LocalDateTime.now());

        TrialSubscription trialSubscription1 = new TrialSubscription(costumer1, LocalDate.now());
        Subscription subscription2 = new Subscription(LocalDate.now(), TypeOfSub.MONTHLY, costumer2, genericBill);
        Daily daily1 = new Daily(LocalDate.now().plusMonths(2), costumer1, genericBill);
        Daily daily2 = new Daily(LocalDate.now().plusMonths(2), costumer2, genericBill);

        trialSubscriptionDao.add(trialSubscription1);
        subscriptionDao.add(subscription2);
        dailyDao.addDaily(daily1);
        dailyDao.addDaily(daily2);

        boolean response1 = receptionistController.addAccessForCostumer(costumer1, LocalDateTime.now().plusDays(1));
        assertTrue(response1);
        assertEquals(LocalDateTime.now().plusDays(1), accessDao.getFromCostumer(costumer1).get(0).getAccessTime());

        boolean response2 = receptionistController.addAccessForCostumer(costumer2, LocalDateTime.now().plusMonths(1));
        assertFalse(response2);

        boolean response3 = receptionistController.addAccessForCostumer(costumer1, LocalDateTime.now().plusDays(14));
        assertTrue(response3);

        boolean response4 = receptionistController.addAccessForCostumer(costumer1, LocalDateTime.now());
        assertTrue(response4);
    }

    @Test
    void addBill() {
        LocalDateTime time1 = LocalDateTime.now();
        receptionistController.addBill(200.0f, time1);
        assertEquals(time1, billDao.getAll().get(0).getDateTime());
    }

    @Test
    void addTypeOfAccess() {
        Costumer costumer1 = new Costumer("Tommaso", "Botarelli", "8926735");
        costumerDao.add(costumer1);

        receptionistController.addAccessType("subscription", "prova", 0, LocalDate.now(), costumer1);

        assertEquals(LocalDate.now(), trialSubscriptionDao.getAll().get(0).getEmission());
        assertEquals(LocalDate.now().plusDays(14), trialSubscriptionDao.getAll().get(0).getExpiration());
        assertEquals(costumer1, trialSubscriptionDao.getAll().get(0).getCostumerTarget());

        receptionistController.addAccessType("subscription", "monthly", 0, LocalDate.now(), costumer1);

        assertEquals(LocalDate.now(), subscriptionDao.getAll().get(0).getEmission());
        assertEquals(LocalDate.now().plusMonths(1), subscriptionDao.getAll().get(0).getExpiration());
        assertEquals(costumer1, subscriptionDao.getAll().get(0).getMyCostumer());
        assertEquals(TypeOfSub.MONTHLY, subscriptionDao.getAll().get(0).getTypeOfSub());

        subscriptionDao.deleteAll();

        receptionistController.addAccessType("subscription", "quarterly", 0, LocalDate.now(), costumer1);

        assertEquals(LocalDate.now(), subscriptionDao.getAll().get(0).getEmission());
        assertEquals(LocalDate.now().plusMonths(3), subscriptionDao.getAll().get(0).getExpiration());
        assertEquals(costumer1, subscriptionDao.getAll().get(0).getMyCostumer());
        assertEquals(TypeOfSub.QUARTERLY, subscriptionDao.getAll().get(0).getTypeOfSub());

        subscriptionDao.deleteAll();

        receptionistController.addAccessType("subscription", "halfyearly", 0, LocalDate.now(), costumer1);

        assertEquals(LocalDate.now(), subscriptionDao.getAll().get(0).getEmission());
        assertEquals(LocalDate.now().plusMonths(6), subscriptionDao.getAll().get(0).getExpiration());
        assertEquals(costumer1, subscriptionDao.getAll().get(0).getMyCostumer());
        assertEquals(TypeOfSub.HALFYEARLY, subscriptionDao.getAll().get(0).getTypeOfSub());

        subscriptionDao.deleteAll();

        receptionistController.addAccessType("subscription", "annuale", 0, LocalDate.now(), costumer1);

        assertEquals(LocalDate.now(), subscriptionDao.getAll().get(0).getEmission());
        assertEquals(LocalDate.now().plusMonths(12), subscriptionDao.getAll().get(0).getExpiration());
        assertEquals(costumer1, subscriptionDao.getAll().get(0).getMyCostumer());
        assertEquals(TypeOfSub.YEARLY, subscriptionDao.getAll().get(0).getTypeOfSub());

        subscriptionDao.deleteAll();

        receptionistController.addAccessType("daily", "", 0, LocalDate.now(), costumer1);

        assertEquals(LocalDate.now(), dailyDao.getAll().get(0).getEmission());
        assertEquals(costumer1, dailyDao.getAll().get(0).getMyCostumer());
    }

    @Test
    void releaseBadge(){
        Costumer costumer1 = new Costumer("Tommaso", "Botarelli", "8926735");
        costumerDao.add(costumer1);

        receptionistController.releaseBadge(costumer1);

        Badge badge = new Badge(costumer1);
        badge.setId(0);

        assertEquals(badge, badgeDao.getFromCostumer(costumer1));
    }

    @Test
    void scanBadgeForGetCostumer(){
        Costumer costumer1 = new Costumer("Tommaso", "Botarelli", "8926735");
        costumerDao.add(costumer1);

        Badge badge = new Badge(costumer1);
        long id = badgeDao.addBadge(badge);

        try{
            Costumer costumer = receptionistController.scanBadgeForGetCostumer(id);
            assertEquals(costumer1, costumer);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        try{
            Costumer costumer = receptionistController.scanBadgeForGetCostumer(9);
        }
        catch (Exception e){
            assertEquals("A costumer with this id doesn't exist", e.getMessage());
        }
    }
}