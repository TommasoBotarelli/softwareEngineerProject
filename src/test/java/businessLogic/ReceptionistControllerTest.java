package businessLogic;

import dao.*;
import domainModel.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ReceptionistControllerTest {

    private ReceptionistController receptionistController = new ReceptionistController();

    @Test
    void addCostumer() {
        Costumer costumer1 = new Costumer("Tommaso", "Botarelli", "71638723");

        receptionistController.addCostumer("Tommaso", "Botarelli", "71638723");

        assertEquals(costumer1, FakeCostumerDao.getInstance().getAll().get(0));
    }

    @Test
    void setCurrentReceptionist(){
        Receptionist receptionist = new Receptionist("Tommaso", "Botarelli", "71638723");

        boolean firstTry = receptionistController.setCurrentReceptionist("Tommaso", "Botarelli", "71638723");

        assertFalse(firstTry);

        FakeReceptionistDao.getInstance().addReceptionist(receptionist);

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

        FakeCostumerDao.getInstance().add(costumer1);
        FakeCostumerDao.getInstance().add(costumer2);
        FakeCostumerDao.getInstance().add(costumer3);

        boolean secondDelete = receptionistController.deleteCostumer(costumer1);

        ArrayList<Costumer> costumers = new ArrayList<>();

        costumers.add(costumer2);
        costumers.add(costumer3);

        assertEquals(costumers, FakeCostumerDao.getInstance().getAll());
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

        FakeCostumerDao.getInstance().add(costumer1);
        FakeCostumerDao.getInstance().add(costumer2);
        FakeCostumerDao.getInstance().add(costumer3);

        assertEquals(allCostumers, receptionistController.visualizeAllCostumer());
    }

    @Test
    void visualizeAllTypeOfAccess() {
        LocalDate actualDate = LocalDate.of(2022, 4, 21);

        Costumer costumer1 = new Costumer("Giulio", "Cesare", "78254821");
        Costumer costumer2 = new Costumer("Cice", "Rone", "45235314");
        Costumer costumer3 = new Costumer("Giulio", "Camillo", "4214214214");
        Costumer costumer4 = new Costumer("Tommaso", "Botarelli", "8723521631");

        TypeOfAccess subscription1 = new Subscription(actualDate, TypeOfSub.PROVA, costumer1);
        TypeOfAccess subscription2 = new Subscription(actualDate, TypeOfSub.MENSILE, costumer2);
        TypeOfAccess subscription3 = new Subscription(actualDate, TypeOfSub.ANNUALE, costumer3);
        TypeOfAccess daily1 = new Daily(actualDate, costumer4);

        ArrayList<TypeOfAccess> allSubscriptions = new ArrayList<>();

        allSubscriptions.add(subscription1);
        allSubscriptions.add(subscription2);
        allSubscriptions.add(subscription3);
        allSubscriptions.add(daily1);

        FakeTypeOfAccessDao.getInstance().addWithBill(subscription1, 1);
        FakeTypeOfAccessDao.getInstance().addWithBill(subscription2, 2);
        FakeTypeOfAccessDao.getInstance().addWithBill(subscription3, 3);
        FakeTypeOfAccessDao.getInstance().addWithBill(daily1, 4);

        assertEquals(allSubscriptions, receptionistController.visualizeAllTypeOfAccess());
    }

    @Test
    void visualizeTypeOfAccessFromCostumer() {
        LocalDate actualDate = LocalDate.of(2022, 4, 21);

        Costumer costumer1 = new Costumer("Giulio", "Cesare", "78254821");
        Costumer costumer2 = new Costumer("Cice", "Rone", "45235314");
        Costumer costumer3 = new Costumer("Giulio", "Camillo", "4214214214");
        Costumer costumer4 = new Costumer("Tommaso", "Botarelli", "8723521631");

        TypeOfAccess subscription1 = new Subscription(actualDate, TypeOfSub.PROVA, costumer1);
        TypeOfAccess subscription2 = new Subscription(actualDate, TypeOfSub.MENSILE, costumer2);
        TypeOfAccess subscription3 = new Subscription(actualDate, TypeOfSub.ANNUALE, costumer3);
        TypeOfAccess daily1 = new Daily(actualDate, costumer4);

        ArrayList<TypeOfAccess> subscriptionOfCostumer1 = new ArrayList<>();

        subscriptionOfCostumer1.add(subscription1);

        FakeTypeOfAccessDao.getInstance().addWithBill(subscription1, 1);
        FakeTypeOfAccessDao.getInstance().addWithBill(subscription2, 2);
        FakeTypeOfAccessDao.getInstance().addWithBill(subscription3, 3);
        FakeTypeOfAccessDao.getInstance().addWithBill(daily1, 4);

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

        FakeCostumerDao.getInstance().add(costumer1);
        FakeCostumerDao.getInstance().add(costumer2);
        FakeCostumerDao.getInstance().add(costumer3);
        FakeCostumerDao.getInstance().add(costumer4);
        FakeCostumerDao.getInstance().add(costumer4bis);

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

        FakeCostumerDao.getInstance().add(costumer1);
        FakeCostumerDao.getInstance().add(costumer2);
        FakeCostumerDao.getInstance().add(costumer3);
        FakeCostumerDao.getInstance().add(costumer4);
        FakeCostumerDao.getInstance().add(costumer4bis);

        assertNull(receptionistController.selectCostumer("Gianluca", "Rossi", "65725181"));
        assertEquals(costumer4bis, receptionistController.selectCostumer("Tommaso", "Botarelli", "452341324214"));
    }

    @Test
    void addAccessForCostumerFromBadge() {
        LocalDate actualDate = LocalDate.now();

        Costumer costumer1 = new Costumer("Giulio", "Cesare", "78254821");
        Costumer costumer2 = new Costumer("Cice", "Rone", "45235314");

        FakeCostumerDao.getInstance().add(costumer1);
        FakeCostumerDao.getInstance().add(costumer2);

        Badge badge1 = new Badge(costumer1);
        Badge badge2 = new Badge(costumer2);

        long id1 = FakeBadgeDao.getInstance().addBadge(badge1);
        long id2 = id1 + 1;

        Subscription subscriptionC1 = new Subscription(actualDate, TypeOfSub.PROVA, costumer1);

        FakeTypeOfAccessDao.getInstance().addWithBill(subscriptionC1, 0);

        try{
            boolean response = receptionistController.addAccessForCostumerFromBadge(id1, actualDate.plusDays(1));
            assertTrue(response);
            assertEquals(actualDate.plusDays(1), FakeAccessDao.getInstance().getFromCostumer(costumer1).get(0).getAccessTime());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        try{
            boolean response = receptionistController.addAccessForCostumerFromBadge(id1, actualDate.plusMonths(1));
            assertFalse(response);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        try{
            boolean response = receptionistController.addAccessForCostumerFromBadge(id2, actualDate.plusDays(1));
        }
        catch(Exception e){
            assertEquals("A costumer with this id doesn't exist", e.getMessage());
        }
    }

    @Test
    void addAccessForCostumer() {
    }

    @Test
    void addBill() {
    }

    @Test
    void addTypeOfAccess() {
    }
}