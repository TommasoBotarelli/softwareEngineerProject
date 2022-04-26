package businessLogic;

import dao.concreteDao.FakeAccessDao;
import dao.concreteDao.FakeBadgeDao;
import dao.concreteDao.FakeTurnstileDao;
import dao.concreteDao.FakeTypeOfAccessDao;
import dao.interfaceClass.AccessDao;
import dao.interfaceClass.BadgeDao;
import dao.interfaceClass.TurnstileDao;
import dao.interfaceClass.TypeOfAccessDao;
import domainModel.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TurnstileController {
    private AccessDao accessDao;
    private BadgeDao badgeDao;
    private TypeOfAccessDao typeOfAccessDao;
    private TurnstileDao turnstileDao;

    public TurnstileController(){
        accessDao = FakeAccessDao.getInstance();
        badgeDao = FakeBadgeDao.getInstance();
        typeOfAccessDao = FakeTypeOfAccessDao.getInstance();
        turnstileDao = FakeTurnstileDao.getInstance();
    }

    private boolean isTypeOfAccessValid(TypeOfAccess sub, LocalDateTime localDateTime){
        if (sub instanceof Daily){
            return ((Daily) sub).getValidity() && sub.getEmission().isEqual(localDateTime.toLocalDate());
        }
        else{
            return (sub.getExpiration().isAfter(localDateTime.toLocalDate()) && sub.getEmission().isBefore(localDateTime.toLocalDate()))
                    || (sub.getExpiration().isEqual(localDateTime.toLocalDate())) || (sub.getEmission().isEqual(localDateTime.toLocalDate()));
        }
    }

    private void openEntryTurnstile(){
        turnstileDao.getEntryTurnstile().setCanAccess(true);
    }

    /*
    This method closeEntryTurnstile will be called automatically after the transit of a costumer.
     */

    public void closeEntryTurnstile(){
        turnstileDao.getEntryTurnstile().setCanAccess(false);
    }

    /*
    The method scanBadge is to intend like a thread always running, or can be call if a button is pressed.
     */

    public boolean scanBadge(long id, LocalDateTime dateTime) throws Exception{
        boolean canAccess = this.addAccessForCostumerFromBadge(id, dateTime);
        if (canAccess) {
            this.openEntryTurnstile();
        }
        return canAccess;
    }

    private boolean addAccessForCostumerFromBadge(long id, LocalDateTime dateTime) throws Exception{

        Costumer costumer = badgeDao.searchCostumerFromId(id);
        if (costumer == null)
            throw new Exception("A costumer with this id doesn't exist");

        ArrayList<TypeOfAccess> typeOfAccesesOfCostumer = typeOfAccessDao.getFromCostumer(costumer);

        if (!typeOfAccesesOfCostumer.isEmpty()) {
            ArrayList<TypeOfAccess> subOfCostumer = this.getSubscriptionsOfCostumer(costumer);
            for (TypeOfAccess t : subOfCostumer) {
                if (this.isTypeOfAccessValid(t, dateTime)) {
                    t.addAccess();
                    Access newAccess = new Access(costumer, dateTime);
                    accessDao.add(newAccess);
                    return true;
                }
            }
            ArrayList<TypeOfAccess> dailySub = this.getDailyOfCostumer(costumer);
            for (TypeOfAccess t : dailySub) {
                if (this.isTypeOfAccessValid(t, dateTime)) {
                    t.addAccess();
                    Access newAccess = new Access(costumer, dateTime);
                    accessDao.add(newAccess);
                    return true;
                }
            }
        }
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
