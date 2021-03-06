package dao.interfaceClass;

import domainModel.*;

import java.util.ArrayList;

public interface TrainingCardDao {
    void addTrainingCard(TrainingCard trainingCard);
    ArrayList<TrainingCard> getTrainingCardFromCostumer(Costumer costumer);
    ArrayList<TrainingCard> getTrainingCardFromPersonalTrainer(PersonalTrainer personalTrainer);
    boolean deleteTrainingCard(TrainingCard trainingCard);

    //FIXME maybe is better to remove this and control this logic in business logic using the getTrainingCardFromPersonalTrainer

    void deleteAll();
}
