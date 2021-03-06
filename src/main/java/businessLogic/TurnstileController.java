package businessLogic;

import dao.concreteClass.FakeAccessDao;
import dao.concreteClass.FakeBadgeDao;
import dao.concreteClass.FakeTurnstileDao;
import dao.concreteClass.FakeTypeOfAccessDao;
import dao.interfaceClass.AccessDao;
import dao.interfaceClass.BadgeDao;
import dao.interfaceClass.TurnstileDao;
import dao.interfaceClass.TypeOfAccessDao;
import domainModel.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TurnstileController {
    private final Turnstile entryTurnstile;

    private AccessDao accessDao;
    private BadgeDao badgeDao;
    private TypeOfAccessDao typeOfAccessDao;
    private TurnstileDao turnstileDao;

    //TODO rimuovere il turnstile dao che non serve ho semplificato così.

    public TurnstileController(){
        accessDao = FakeAccessDao.getInstance();
        badgeDao = FakeBadgeDao.getInstance();
        typeOfAccessDao = FakeTypeOfAccessDao.getInstance();
        turnstileDao = FakeTurnstileDao.getInstance();

        entryTurnstile = new Turnstile();
        entryTurnstile.setOpen(false);
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
        this.entryTurnstile.setOpen(true);
    }

    /*
    This method closeEntryTurnstile will be called automatically after the transit of a costumer.
     */

    public void closeEntryTurnstile(){
        this.entryTurnstile.setOpen(false);
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
                    this.entryTurnstile.setOpen(true);
                    return true;
                }
            }
            ArrayList<TypeOfAccess> dailySub = this.getDailyOfCostumer(costumer);
            for (TypeOfAccess t : dailySub) {
                if (this.isTypeOfAccessValid(t, dateTime)) {
                    t.addAccess();
                    Access newAccess = new Access(costumer, dateTime);
                    accessDao.add(newAccess);
                    this.entryTurnstile.setOpen(true);
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
