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

    private CustomerDao customerDao;
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
        customerDao = FakeCustomerDao.getInstance();
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

    public void addCustomer(String name, String surname, String phoneNumber) {
        Customer newCustomer = new Customer(name, surname, phoneNumber);
        customerDao.add(newCustomer);
    }

    public boolean deleteCustomer(Customer customer){
        return customerDao.delete(customer);
    }

    public ArrayList<Customer> visualizeAllCustomer(){
        return customerDao.getAll();
    }

    public ArrayList<AccessType> getAllAccessType(){
        ArrayList<AccessType> allAccessTypeOfCustomer = new ArrayList<>();
        allAccessTypeOfCustomer.addAll(trialSubscriptionDao.getAll());
        allAccessTypeOfCustomer.addAll(dailyDao.getAll());
        allAccessTypeOfCustomer.addAll(subscriptionDao.getAll());
        return allAccessTypeOfCustomer;
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
    
    public ArrayList<AccessType> getAllAccessTypeFromCustomer(Customer customer){
        ArrayList<AccessType> allAccessTypeOfCustomer = new ArrayList<>();
        allAccessTypeOfCustomer.add(trialSubscriptionDao.getFromCustomer(customer));
        allAccessTypeOfCustomer.addAll(dailyDao.getFromCustomer(customer));
        allAccessTypeOfCustomer.addAll(subscriptionDao.getFromCustomer(customer));
        return allAccessTypeOfCustomer;
    }
    
    public ArrayList<Subscription> getSubscriptionsFromCustomer(Customer customer){
        return subscriptionDao.getFromCustomer(customer);
    }
    public ArrayList<Daily> getDailySubsFromCustomer(Customer customer){
        return dailyDao.getFromCustomer(customer);
    }
    public ArrayList<Subscription> getTrialSubsFromCustomer(Customer customer){
        return subscriptionDao.getFromCustomer(customer);
    }

    public ArrayList<Customer> findCustomer(String name, String surname){
        return customerDao.getFromNameSurname(name, surname);
    }

    public Customer selectCustomer(String name, String surname, String phoneNumber){
        return customerDao.getSelectedCustomer(name, surname, phoneNumber);
    }

    public void addAccessForCustomerFromBadge(long id, LocalDateTime dateTime) throws Exception{
        Customer customer = badgeDao.searchCustomerFromId(id);
        if (customer == null)
            throw new Exception("A Customer with this id doesn't exist");
        else
            this.addAccessForCustomer(customer, dateTime);
    }

    public void addAccessForCustomer(Customer customer, LocalDateTime dateTime) throws Exception{

        boolean canAccess = false;

        TrialSubscription trialSubscription = trialSubscriptionDao.getFromCustomer(customer);
        if (trialSubscription != null){
            if (trialSubscription.isValid(dateTime.toLocalDate())){
                trialSubscription.addAccess();
                canAccess = true;
            }
        }

        if (!canAccess) {
            ArrayList<Daily> dailies = dailyDao.getFromCustomer(customer);
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
            ArrayList<Subscription> subscriptions = subscriptionDao.getFromCustomer(customer);
            if (!subscriptions.isEmpty()) {
                for (Subscription sub : subscriptions) {
                    if (sub.isValid(dateTime.toLocalDate())) {
                        canAccess = true;
                    }
                }
            }
        }

        if (canAccess){
            Access newAccess = new Access(customer, dateTime);
            accessDao.add(newAccess);
        }
        else{
            throw new Exception("The Customer cannot access to the gym");
        }
    }

    public Customer scanBadgeForGetCustomer(long id) throws Exception{
        Customer customer = badgeDao.searchCustomerFromId(id);
        if(customer == null)
            throw new Exception("A Customer with this id doesn't exist");
        return customer;
    }

    public long addBill(float cost, LocalDateTime dateTime){
        Bill newBill = new Bill(cost, dateTime);
        return billDao.add(newBill);
    }

    public void addAccessType(String type, String subscriptionType, long billID, LocalDate date, Customer customer){

        if (type.equalsIgnoreCase("SUBSCRIPTION")){
            if (subscriptionType.equalsIgnoreCase("TRIAL")){
                TrialSubscription sub = new TrialSubscription(customer, date);
                trialSubscriptionDao.add(sub);
            }
            else if (subscriptionType.equalsIgnoreCase("MONTHLY")){
                Subscription subscription = new Subscription(date, TypeOfSub.MONTHLY, customer, billDao.getFromID(billID));
                subscriptionDao.add(subscription);
            }
            else if (subscriptionType.equalsIgnoreCase("QUARTERLY")){
                Subscription subscription = new Subscription(date, TypeOfSub.QUARTERLY, customer, billDao.getFromID(billID));
                subscriptionDao.add(subscription);
            }
            else if (subscriptionType.equalsIgnoreCase("HALFYEARLY")){
                Subscription subscription = new Subscription(date, TypeOfSub.HALFYEARLY, customer, billDao.getFromID(billID));
                subscriptionDao.add(subscription);
            }
            else if (subscriptionType.equalsIgnoreCase("YEARLY")){
                Subscription subscription = new Subscription(date, TypeOfSub.YEARLY, customer, billDao.getFromID(billID));
                subscriptionDao.add(subscription);
            }
        }
        else if (type.equalsIgnoreCase("DAILY")) {
            Daily dailySub = new Daily(date, customer, billDao.getFromID(billID));
            dailyDao.addDaily(dailySub);
        }
    }

    public void releaseBadge(Customer customer){
        Badge badge = new Badge(customer);
        badgeDao.addBadge(badge);
    }

    public long addPayment(TypeOfSub type, LocalDateTime dateTime){
        return billDao.add(new Bill(type.getCost(), dateTime));
    }
}
