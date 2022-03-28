package dao;

import domainModel.*;

import java.util.ArrayList;

public interface TrainingDiaryDao {
    void addEvaluation(Evaluation evaluation, Costumer costumer);
    void addTrainingCard(TrainingCard trainingCard, Costumer costumer);
    void addTrainingDiary(TrainingDiary trainingDiary);
    TrainingDiary getTrainingDiaryFromCostumer(Costumer costumer);
}
