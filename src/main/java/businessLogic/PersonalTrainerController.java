package businessLogic;

import dao.concreteClass.FakeCostumerDao;
import dao.concreteClass.FakeEvaluationDao;
import dao.concreteClass.FakePersonalTrainerDao;
import dao.concreteClass.FakeTrainingCardDao;
import dao.interfaceClass.CostumerDao;
import dao.interfaceClass.EvaluationDao;
import dao.interfaceClass.PersonalTrainerDao;
import dao.interfaceClass.TrainingCardDao;
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
        ArrayList<TrainingCard> allMyTrainingCard = trainingCardDao.getTrainingCardFromPersonalTrainer(thisPersonalTrainer);
        ArrayList<TrainingCard> standardTrainingCard = new ArrayList<>();

        for (TrainingCard t : allMyTrainingCard){
            if (t.isStandard())
                standardTrainingCard.add(t);
        }

        return standardTrainingCard;
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

    //TODO testa questi metodi

    public ArrayList<TrainingCard> getMyTrainingCard(){
        return trainingCardDao.getTrainingCardFromPersonalTrainer(thisPersonalTrainer);
    }

    public void copyTrainingCard(TrainingCard trainingCard, Costumer costumer, LocalDate emission, LocalDate expiration){
        TrainingCard trainingCardCopied = new TrainingCard(trainingCard);
        trainingCardCopied.setCostumer(costumer);
        trainingCardCopied.setEmission(emission);
        trainingCardCopied.setExpiration(expiration);
        trainingCardDao.addTrainingCard(trainingCardCopied);
    }
}
