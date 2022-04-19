package businessLogic;

import dao.*;
import domainModel.*;

import java.time.LocalDate;

public class TurnstileController {
    private Turnstile thisTurnstile;
    private AccessDao accessDao;
    private BadgeDao badgeDao;
    private TypeOfAccessDao typeOfAccessDao;

    public TurnstileController(){
        accessDao = FakeAccessDao.getInstance();
        badgeDao = FakeBadgeDao.getInstance();
        typeOfAccessDao = FakeTypeOfAccessDao.getInstance();
    }

    public void addAccess(int id, int year, int month, int day){
        LocalDate date = LocalDate.of(year, month, day);
        Costumer costumer = badgeDao.searchCostumerFromId(id);
        TypeOfAccess validTypeOfAccess = typeOfAccessDao.getValidTypeOfAccessFromCostumer(costumer, date);
        if (validTypeOfAccess != null){
            accessDao.add(new Access(costumer, date, true));
            validTypeOfAccess.addAccess();
        }
        else
            accessDao.add(new Access(costumer, date, false));
    }
}
