package dao.concreteClass;

import dao.interfaceClass.TrainingCardDao;
import domainModel.*;

import java.util.ArrayList;

public class FakeTrainingCardDao implements TrainingCardDao {
    private ArrayList<TrainingCard> trainingCards;
    private static FakeTrainingCardDao instance = null;

    private FakeTrainingCardDao(){
        trainingCards = new ArrayList<>();
    }

    public static FakeTrainingCardDao getInstance(){
        if (instance == null){
            instance = new FakeTrainingCardDao();
        }
        return instance;
    }

    @Override
    public void addTrainingCard(TrainingCard trainingCard) {
        trainingCards.add(trainingCard);
    }

    @Override
    public ArrayList<TrainingCard> getTrainingCardFromCostumer(Costumer costumer) {
        ArrayList<TrainingCard> trainingCardsOfCostumer = new ArrayList<>();

        for(TrainingCard t : trainingCards){
            if (t.getCostumer().equals(costumer)){
                trainingCardsOfCostumer.add(t);
            }
        }

        return trainingCardsOfCostumer;
    }

    @Override
    public ArrayList<TrainingCard> getTrainingCardFromPersonalTrainer(PersonalTrainer personalTrainer) {
        ArrayList<TrainingCard> trainingCardsOfPersonalTrainer = new ArrayList<>();

        for(TrainingCard t : trainingCards){
            if(t.getPersonalTrainer().equals(personalTrainer))
                trainingCardsOfPersonalTrainer.add(t);
        }

        return trainingCardsOfPersonalTrainer;
    }

    @Override
    public boolean deleteTrainingCard(TrainingCard trainingCard) {
        if (trainingCards.contains(trainingCard)){
            trainingCards.remove(trainingCard);
            return true;
        }
        else
            return false;
    }

    @Override
    public void deleteAll() {
        trainingCards.clear();
    }
}
