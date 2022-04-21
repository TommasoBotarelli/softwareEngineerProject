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

    public boolean setCurrentReceptionist(String name, String surname, String phoneNumber) throws Exception{
        thisReceptionist = receptionistDao.getReceptionistFromNameSurnamePhoneNumber(name, surname, phoneNumber);
        return thisReceptionist != null;
    }

    public void addCostumer(String name, String surname, String email) {
        Costumer newCostumer = new Costumer(name, surname, email);
        costumerDao.add(newCostumer);
    }

    public void deleteCostumer(Costumer costumer){
        costumerDao.delete(costumer);
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

    public boolean addAccessForCostumerFromBadge(int id, int day, int month, int year){
        LocalDate date = LocalDate.of(year, month, day);
        Costumer costumer = badgeDao.searchCostumerFromId(id);
        TypeOfAccess validTypeOfAccess = typeOfAccessDao.getValidTypeOfAccessFromCostumer(costumer, date);
        if (validTypeOfAccess != null){
            accessDao.add(new Access(costumer, date, true));
            validTypeOfAccess.addAccess();
            return true;
        }
        else{
            accessDao.add(new Access(costumer, date, false));
            return false;
        }
    }

    public boolean addAccessForCostumer(Costumer costumer, int day, int month, int year){
        LocalDate date = LocalDate.of(year, month, day);
        TypeOfAccess validTypeOfAccess = typeOfAccessDao.getValidTypeOfAccessFromCostumer(costumer, date);
        if (validTypeOfAccess != null){
            accessDao.add(new Access(costumer, date, true));
            validTypeOfAccess.addAccess();
            return true;
        }
        else{
            accessDao.add(new Access(costumer, date, false));
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
}
