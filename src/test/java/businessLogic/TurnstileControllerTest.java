package businessLogic;

import dao.concreteDao.FakeAccessDao;
import dao.concreteDao.FakeBillDao;
import dao.factoryClass.DaoFactory;
import dao.interfaceClass.BadgeDao;
import dao.interfaceClass.CostumerDao;
import dao.interfaceClass.TypeOfAccessDao;
import domainModel.Badge;
import domainModel.Costumer;
import domainModel.Daily;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class TurnstileControllerTest {

    private TurnstileController turnstileController = new TurnstileController();
    private static CostumerDao costumerDao;
    private static TypeOfAccessDao typeOfAccessDao;
    private static BadgeDao badgeDao;

    @BeforeAll
    static void beforeAll(){
        costumerDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getCostumerDao();
        typeOfAccessDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getTypeOfAccessDao();
        badgeDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getBadgeDao();
    }

    @BeforeEach
    void setUp(){
        costumerDao.deleteAll();
        typeOfAccessDao.deleteAll();
        FakeBillDao.getInstance().deleteAll();
        FakeAccessDao.getInstance().deleteAll();
    }

    @Test
    void scanBadge(){
        Costumer costumer = new Costumer("Tommaso", "Botarelli", "6757823122");
        costumerDao.add(costumer);

        Daily daily = new Daily(LocalDate.now(), costumer);
        typeOfAccessDao.addWithBill(daily, 1);

        Badge badge = new Badge(costumer);
        long id = badgeDao.addBadge(badge);

        try{
            boolean result1 = turnstileController.scanBadge(id, LocalDateTime.now());
            assertTrue(result1);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        try{
            boolean result2 = turnstileController.scanBadge(id, LocalDateTime.now());
            assertFalse(result2);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        try{
            boolean result3 = turnstileController.scanBadge(5, LocalDateTime.now());
        }
        catch (Exception e){
            assertEquals("A costumer with this id doesn't exist", e.getMessage());
        }
    }

}