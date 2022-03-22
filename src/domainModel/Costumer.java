package domainModel;

import java.util.ArrayList;
import java.util.Calendar;

/*
TODO la logica di accesso deve essere tolta dal Costumer e l'accesso viene controllato tramite il database
 */

public class Costumer {

    private long id;

    private CostumerInfo myInfo;
    private Badge myBadge;
    private TrainingDiary myTrainingDiary;

    private ArrayList<TypeOfAccess> myTypeOfAccess;


    Costumer(String name, String surname, String email){
        myInfo = new CostumerInfo(name, surname, email);
        myTypeOfAccess = new ArrayList<>();
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setBadge(Badge badge){
        this.myBadge = badge;
    }


    public CostumerInfo getInfo() {
        return myInfo;
    }


    public void addTypeOfAccess(TypeOfAccess access){
        myTypeOfAccess.add(access);
    }


    public boolean canAccess(Calendar actualDate){
        boolean canAccess = false;

        for (TypeOfAccess access : myTypeOfAccess){
            canAccess = canAccess || access.isValid(actualDate);
        }

        return canAccess;
    }


    private ArrayList<TypeOfAccess> getMyValidAccess(Calendar actualDate){
        ArrayList<TypeOfAccess> validAccessList = new ArrayList<>();

        for (TypeOfAccess access : this.myTypeOfAccess){
            if (access.isValid(actualDate))
                validAccessList.add(access);
        }

        return validAccessList;
    }


    public void setAccess(Calendar actualDate){
        boolean setAccess = false;

        ArrayList<TypeOfAccess> validAccessList = this.getMyValidAccess(actualDate);

        /*
        Il cliente potrebbe avere abbonamenti e accessi singoli, in questo caso
        se ha un abbonamento entra usando quello, altrimenti utilizza uno dei
        daily che ha. Si rende necessatio l'utilizzo di instanceof.
        */

        int index = 0;
        while(!setAccess && index < validAccessList.size()){
            if (validAccessList.get(index) instanceof Subscription) {
                setAccess = true;
                validAccessList.get(index).addAccess();
            }
        }

        if (!setAccess){
            validAccessList.get(0).addAccess();
        }
    }


}
