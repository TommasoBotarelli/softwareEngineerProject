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
    private static TypeOfAccessDao typeOfAccessDao;
    private static BadgeDao badgeDao;
    private static AccessDao accessDao;
    private static BillDao billDao;

    @BeforeAll
    static void beforeAll(){
        costumerDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getCostumerDao();
        receptionistDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getReceptionistDao();
        typeOfAccessDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getTypeOfAccessDao();
        badgeDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getBadgeDao();
        accessDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getAccessDao();
        billDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getBillDao();
    }

    @BeforeEach
    void setUp(){
        costumerDao.deleteAll();
        typeOfAccessDao.deleteAll();
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

        boolean firstTry = receptionistController.setCurrentReceptionist("Tommaso", "Botarelli", "71638723");

        assertFalse(firstTry);

        receptionistDao.addReceptionist(receptionist);

        boolean secondTry = receptionistController.setCurrentReceptionist("Tommaso", "Botarelli", "71638723");

        assertTrue(secondTry);
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

        TypeOfAccess subscription1 = new Subscription(actualDate, TypeOfSub.TRIAL, costumer1);
        TypeOfAccess subscription2 = new Subscription(actualDate, TypeOfSub.MONTHLY, costumer2);
        TypeOfAccess subscription3 = new Subscription(actualDate, TypeOfSub.YEARLY, costumer3);
        TypeOfAccess daily1 = new Daily(actualDate, costumer4);

        ArrayList<TypeOfAccess> allSubscriptions = new ArrayList<>();

        allSubscriptions.add(subscription1);
        allSubscriptions.add(subscription2);
        allSubscriptions.add(subscription3);
        allSubscriptions.add(daily1);

        typeOfAccessDao.addWithBill(subscription1, 1);
        typeOfAccessDao.addWithBill(subscription2, 2);
        typeOfAccessDao.addWithBill(subscription3, 3);
        typeOfAccessDao.addWithBill(daily1, 4);

        assertEquals(allSubscriptions, receptionistController.visualizeAllTypeOfAccess());
    }

    @Test
    void visualizeTypeOfAccessFromCostumer() {
        LocalDate actualDate = LocalDate.of(2022, 4, 21);

        Costumer costumer1 = new Costumer("Giulio", "Cesare", "78254821");
        Costumer costumer2 = new Costumer("Cice", "Rone", "45235314");
        Costumer costumer3 = new Costumer("Giulio", "Camillo", "4214214214");
        Costumer costumer4 = new Costumer("Tommaso", "Botarelli", "8723521631");

        TypeOfAccess subscription1 = new Subscription(actualDate, TypeOfSub.TRIAL, costumer1);
        TypeOfAccess subscription2 = new Subscription(actualDate, TypeOfSub.MONTHLY, costumer2);
        TypeOfAccess subscription3 = new Subscription(actualDate, TypeOfSub.YEARLY, costumer3);
        TypeOfAccess daily1 = new Daily(actualDate, costumer4);

        ArrayList<TypeOfAccess> subscriptionOfCostumer1 = new ArrayList<>();

        subscriptionOfCostumer1.add(subscription1);

        typeOfAccessDao.addWithBill(subscription1, 1);
        typeOfAccessDao.addWithBill(subscription2, 2);
        typeOfAccessDao.addWithBill(subscription3, 3);
        typeOfAccessDao.addWithBill(daily1, 4);

        assertEquals(subscriptionOfCostumer1, receptionistController.visualizeTypeOfAccessFromCostumer(costumer1));
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
        LocalDateTime actualDateTime = LocalDateTime.now();

        Costumer costumer1 = new Costumer("Giulio", "Cesare", "78254821");
        Costumer costumer2 = new Costumer("Cice", "Rone", "45235314");

        costumerDao.add(costumer1);
        costumerDao.add(costumer2);

        Badge badge1 = new Badge(costumer1);
        Badge badge2 = new Badge(costumer2);

        long id1 = badgeDao.addBadge(badge1);
        long id2 = id1 + 1;

        Subscription subscriptionC1 = new Subscription(actualDateTime.toLocalDate(), TypeOfSub.TRIAL, costumer1);
        Daily dailyC1 = new Daily(actualDateTime.toLocalDate().plusMonths(10), costumer1);

        subscriptionC1.setExpiration(actualDateTime.toLocalDate().plusDays(14));
        dailyC1.setExpiration(actualDateTime.toLocalDate().plusMonths(10));

        typeOfAccessDao.addWithBill(subscriptionC1, 0);
        typeOfAccessDao.addWithBill(dailyC1, 1);

        try{
            boolean response = receptionistController.addAccessForCostumerFromBadge(id1, actualDateTime.plusDays(1));
            assertTrue(response);
            assertEquals(actualDateTime.plusDays(1), accessDao.getFromCostumer(costumer1).get(0).getAccessTime());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        try{
            boolean response = receptionistController.addAccessForCostumerFromBadge(id1, actualDateTime.plusMonths(1));
            assertFalse(response);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        try{
            receptionistController.addAccessForCostumerFromBadge(id2, actualDateTime.plusDays(1));
        }
        catch(Exception e){
            assertEquals("A costumer with this id doesn't exist", e.getMessage());
        }
        try{
            boolean response1 = receptionistController.addAccessForCostumerFromBadge(id2, actualDateTime.plusMonths(10));
            assertTrue(response1);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        try{
            boolean response2 = receptionistController.addAccessForCostumerFromBadge(id2, actualDateTime.plusMonths(10));
            assertFalse(response2);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void addAccessForCostumer() {
        LocalDateTime actualDateTime = LocalDateTime.now();

        Costumer costumer1 = new Costumer("Giulio", "Cesare", "78254821");
        Costumer costumer2 = new Costumer("Cice", "Rone", "45235314");

        costumerDao.add(costumer1);
        costumerDao.add(costumer2);

        Subscription subscriptionC1 = new Subscription(actualDateTime.toLocalDate(), TypeOfSub.TRIAL, costumer1);
        subscriptionC1.setExpiration(actualDateTime.toLocalDate().plusDays(14));

        typeOfAccessDao.addWithBill(subscriptionC1, 0);

        boolean response1 = receptionistController.addAccessForCostumer(costumer1, actualDateTime.plusDays(1));
        assertTrue(response1);
        assertEquals(actualDateTime.plusDays(1), accessDao.getFromCostumer(costumer1).get(0).getAccessTime());

        boolean response2 = receptionistController.addAccessForCostumer(costumer2, actualDateTime.plusMonths(1));
        assertFalse(response2);

        boolean response3 = receptionistController.addAccessForCostumer(costumer1, actualDateTime.plusDays(14));
        assertTrue(response3);

        boolean response4 = receptionistController.addAccessForCostumer(costumer1, actualDateTime);
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

        receptionistController.addTypeOfAccess("subscription", "prova", 0, LocalDate.now(), costumer1);

        assertEquals(LocalDate.now(), typeOfAccessDao.getAll().get(0).getEmission());
        assertEquals(LocalDate.now().plusDays(14), typeOfAccessDao.getAll().get(0).getExpiration());
        assertEquals(costumer1, typeOfAccessDao.getAll().get(0).getCostumer());
        assertEquals(TypeOfSub.TRIAL, ((Subscription)typeOfAccessDao.getAll().get(0)).getType());

        typeOfAccessDao.deleteAll();

        receptionistController.addTypeOfAccess("subscription", "mensile", 0, LocalDate.now(), costumer1);

        assertEquals(LocalDate.now(), typeOfAccessDao.getAll().get(0).getEmission());
        assertEquals(LocalDate.now().plusMonths(1), typeOfAccessDao.getAll().get(0).getExpiration());
        assertEquals(costumer1, typeOfAccessDao.getAll().get(0).getCostumer());
        assertEquals(TypeOfSub.MONTHLY, ((Subscription)typeOfAccessDao.getAll().get(0)).getType());

        typeOfAccessDao.deleteAll();

        receptionistController.addTypeOfAccess("subscription", "trimestrale", 0, LocalDate.now(), costumer1);

        assertEquals(LocalDate.now(), typeOfAccessDao.getAll().get(0).getEmission());
        assertEquals(LocalDate.now().plusMonths(3), typeOfAccessDao.getAll().get(0).getExpiration());
        assertEquals(costumer1, typeOfAccessDao.getAll().get(0).getCostumer());
        assertEquals(TypeOfSub.QUARTERLY, ((Subscription)typeOfAccessDao.getAll().get(0)).getType());

        typeOfAccessDao.deleteAll();

        receptionistController.addTypeOfAccess("subscription", "semestrale", 0, LocalDate.now(), costumer1);

        assertEquals(LocalDate.now(), typeOfAccessDao.getAll().get(0).getEmission());
        assertEquals(LocalDate.now().plusMonths(6), typeOfAccessDao.getAll().get(0).getExpiration());
        assertEquals(costumer1, typeOfAccessDao.getAll().get(0).getCostumer());
        assertEquals(TypeOfSub.HALFYEARLY, ((Subscription)typeOfAccessDao.getAll().get(0)).getType());

        typeOfAccessDao.deleteAll();

        receptionistController.addTypeOfAccess("subscription", "annuale", 0, LocalDate.now(), costumer1);

        assertEquals(LocalDate.now(), typeOfAccessDao.getAll().get(0).getEmission());
        assertEquals(LocalDate.now().plusMonths(12), typeOfAccessDao.getAll().get(0).getExpiration());
        assertEquals(costumer1, typeOfAccessDao.getAll().get(0).getCostumer());
        assertEquals(TypeOfSub.YEARLY, ((Subscription)typeOfAccessDao.getAll().get(0)).getType());

        typeOfAccessDao.deleteAll();

        receptionistController.addTypeOfAccess("daily", "", 0, LocalDate.now(), costumer1);

        assertEquals(LocalDate.now(), typeOfAccessDao.getAll().get(0).getEmission());
        assertEquals(LocalDate.now(), typeOfAccessDao.getAll().get(0).getExpiration());
        assertEquals(costumer1, typeOfAccessDao.getAll().get(0).getCostumer());
        assertTrue(typeOfAccessDao.getAll().get(0) instanceof Daily);
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