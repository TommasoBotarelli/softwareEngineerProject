package businessLogic;

import dao.*;
import domainModel.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

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

    private boolean isTypeOfAccessValid(TypeOfAccess sub, LocalDateTime localDateTime){
        return (sub.getExpiration().isAfter(localDateTime.toLocalDate()) && sub.getEmission().isBefore(localDateTime.toLocalDate()))
                || (sub.getExpiration().isEqual(localDateTime.toLocalDate())) || (sub.getEmission().isEqual(localDateTime.toLocalDate()));
    }

    public boolean addAccessForCostumerFromBadge(long id, LocalDateTime dateTime) throws Exception{
        Costumer costumer = badgeDao.searchCostumerFromId(id);
        if (costumer == null)
            throw new Exception("A costumer with this id doesn't exist");

        ArrayList<TypeOfAccess> typeOfAccesesOfCostumer = typeOfAccessDao.getFromCostumer(costumer);

        if (!typeOfAccesesOfCostumer.isEmpty()){
            ArrayList<TypeOfAccess> subOfCostumer = this.getSubscriptionsOfCostumer(costumer);
            for (TypeOfAccess t : subOfCostumer){
                if (this.isTypeOfAccessValid(t, dateTime)){
                    t.addAccess();
                    Access newAccess = new Access(costumer, dateTime);
                    accessDao.add(newAccess);
                    return true;
                }
            }
            ArrayList<TypeOfAccess> dailyOfSub = this.getDailyOfCostumer(costumer);
            for (TypeOfAccess t : subOfCostumer){
                if (this.isTypeOfAccessValid(t, dateTime)){
                    t.addAccess();
                    Access newAccess = new Access(costumer, dateTime);
                    accessDao.add(newAccess);
                    return true;
                }
            }
        }
        accessDao.add(new Access(costumer, dateTime));
        return false;
    }

    private ArrayList<TypeOfAccess> getSubscriptionsOfCostumer(Costumer costumer){
        ArrayList<TypeOfAccess> onlySubscriptions = typeOfAccessDao.getFromCostumer(costumer);

        onlySubscriptions.removeIf(t -> t instanceof Daily);

        return onlySubscriptions;
    }

    private ArrayList<TypeOfAccess> getDailyOfCostumer(Costumer costumer){
        ArrayList<TypeOfAccess> onlySubscriptions = typeOfAccessDao.getFromCostumer(costumer);

        onlySubscriptions.removeIf(t -> t instanceof Subscription);

        return onlySubscriptions;
    }
}
