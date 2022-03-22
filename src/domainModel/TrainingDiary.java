package domainModel;

import java.util.ArrayList;

public class TrainingDiary {

    private final Costumer costumerRef;
    private ArrayList<TrainingCard> trainingCardsContainer;
    private ArrayList<Evaluation> evaluationHistory;


    TrainingDiary(Costumer costumer){
        this.costumerRef = costumer;
        trainingCardsContainer = new ArrayList<TrainingCard>();
        evaluationHistory = new ArrayList<Evaluation>();
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
