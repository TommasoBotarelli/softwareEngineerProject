package businessLogic;

import dao.*;
import domainModel.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class PersonalTrainerController {
    private PersonalTrainer thisPersonalTrainer;

    private PersonalTrainerDao personalTrainerDao;
    private TrainingDiaryDao trainingDiaryDao;
    private CostumerDao costumerDao;
    private EvaluationDao evaluationDao;

    public PersonalTrainerController(){
        personalTrainerDao = FakePersonalTrainerDao.getInstance();
        trainingDiaryDao = FakeTrainingDiaryDao.getInstance();
        costumerDao = FakeCostumerDao.getInstance();
        evaluationDao = FakeEvaluationDao.getInstance();
    }

    public boolean setThisPersonalTrainer(String name, String surname, String phoneNumber){
        thisPersonalTrainer = personalTrainerDao.getPersonalTrainer(name, surname, phoneNumber);
        return thisPersonalTrainer != null;
    }

    public ArrayList<TrainingCard> getMyStandardTrainingCard(){
        ArrayList<TrainingCard> myStandardTrainingCards = new ArrayList<>();

        for (TrainingCard trainingCard : trainingDiaryDao.getTrainingCardFromPersonalTrainer(thisPersonalTrainer)){
            if (trainingCard.isStandard())
                myStandardTrainingCards.add(trainingCard);
        }

        return myStandardTrainingCards;
    }

    public void addTrainingCard(Costumer costumer, String exercises, int level, int emissionDay, int emissionMonth, int
                                emissionYear, int expirationDay, int expirationMonth, int expirationYear, boolean standard){
        TrainingCard trainingCard = new TrainingCard(exercises, level, LocalDate.of(emissionYear, emissionMonth, emissionDay),
                LocalDate.of(expirationYear, expirationMonth, expirationDay), standard, thisPersonalTrainer);
        trainingDiaryDao.addTrainingCard(trainingCard, costumer);
    }

    public void addNewCostumer(Costumer costumer){
        TrainingDiary trainingDiary = new TrainingDiary(costumer);
        trainingDiaryDao.addTrainingDiary(trainingDiary);
    }

    public ArrayList<Costumer> getAllCostumer(){
        return costumerDao.getAll();
    }

    public TrainingDiary getTrainingDiaryOfCostumer(Costumer selectedCostumer){
        return trainingDiaryDao.getTrainingDiaryFromCostumer(selectedCostumer);
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
}
