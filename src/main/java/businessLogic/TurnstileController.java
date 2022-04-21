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

    public void setTurnstile(Turnstile turnstile) {
        this.thisTurnstile = turnstile;
    }

    public boolean addAccess(int id, int year, int month, int day){
        if(thisTurnstile.isOperative()) {
            LocalDate date = LocalDate.of(year, month, day);
            Costumer costumer = badgeDao.searchCostumerFromId(id);
            TypeOfAccess validTypeOfAccess = typeOfAccessDao.getValidTypeOfAccessFromCostumer(costumer, date);
            if (validTypeOfAccess != null) {
                accessDao.add(new Access(costumer, date));
                validTypeOfAccess.addAccess();
                thisTurnstile.setCanAccess(true);
                return true;
            } else {
                accessDao.add(new Access(costumer, date));
                return false;
            }
        }
        else
            return false;
    }
}
