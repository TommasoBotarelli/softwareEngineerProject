package domainModel;

import java.util.ArrayList;

/*
TODO Maybe is better to remove this? Because we dont't use this so much.
 */

public class TrainingDiary {

    private final Costumer costumerRef;
    private ArrayList<TrainingCard> trainingCardsContainer;
    private ArrayList<Evaluation> evaluationHistory;


    public TrainingDiary(Costumer costumer){
        this.costumerRef = costumer;
        trainingCardsContainer = new ArrayList<>();
        evaluationHistory = new ArrayList<>();
    }


    public void addTrainingCard(TrainingCard newTrainingCard){
        trainingCardsContainer.add(newTrainingCard);
    }


    public void addEvaluation(Evaluation evaluation){
        evaluationHistory.add(evaluation);
    }


    public ArrayList<TrainingCard> getTrainingCardsContainer() {
        return trainingCardsContainer;
    }


    public ArrayList<Evaluation> getEvaluationHistory() {
        return evaluationHistory;
    }


    public Costumer getCostumerRef() {
        return costumerRef;
    }

    @Override
    public boolean equals(Object obj) {
        return this.costumerRef.equals(((TrainingDiary)obj).getCostumerRef());
    }
}
