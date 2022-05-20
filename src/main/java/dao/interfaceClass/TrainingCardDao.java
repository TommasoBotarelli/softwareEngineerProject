package dao.interfaceClass;

import domainModel.*;

import java.util.ArrayList;

public interface TrainingCardDao {
    void addTrainingCard(TrainingCard trainingCard);
    ArrayList<TrainingCard> getTrainingCardFromCustomer(Customer customer);
    ArrayList<TrainingCard> getTrainingCardFromPersonalTrainer(PersonalTrainer personalTrainer);
    boolean deleteTrainingCard(TrainingCard trainingCard);
    void deleteAll();
}
