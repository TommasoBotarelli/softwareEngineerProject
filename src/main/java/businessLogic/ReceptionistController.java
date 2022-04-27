package businessLogic;

import dao.concreteClass.*;
import dao.interfaceClass.*;
import domainModel.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ReceptionistController {

    private CostumerDao costumerDao;
    private TypeOfAccessDao typeOfAccessDao;
    private AccessDao accessDao;
    private BillDao billDao;
    private ReceptionistDao receptionistDao;
    private BadgeDao badgeDao;
    private TurnstileDao turnstileDao;

    private Receptionist thisReceptionist;

    public ReceptionistController(){
        costumerDao = FakeCostumerDao.getInstance();
        typeOfAccessDao = FakeTypeOfAccessDao.getInstance();
        accessDao = FakeAccessDao.getInstance();
        billDao = FakeBillDao.getInstance();
        receptionistDao = FakeReceptionistDao.getInstance();
        badgeDao = FakeBadgeDao.getInstance();
        turnstileDao = FakeTurnstileDao.getInstance();
    }

    public void setCurrentReceptionist(Receptionist receptionist){
        this.thisReceptionist = receptionist;
    }

    public Receptionist getCurrentReceptionist(){
        return thisReceptionist;
    }

    public void addCostumer(String name, String surname, String email) {
        Costumer newCostumer = new Costumer(name, surname, email);
        costumerDao.add(newCostumer);
    }

    public boolean deleteCostumer(Costumer costumer){
        return costumerDao.delete(costumer);
    }

    public ArrayList<Costumer> visualizeAllCostumer(){
        return costumerDao.getAll();
    }

    public ArrayList<TypeOfAccess> visualizeAllTypeOfAccess(){
        return typeOfAccessDao.getAll();
    }

    public ArrayList<TypeOfAccess> visualizeTypeOfAccessFromCostumer(Costumer costumer){
        ArrayList<TypeOfAccess> returnArray = new ArrayList<>();
        for (TypeOfAccess typeOfAccess : typeOfAccessDao.getAll()){
            if(typeOfAccess.getCostumer() == costumer)
                returnArray.add(typeOfAccess);
        }
        return returnArray;
    }

    public ArrayList<Costumer> findCostumer(String name, String surname){
        return costumerDao.getFromNameSurname(name, surname);
    }

    public Costumer selectCostumer(String name, String surname, String phoneNumber){
        return costumerDao.getSelectedCostumer(name, surname, phoneNumber);
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

    private boolean isTypeOfAccessValid(TypeOfAccess sub, LocalDateTime localDateTime){
        return (sub.getExpiration().isAfter(localDateTime.toLocalDate()) && sub.getEmission().isBefore(localDateTime.toLocalDate()))
                || (sub.getExpiration().isEqual(localDateTime.toLocalDate())) || (sub.getEmission().isEqual(localDateTime.toLocalDate()));
    }

    public boolean addAccessForCostumerFromBadge(long id, LocalDateTime dateTime) throws Exception{
        Costumer costumer = badgeDao.searchCostumerFromId(id);
        if (costumer == null)
            throw new Exception("A costumer with this id doesn't exist");

        return this.addAccessForCostumer(costumer, dateTime);
    }

    public boolean addAccessForCostumer(Costumer costumer, LocalDateTime dateTime){
        ArrayList<TypeOfAccess> typeOfAccesesOfCostumer = typeOfAccessDao.getFromCostumer(costumer);

        if (!typeOfAccesesOfCostumer.isEmpty()) {
            ArrayList<TypeOfAccess> subOfCostumer = this.getSubscriptionsOfCostumer(costumer);
            for (TypeOfAccess t : subOfCostumer) {
                if (this.isTypeOfAccessValid(t, dateTime)) {
                    t.addAccess();
                    Access newAccess = new Access(costumer, dateTime, turnstileDao.getEntryTurnstile());
                    accessDao.add(newAccess);
                    return true;
                }
            }
            ArrayList<TypeOfAccess> dailySub = this.getDailyOfCostumer(costumer);
            for (TypeOfAccess t : dailySub) {
                if (this.isTypeOfAccessValid(t, dateTime)) {
                    t.addAccess();
                    Access newAccess = new Access(costumer, dateTime, turnstileDao.getEntryTurnstile());
                    accessDao.add(newAccess);
                    return true;
                }
            }
        }
        return false;
    }

    public Costumer scanBadgeForGetCostumer(long id) throws Exception{
        Costumer costumer = badgeDao.searchCostumerFromId(id);
        if(costumer == null)
            throw new Exception("A costumer with this id doesn't exist");
        return costumer;
    }

    public void openEntryTurnstile(){
        turnstileDao.getEntryTurnstile().setOpen(true);
    }

    public void closeEntryTurnstile(){
        turnstileDao.getEntryTurnstile().setOpen(false);
    }

    public long addBill(float cost, LocalDateTime dateTime){
        Bill newBill = new Bill(cost, dateTime);
        return billDao.add(newBill);
    }

    public void addTypeOfAccess(String type, String subscriptionType, long billID, LocalDate date, Costumer costumer){

        if (type.equalsIgnoreCase("SUBSCRIPTION")){
            if (subscriptionType.equalsIgnoreCase("PROVA")){
                TypeOfAccess sub = new Subscription(date, TypeOfSub.TRIAL, costumer);
                sub.setExpiration(date.plusDays(TypeOfSub.TRIAL.getnDay()));
                typeOfAccessDao.addWithBill(sub, billID);
            }
            else if (subscriptionType.equalsIgnoreCase("MENSILE")){
                TypeOfAccess sub = new Subscription(date, TypeOfSub.MONTHLY, costumer);
                sub.setBillID(billID);
                sub.setExpiration(date.plusMonths(TypeOfSub.MONTHLY.getnMonth()));
                typeOfAccessDao.addWithBill(sub, billID);
            }
            else if (subscriptionType.equalsIgnoreCase("TRIMESTRALE")){
                TypeOfAccess sub = new Subscription(date, TypeOfSub.QUARTERLY, costumer);
                sub.setBillID(billID);
                sub.setExpiration(date.plusMonths(TypeOfSub.QUARTERLY.getnMonth()));
                typeOfAccessDao.addWithBill(sub, billID);
            }
            else if (subscriptionType.equalsIgnoreCase("SEMESTRALE")){
                TypeOfAccess sub = new Subscription(date, TypeOfSub.HALFYEARLY, costumer);
                sub.setBillID(billID);
                sub.setExpiration(date.plusMonths(TypeOfSub.HALFYEARLY.getnMonth()));
                typeOfAccessDao.addWithBill(sub, billID);
            }
            else if (subscriptionType.equalsIgnoreCase("ANNUALE")){
                TypeOfAccess sub = new Subscription(date, TypeOfSub.YEARLY, costumer);
                sub.setBillID(billID);
                sub.setExpiration(date.plusMonths(TypeOfSub.YEARLY.getnMonth()));
                typeOfAccessDao.addWithBill(sub, billID);
            }
        }
        else if (type.equalsIgnoreCase("DAILY")) {
            TypeOfAccess daily = new Daily(date, costumer);
            daily.setBillID(billID);
            daily.setExpiration(date);
            typeOfAccessDao.addWithBill(daily, billID);
        }
    }

    public void releaseBadge(Costumer costumer){
        Badge badge = new Badge(costumer);
        badgeDao.addBadge(badge);
    }

    public long addPayment(TypeOfSub type, LocalDateTime dateTime){
        return billDao.add(new Bill(type.getCost(), dateTime));
    }
}
