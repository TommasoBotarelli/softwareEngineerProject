package businessLogic;

import dao.*;
import domainModel.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class TurnstileController {
    Turnstile thisTurnstile;
    AccessDao accessDao;
    BadgeDao badgeDao;
    TypeOfAccessDao typeOfAccessDao;

    TurnstileController(boolean isOpen, boolean isOperative){
        this.thisTurnstile = new Turnstile(isOperative, isOpen);
        accessDao = new FakeAccessDao();
        badgeDao = new FakeBadgeDao();
        typeOfAccessDao = new FakeTypeOfAccessDao();
    }

    public void addAccess(int id, int year, int month, int day){
        LocalDate date = LocalDate.of(year, month, day);
        Costumer costumer = badgeDao.searchCostumerFromId(id);
        ArrayList<TypeOfAccess> typeOfAccesses = typeOfAccessDao.getFromCostumer(costumer);
        TypeOfAccess validTypeOfAccess = typeOfAccessDao.getValidTypeOfAccessFromCostumer(costumer, date);
        if (validTypeOfAccess != null){
            accessDao.add(new Access(costumer, date, true));
            validTypeOfAccess.addAccess();
        }
        else
            accessDao.add(new Access(costumer, date, false));
    }
}
