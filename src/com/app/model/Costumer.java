package com.app.model;

public class Costumer {
    private CostumerInfo myInfo;
    private Badge myBadge;
    private TrainingDiary myTrainingDiary;

    Costumer(String name, String surname, String email){
        myInfo = new CostumerInfo(name, surname, email);
    }


    public void setBadge(Badge badge){
        this.myBadge = badge;
    }


    public CostumerInfo getInfo(){
        return myInfo;
    }
}
