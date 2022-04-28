package businessLogic;

import dao.concreteClass.*;
import dao.factoryClass.DaoFactory;
import dao.interfaceClass.*;
import domainModel.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class ReceptionistController {

    private CostumerDao costumerDao;
    private AccessDao accessDao;
    private BillDao billDao;
    private ReceptionistDao receptionistDao;
    private BadgeDao badgeDao;
    private TrialSubscriptionDao trialSubscriptionDao;
    private SubscriptionDao subscriptionDao;
    private DailyDao dailyDao;

    private Receptionist thisReceptionist;

    public ReceptionistController(){
        trialSubscriptionDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getTrialSubscriptionDao();
        subscriptionDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getSubscriptionDao();
        dailyDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getDailyDao();
        costumerDao = FakeCostumerDao.getInstance();
        accessDao = FakeAccessDao.getInstance();
        billDao = FakeBillDao.getInstance();
        receptionistDao = FakeReceptionistDao.getInstance();
        badgeDao = FakeBadgeDao.getInstance();
    }

    public void setCurrentReceptionist(Receptionist receptionist){
        this.thisReceptionist = receptionist;
    }

    public Receptionist getCurrentReceptionist(){
        return thisReceptionist;
    }

    public boolean setCurrentUser(String name, String surname, String phoneNumber){
        Receptionist receptionist = receptionistDao.getReceptionistFromNameSurnamePhoneNumber(name, surname, phoneNumber);
        if(receptionist != null)
            this.thisReceptionist = receptionist;
        return receptionist != null;
    }

    public void addCostumer(String name, String surname, String phoneNumber) {
        Costumer newCostumer = new Costumer(name, surname, phoneNumber);
        costumerDao.add(newCostumer);
    }

    public boolean deleteCostumer(Costumer costumer){
        return costumerDao.delete(costumer);
    }

    public ArrayList<Costumer> visualizeAllCostumer(){
        return costumerDao.getAll();
    }

    public ArrayList<TrialSubscription> getAllTrialSubscriptions(){
        return trialSubscriptionDao.getAll();
    }
    
    public ArrayList<Subscription> getAllSubscription(){
        return subscriptionDao.getAll();
    }
    
    public ArrayList<Daily> getAllDaily(){
        return dailyDao.getAll();
    }
    
    public ArrayList<AccessType> getAllAccessTypeFromCostumer(Costumer costumer){
        ArrayList<AccessType> allAccessTypeOfCostumer = new ArrayList<>();
        allAccessTypeOfCostumer.add(trialSubscriptionDao.getFromCostumer(costumer));
        allAccessTypeOfCostumer.addAll(dailyDao.getFromCostumer(costumer));
        allAccessTypeOfCostumer.addAll(subscriptionDao.getFromCostumer(costumer));
        return allAccessTypeOfCostumer;
    }
    
    public ArrayList<Subscription> getSubscriptionsFromCostumer(Costumer costumer){
        return subscriptionDao.getFromCostumer(costumer);
    }
    public ArrayList<Daily> getDailySubsFromCostumer(Costumer costumer){
        return dailyDao.getFromCostumer(costumer);
    }
    public ArrayList<Subscription> getTrialSubsFromCostumer(Costumer costumer){
        return subscriptionDao.getFromCostumer(costumer);
    }

    public ArrayList<Costumer> findCostumer(String name, String surname){
        return costumerDao.getFromNameSurname(name, surname);
    }

    public Costumer selectCostumer(String name, String surname, String phoneNumber){
        return costumerDao.getSelectedCostumer(name, surname, phoneNumber);
    }

    public boolean addAccessForCostumerFromBadge(long id, LocalDateTime dateTime) throws Exception{
        Costumer costumer = badgeDao.searchCostumerFromId(id);
        if (costumer == null)
            throw new Exception("A costumer with this id doesn't exist");

        return this.addAccessForCostumer(costumer, dateTime);
    }

    public boolean addAccessForCostumer(Costumer costumer, LocalDateTime dateTime){

        boolean canAccess = false;

        TrialSubscription trialSubscription = trialSubscriptionDao.getFromCostumer(costumer);
        if (trialSubscription != null){
            if (trialSubscription.isValid(dateTime.toLocalDate())){
                trialSubscription.addAccess();
                canAccess = true;
            }
        }

        if (!canAccess) {
            ArrayList<Daily> dailies = dailyDao.getFromCostumer(costumer);
            if (!dailies.isEmpty()) {
                for (Daily d : dailies) {
                    if (d.isValid(dateTime.toLocalDate())) {
                        d.addAccess();
                        canAccess = true;
                    }
                }
            }
        }

        if (!canAccess) {
            ArrayList<Subscription> subscriptions = subscriptionDao.getFromCostumer(costumer);
            if (!subscriptions.isEmpty()) {
                for (Subscription sub : subscriptions) {
                    if (sub.isValid(dateTime.toLocalDate())) {
                        canAccess = true;
                    }
                }
            }
        }

        if (canAccess){
            Access newAccess = new Access(costumer, dateTime);
            accessDao.add(newAccess);
        }

        return canAccess;
    }

    public Costumer scanBadgeForGetCostumer(long id) throws Exception{
        Costumer costumer = badgeDao.searchCostumerFromId(id);
        if(costumer == null)
            throw new Exception("A costumer with this id doesn't exist");
        return costumer;
    }

    public long addBill(float cost, LocalDateTime dateTime){
        Bill newBill = new Bill(cost, dateTime);
        return billDao.add(newBill);
    }

    public void addAccessType(String type, String subscriptionType, long billID, LocalDate date, Costumer costumer){

        if (type.equalsIgnoreCase("SUBSCRIPTION")){
            if (subscriptionType.equalsIgnoreCase("TRIAL")){
                TrialSubscription sub = new TrialSubscription(costumer, date);
                trialSubscriptionDao.add(sub);
            }
            else if (subscriptionType.equalsIgnoreCase("MONTHLY")){
                Subscription subscription = new Subscription(date, TypeOfSub.MONTHLY, costumer, billDao.getFromID(billID));
                subscriptionDao.add(subscription);
            }
            else if (subscriptionType.equalsIgnoreCase("QUARTERLY")){
                Subscription subscription = new Subscription(date, TypeOfSub.QUARTERLY, costumer, billDao.getFromID(billID));
                subscriptionDao.add(subscription);
            }
            else if (subscriptionType.equalsIgnoreCase("HALFYEARLY")){
                Subscription subscription = new Subscription(date, TypeOfSub.HALFYEARLY, costumer, billDao.getFromID(billID));
                subscriptionDao.add(subscription);
            }
            else if (subscriptionType.equalsIgnoreCase("YEARLY")){
                Subscription subscription = new Subscription(date, TypeOfSub.YEARLY, costumer, billDao.getFromID(billID));
                subscriptionDao.add(subscription);
            }
        }
        else if (type.equalsIgnoreCase("DAILY")) {
            Daily dailySub = new Daily(date, costumer, billDao.getFromID(billID));
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
