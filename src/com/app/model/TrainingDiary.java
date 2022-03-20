package com.app.model;

import java.util.ArrayList;

public class TrainingDiary {

    private Costumer costumerRef;
    private ArrayList<TrainingCard> trainingCardsContainer;


    TrainingDiary(Costumer costumer){
        this.costumerRef = costumer;
    }


    public void addTrainingCard(TrainingCard newTrainingCard){
        trainingCardsContainer.add(newTrainingCard);
    }


    public ArrayList<TrainingCard> getTrainingCardsContainer() {
        return trainingCardsContainer;
    }


    public ArrayList<TrainingCard> getTrainingCardFromPT(PersonalTrainer pt){
        ArrayList<TrainingCard> personalTrainerCards = new ArrayList<TrainingCard>();

        for (TrainingCard tCard : trainingCardsContainer){
            if (tCard.getPersonalTrainer() == pt)
                personalTrainerCards.add(tCard);
        }

        return personalTrainerCards;
    }


    public Costumer getCostumerRef() {
        return costumerRef;
    }
}
