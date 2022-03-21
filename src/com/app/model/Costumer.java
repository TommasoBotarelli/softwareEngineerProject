package com.app.model;

import java.util.ArrayList;
import java.util.Calendar;

public class Costumer {

    private CostumerInfo myInfo;
    private Badge myBadge;
    private TrainingDiary myTrainingDiary;

    private ArrayList<TypeOfAccess> myTypeOfAccess;


    Costumer(CostumerInfo info){
        myInfo = info;
        myTypeOfAccess = new ArrayList<>();
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


    public boolean canAccess(Calendar actualeDate){
        boolean canAccess = false;

        for (TypeOfAccess access : myTypeOfAccess){
            canAccess = canAccess || access.isValid(actualeDate);
        }

        return canAccess;
    }


    private ArrayList<TypeOfAccess> getMyValidAccess(Calendar actualdate){
        ArrayList<TypeOfAccess> validAccessList = new ArrayList<>();

        for (TypeOfAccess access : this.myTypeOfAccess){
            if (access.isValid(actualdate))
                validAccessList.add(access);
        }

        return validAccessList;
    }


    public void setAccess(Calendar actualdate){
        boolean setAccess = false;

        ArrayList<TypeOfAccess> validAccessList = this.getMyValidAccess(actualdate);

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
