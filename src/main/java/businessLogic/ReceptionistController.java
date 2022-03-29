package businessLogic;

import dao.*;
import domainModel.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class ReceptionistController {

    CostumerDao costumerDao = new FakeCostumerDao();
    TypeOfAccessDao typeOfAccessDao = new FakeTypeOfAccessDao();
    AccessDao accessDao = new FakeAccessDao();
    BillDao billDao = new FakeBilldao();
    ReceptionistDao receptionistDao = new FakeReceptionistDao();

    Receptionist thisReceptionist;

    public boolean setCurrentReceptionist(String name, String surname, String phoneNumber){
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

    public Costumer selectCostumer(String name, String surname, String email){
        return costumerDao.getSelectedCostumer(name, surname, email);
    }

    public boolean addAccessForCostumer(Costumer costumer, int day, int month, int year){

        //INVARIANTE prima del controllo sono sicuro che ho un solo abbonamento valido, all'aggiunta dell'abbonamento ho il controllo

        LocalDate date = LocalDate.of(year, month, day);

        Access access = new Access(costumer, date);
        access.setAccessValid(false);

        ArrayList<TypeOfAccess> typeOfAccessesOfCostumer = typeOfAccessDao.getFromCostumer(costumer);

        int i = 0;
        while (!typeOfAccessesOfCostumer.get(i).isValid(date) && i < typeOfAccessesOfCostumer.size()){
            i++;
        }

        if (i < typeOfAccessesOfCostumer.size()) {
            typeOfAccessesOfCostumer.get(i).addAccess();
            access.setAccessValid(true);
        }

        accessDao.add(access);

        return access.isAccessValid();
    }

    public long addBill(float cost, int year, int month, int day){
        Bill newBill = new Bill(cost, LocalDate.of(year, month, day));
        return billDao.add(newBill);
    }

    public void addTypeOfAccess(String type, String subscriptionType, long billID, int day, int month, int year, Costumer costumer){

        LocalDate date = LocalDate.of(year, month, day);

        if (type.equalsIgnoreCase("SUBSCRIPTION")){
            if (subscriptionType.equalsIgnoreCase("PROVA")){
                TypeOfAccess sub = new Subscription(date, TypeOfSub.PROVA, costumer, billID);
                sub.setExpiration(date.plusDays(TypeOfSub.PROVA.getnDay()));
            }
            else if (subscriptionType.equalsIgnoreCase("MENSILE")){
                TypeOfAccess sub = new Subscription(date, TypeOfSub.MENSILE, costumer, billID);
                sub.setExpiration(date.plusMonths(TypeOfSub.MENSILE.getnMonth()));
            }
            else if (subscriptionType.equalsIgnoreCase("TRIMESTRALE")){
                TypeOfAccess sub = new Subscription(date, TypeOfSub.TRIMESTRALE, costumer, billID);
                sub.setExpiration(date.plusMonths(TypeOfSub.TRIMESTRALE.getnMonth()));
            }
            else if (subscriptionType.equalsIgnoreCase("SEMESTRALE")){
                TypeOfAccess sub = new Subscription(date, TypeOfSub.SEMESTRALE, costumer, billID);
                sub.setExpiration(date.plusMonths(TypeOfSub.SEMESTRALE.getnMonth()));
            }
            else if (subscriptionType.equalsIgnoreCase("ANNUALE")){
                TypeOfAccess sub = new Subscription(date, TypeOfSub.ANNUALE, costumer, billID);
                sub.setExpiration(date.plusMonths(TypeOfSub.ANNUALE.getnMonth()));
            }
        }
        else if (type.equalsIgnoreCase("DAILY")) {
            TypeOfAccess daily = new Daily(date, billID, costumer);
        }
    }
}
