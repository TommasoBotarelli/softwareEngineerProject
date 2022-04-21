package businessLogic;

import dao.*;
import domainModel.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class ReceptionistController {

    private CostumerDao costumerDao;
    private TypeOfAccessDao typeOfAccessDao;
    private AccessDao accessDao;
    private BillDao billDao;
    private ReceptionistDao receptionistDao;
    private BadgeDao badgeDao;

    private Receptionist thisReceptionist;

    public ReceptionistController(){
        costumerDao = FakeCostumerDao.getInstance();
        typeOfAccessDao = FakeTypeOfAccessDao.getInstance();
        accessDao = FakeAccessDao.getInstance();
        billDao = FakeBillDao.getInstance();
        receptionistDao = FakeReceptionistDao.getInstance();
        badgeDao = FakeBadgeDao.getInstance();
    }

    public boolean setCurrentReceptionist(String name, String surname, String phoneNumber){
        thisReceptionist = receptionistDao.getReceptionistFromNameSurnamePhoneNumber(name, surname, phoneNumber);
        return thisReceptionist != null;
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

    public boolean addAccessForCostumerFromBadge(long id, LocalDate date) throws Exception{
        Costumer costumer = badgeDao.searchCostumerFromId(id);
        if (costumer == null)
            throw new Exception("A costumer with this id doesn't exist");

        ArrayList<TypeOfAccess> typeOfAccesesOfCostumer = typeOfAccessDao.getFromCostumer(costumer);

        if (!typeOfAccesesOfCostumer.isEmpty()){
            ArrayList<TypeOfAccess> subOfCostumer = this.getSubscriptionsOfCostumer(costumer);
            for (TypeOfAccess t : subOfCostumer){
                if (t.isValid(date)){
                    t.addAccess();
                    Access newAccess = new Access(costumer, date);
                    accessDao.add(newAccess);
                    return true;
                }
            }
            ArrayList<TypeOfAccess> dailyOfSub = this.getDailyOfCostumer(costumer);
            for (TypeOfAccess t : subOfCostumer){
                if (t.isValid(date)){
                    t.addAccess();
                    Access newAccess = new Access(costumer, date);
                    accessDao.add(newAccess);
                    return true;
                }
            }
        }
        accessDao.add(new Access(costumer, date));
        return false;
    }

    public boolean addAccessForCostumer(Costumer costumer, int day, int month, int year){
        LocalDate date = LocalDate.of(year, month, day);
        TypeOfAccess validTypeOfAccess = typeOfAccessDao.getValidTypeOfAccessFromCostumer(costumer, date);
        if (validTypeOfAccess != null){
            accessDao.add(new Access(costumer, date));
            validTypeOfAccess.addAccess();
            return true;
        }
        else{
            accessDao.add(new Access(costumer, date));
            return false;
        }
    }

    public long addBill(float cost, int year, int month, int day){
        Bill newBill = new Bill(cost, LocalDate.of(year, month, day));
        return billDao.add(newBill);
    }

    public void addTypeOfAccess(String type, String subscriptionType, long billID, int day, int month, int year, Costumer costumer){

        LocalDate date = LocalDate.of(year, month, day);

        if (type.equalsIgnoreCase("SUBSCRIPTION")){
            if (subscriptionType.equalsIgnoreCase("PROVA")){
                TypeOfAccess sub = new Subscription(date, TypeOfSub.PROVA, costumer);
                sub.setExpiration(date.plusDays(TypeOfSub.PROVA.getnDay()));
            }
            else if (subscriptionType.equalsIgnoreCase("MENSILE")){
                TypeOfAccess sub = new Subscription(date, TypeOfSub.MENSILE, costumer);
                sub.setBillID(billID);
                sub.setExpiration(date.plusMonths(TypeOfSub.MENSILE.getnMonth()));
            }
            else if (subscriptionType.equalsIgnoreCase("TRIMESTRALE")){
                TypeOfAccess sub = new Subscription(date, TypeOfSub.TRIMESTRALE, costumer);
                sub.setBillID(billID);
                sub.setExpiration(date.plusMonths(TypeOfSub.TRIMESTRALE.getnMonth()));
            }
            else if (subscriptionType.equalsIgnoreCase("SEMESTRALE")){
                TypeOfAccess sub = new Subscription(date, TypeOfSub.SEMESTRALE, costumer);
                sub.setBillID(billID);
                sub.setExpiration(date.plusMonths(TypeOfSub.SEMESTRALE.getnMonth()));
            }
            else if (subscriptionType.equalsIgnoreCase("ANNUALE")){
                TypeOfAccess sub = new Subscription(date, TypeOfSub.ANNUALE, costumer);
                sub.setBillID(billID);
                sub.setExpiration(date.plusMonths(TypeOfSub.ANNUALE.getnMonth()));
            }
        }
        else if (type.equalsIgnoreCase("DAILY")) {
            TypeOfAccess daily = new Daily(date, costumer);
            daily.setBillID(billID);
        }
    }

    public void releaseBadge(Costumer costumer){
        Badge badge = new Badge(costumer);
        badgeDao.addBadge(badge);
    }
}
