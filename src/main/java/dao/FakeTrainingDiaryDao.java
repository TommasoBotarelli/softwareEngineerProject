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
}
