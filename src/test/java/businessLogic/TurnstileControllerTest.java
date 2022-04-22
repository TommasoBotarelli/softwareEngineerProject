package businessLogic;

import dao.*;
import domainModel.Badge;
import domainModel.Costumer;
import domainModel.Daily;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TurnstileControllerTest {

    private TurnstileController turnstileController = new TurnstileController();

    @BeforeEach
    void setUp(){
        FakeCostumerDao.getInstance().deleteAll();
        FakeTypeOfAccessDao.getInstance().deleteAll();
        FakeBillDao.getInstance().deleteAll();
        FakeAccessDao.getInstance().deleteAll();
    }

    @Test
    void scanBadge(){
        Costumer costumer = new Costumer("Tommaso", "Botarelli", "6757823122");
        FakeCostumerDao.getInstance().add(costumer);

        Daily daily = new Daily(LocalDate.now(), costumer);
        FakeTypeOfAccessDao.getInstance().addWithBill(daily, 1);

        Badge badge = new Badge(costumer);
        long id = FakeBadgeDao.getInstance().addBadge(badge);

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