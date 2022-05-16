package businessLogic;

import dao.concreteClass.FakeCostumerDao;
import dao.concreteClass.FakeEvaluationDao;
import dao.concreteClass.FakeTrainingCardDao;
import dao.factoryClass.DaoFactory;
import dao.interfaceClass.*;

import domainModel.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class CostumerController {
    private CostumerDao costumerDao;
    private TrainingCardDao trainingCardDao;
    private EvaluationDao evaluationDao;
    private TrialSubscriptionDao trialSubscriptionDao;
    private SubscriptionDao subscriptionDao;
    private DailyDao dailyDao;

    private Costumer thisCostumer;

    public CostumerController(){
        trialSubscriptionDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getTrialSubscriptionDao();
        subscriptionDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getSubscriptionDao();
        dailyDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getDailyDao();
        costumerDao = FakeCostumerDao.getInstance();
        trainingCardDao = FakeTrainingCardDao.getInstance();
        evaluationDao = FakeEvaluationDao.getInstance();
    }

    public void setCurrentUser(Costumer costumer){
        this.thisCostumer = costumer;
    }

    public Costumer getThisCostumer(){
        return thisCostumer;
    }

    public String getName(){
        return thisCostumer.getName();
    }

    public String getSurname(){
        return thisCostumer.getSurname();
    }

    public String getPhoneNumber(){
        return thisCostumer.getPhoneNumber();
    }

    public ArrayList<AccessType> getMyAccessType(){
        ArrayList<AccessType> allAccessTypeOfCostumer = new ArrayList<>();
        allAccessTypeOfCostumer.add(trialSubscriptionDao.getFromCostumer(thisCostumer));
        allAccessTypeOfCostumer.addAll(dailyDao.getFromCostumer(thisCostumer));
        allAccessTypeOfCostumer.addAll(subscriptionDao.getFromCostumer(thisCostumer));
        return allAccessTypeOfCostumer;
    }

    public ArrayList<TrainingCard> getListOfMyTrainingCard(){
        return trainingCardDao.getTrainingCardFromCostumer(thisCostumer);
    }

    public ArrayList<Evaluation> getListOfMyEvaluation(){
        return evaluationDao.getEvaluationOfCostumer(thisCostumer);
    }

    public ArrayList<TrainingCard> getMyCurrentTrainingCard(int day, int month, int year) throws Exception {
        LocalDate actualDate = LocalDate.of(year, month, day);

        if (!trainingCardDao.getTrainingCardFromCostumer(thisCostumer).isEmpty()){
            ArrayList<TrainingCard> trainingCards = trainingCardDao.getTrainingCardFromCostumer(thisCostumer);

            trainingCards.removeIf(t -> t.getEmission().isAfter(actualDate) || t.getExpiration().isBefore(actualDate));

            return trainingCards;
        }
        else
            throw new Exception("Nessuna scheda da visualizzare");
    }

    public ArrayList<LocalDate> getXCoordinateForGraph(){
        ArrayList<LocalDate> xCoordinate = new ArrayList<>();

        for (Evaluation evaluation : evaluationDao.getEvaluationOfCostumer(thisCostumer)){
            xCoordinate.add(evaluation.getDate());
        }

        return xCoordinate;
    }

    public ArrayList<Float> getWeightForGraph(){
        ArrayList<Float> weightCoordinate = new ArrayList<>();

        for (Evaluation evaluation : evaluationDao.getEvaluationOfCostumer(thisCostumer)){
            weightCoordinate.add(evaluation.getMeasurement().getWeight());
        }

        return weightCoordinate;
    }

    public ArrayList<Float> getHeightForGraph(){
        ArrayList<Float> heightCoordinate = new ArrayList<>();

        for (Evaluation evaluation : evaluationDao.getEvaluationOfCostumer(thisCostumer)){
            heightCoordinate.add(evaluation.getMeasurement().getHeight());
        }

        return heightCoordinate;
    }

    public ArrayList<Float> getLeanMassForGraph(){
        ArrayList<Float> leanMassCoordinate = new ArrayList<>();

        for (Evaluation evaluation : evaluationDao.getEvaluationOfCostumer(thisCostumer)){
            leanMassCoordinate.add(evaluation.getMeasurement().getLeanMass());
        }

        return leanMassCoordinate;
    }

    public ArrayList<Float> getFatMassForGraph(){
        ArrayList<Float> fatMassCoordinate = new ArrayList<>();

        for (Evaluation evaluation : evaluationDao.getEvaluationOfCostumer(thisCostumer)){
            fatMassCoordinate.add(evaluation.getMeasurement().getFatMass());
        }

        return fatMassCoordinate;
    }
}
