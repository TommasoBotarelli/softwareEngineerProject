package dao;

import domainModel.*;

import java.util.ArrayList;

public class FakeTrainingDiaryDao implements TrainingDiaryDao{
    ArrayList<TrainingDiary> trainingDiaries;

    @Override
    public void addEvaluation(Evaluation evaluation, Costumer costumer) {
        TrainingDiary trainingDiaryOfCostumer = (TrainingDiary) trainingDiaries.stream().filter
                (trainingDiary -> trainingDiary.getCostumerRef().equals(costumer));
        trainingDiaryOfCostumer.addEvaluation(evaluation);
    }

    @Override
    public void addTrainingCard(TrainingCard trainingCard, Costumer costumer) {
        TrainingDiary trainingDiaryOfCostumer = (TrainingDiary) trainingDiaries.stream().filter
                (trainingDiary -> trainingDiary.getCostumerRef().equals(costumer));
        trainingDiaryOfCostumer.addTrainingCard(trainingCard);
    }

    @Override
    public void addTrainingDiary(TrainingDiary trainingDiary) {
        trainingDiaries.add(trainingDiary);
    }

    @Override
    public TrainingDiary getTrainingDiaryFromCostumer(Costumer costumer) {
        return (TrainingDiary)(trainingDiaries.stream().filter(trainingDiary -> trainingDiary.getCostumerRef().equals(costumer)));
    }

    @Override
    public ArrayList<TrainingCard> getTrainingCardFromCostumer(Costumer costumer) {
        return getTrainingDiaryFromCostumer(costumer).getTrainingCardsContainer();
    }

    @Override
    public ArrayList<Evaluation> getEvaluationOfCostumer(Costumer costumer) {
        return getTrainingDiaryFromCostumer(costumer).getEvaluationHistory();
    }

    @Override
    public ArrayList<TrainingCard> getTrainingCardFromPersonalTrainer(PersonalTrainer personalTrainer) {
        ArrayList<TrainingCard> trainingCardsOfPersonalTrainer = new ArrayList<>();

        for(TrainingDiary trainingDiary : trainingDiaries){
            for (TrainingCard trainingCard : trainingDiary.getTrainingCardsContainer()){
                if (trainingCard.getPersonalTrainer().equals(personalTrainer))
                    trainingCardsOfPersonalTrainer.add(trainingCard);
            }
        }

        return trainingCardsOfPersonalTrainer;
    }
}
