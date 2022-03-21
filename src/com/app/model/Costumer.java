package com.app.model;

public class Costumer {
    private CostumerInfo myInfo;
    private Badge myBadge;
    private TrainingDiary myTrainingDiary;

    Costumer(CostumerInfo info){
        myInfo = info;
    }


    public void setBadge(Badge badge){
        this.myBadge = badge;
    }


    public CostumerInfo getInfo(){
        return myInfo;
    }
}
