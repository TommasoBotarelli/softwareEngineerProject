package businessLogic;

import dao.*;
import domainModel.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class PersonalTrainerController {
    private PersonalTrainer thisPersonalTrainer;

    private PersonalTrainerDao personalTrainerDao;
    private TrainingCardDao trainingCardDao;
    private CostumerDao costumerDao;
    private EvaluationDao evaluationDao;

    public PersonalTrainerController(){
        personalTrainerDao = FakePersonalTrainerDao.getInstance();
        trainingCardDao = FakeTrainingCardDao.getInstance();
        costumerDao = FakeCostumerDao.getInstance();
        evaluationDao = FakeEvaluationDao.getInstance();
    }

    public boolean setThisPersonalTrainer(String name, String surname, String phoneNumber){
        thisPersonalTrainer = personalTrainerDao.getPersonalTrainer(name, surname, phoneNumber);
        return thisPersonalTrainer != null;
    }

    public ArrayList<TrainingCard> getMyStandardTrainingCard(){
        return trainingCardDao.getStandardTrainingCardsFromPersonalTrainer(thisPersonalTrainer);
    }

    public void addCustomizeTrainingCard(Costumer costumer, String exercises, int level, int emissionDay, int emissionMonth, int
                                emissionYear, int expirationDay, int expirationMonth, int expirationYear){

        TrainingCard trainingCard = new TrainingCard(exercises, level,false, thisPersonalTrainer);

        trainingCard.setCostumer(costumer);
        trainingCard.setEmission(LocalDate.of(emissionYear, emissionMonth, emissionDay));
        trainingCard.setExpiration(LocalDate.of(expirationYear, expirationMonth, expirationDay));

        trainingCardDao.addTrainingCard(trainingCard);
    }

    public void addStandardTrainingCard(String exercises, int level){
        TrainingCard trainingCard = new TrainingCard(exercises, level, true, thisPersonalTrainer);

        trainingCardDao.addTrainingCard(trainingCard);
    }

    public ArrayList<Costumer> getAllCostumer(){
        return costumerDao.getAll();
    }

    public ArrayList<Costumer> findCostumer(String name, String surname){
        return costumerDao.getFromNameSurname(name, surname);
    }

    public void addEvaluation(Costumer costumer, int year, int month, int day, String comments, int progressLevel, float height,
                              float weight, float leanMass, float fatMass){
        LocalDate date = LocalDate.of(year, month, day);
        Measurement measurement = new Measurement(height, weight, leanMass, fatMass);
        Evaluation evaluation = new Evaluation(date, measurement, costumer);
        evaluationDao.addEvaluation(evaluation);
    }

    public ArrayList<Evaluation> getEvaluationOfCostumer(Costumer costumer){
        return evaluationDao.getEvaluationOfCostumer(costumer);
    }

    public void deleteEvaluation(Evaluation evaluation){
        evaluationDao.deleteEvaluation(evaluation);
    }

    public PersonalTrainer getThisPersonalTrainer(){
        return thisPersonalTrainer;
    }

    public ArrayList<TrainingCard> getMyTrainingCard(){
        return trainingCardDao.getTrainingCardFromPersonalTrainer(thisPersonalTrainer);
    }
}
