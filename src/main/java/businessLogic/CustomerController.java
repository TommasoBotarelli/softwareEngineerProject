package businessLogic;

import dao.concreteClass.FakeCustomerDao;
import dao.concreteClass.FakeEvaluationDao;
import dao.concreteClass.FakeTrainingCardDao;
import dao.factoryClass.DaoFactory;
import dao.interfaceClass.*;

import domainModel.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class CustomerController {
    private CustomerDao customerDao;
    private TrainingCardDao trainingCardDao;
    private EvaluationDao evaluationDao;
    private TrialSubscriptionDao trialSubscriptionDao;
    private SubscriptionDao subscriptionDao;
    private DailyDao dailyDao;

    private Customer thisCustomer;

    public CustomerController(){
        trialSubscriptionDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getTrialSubscriptionDao();
        subscriptionDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getSubscriptionDao();
        dailyDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getDailyDao();
        customerDao = FakeCustomerDao.getInstance();
        trainingCardDao = FakeTrainingCardDao.getInstance();
        evaluationDao = FakeEvaluationDao.getInstance();
    }

    public void setCurrentUser(Customer customer){
        this.thisCustomer = customer;
    }

    public Customer getThisCustomer(){
        return thisCustomer;
    }

    public String getName(){
        return thisCustomer.getName();
    }

    public String getSurname(){
        return thisCustomer.getSurname();
    }

    public String getPhoneNumber(){
        return thisCustomer.getPhoneNumber();
    }

    public ArrayList<AccessType> getMyAccessType(){
        ArrayList<AccessType> allAccessTypeOfCustomer = new ArrayList<>();
        allAccessTypeOfCustomer.add(trialSubscriptionDao.getFromCustomer(thisCustomer));
        allAccessTypeOfCustomer.addAll(dailyDao.getFromCustomer(thisCustomer));
        allAccessTypeOfCustomer.addAll(subscriptionDao.getFromCustomer(thisCustomer));
        return allAccessTypeOfCustomer;
    }

    public ArrayList<TrainingCard> getListOfMyTrainingCard(){
        return trainingCardDao.getTrainingCardFromCustomer(thisCustomer);
    }

    public ArrayList<Evaluation> getListOfMyEvaluation(){
        return evaluationDao.getEvaluationOfCustomer(thisCustomer);
    }

    public ArrayList<TrainingCard> getMyCurrentTrainingCard(int day, int month, int year) throws Exception {
        LocalDate actualDate = LocalDate.of(year, month, day);

        if (!trainingCardDao.getTrainingCardFromCustomer(thisCustomer).isEmpty()){
            ArrayList<TrainingCard> trainingCards = trainingCardDao.getTrainingCardFromCustomer(thisCustomer);

            trainingCards.removeIf(t -> t.getEmission().isAfter(actualDate) || t.getExpiration().isBefore(actualDate));

            return trainingCards;
        }
        else
            throw new Exception("Nessuna scheda da visualizzare");
    }

    public ArrayList<LocalDate> getXCoordinateForGraph(){
        ArrayList<LocalDate> xCoordinate = new ArrayList<>();

        for (Evaluation evaluation : evaluationDao.getEvaluationOfCustomer(thisCustomer)){
            xCoordinate.add(evaluation.getDate());
        }

        return xCoordinate;
    }

    public ArrayList<Float> getWeightForGraph(){
        ArrayList<Float> weightCoordinate = new ArrayList<>();

        for (Evaluation evaluation : evaluationDao.getEvaluationOfCustomer(thisCustomer)){
            weightCoordinate.add(evaluation.getMeasurement().getWeight());
        }

        return weightCoordinate;
    }

    public ArrayList<Float> getHeightForGraph(){
        ArrayList<Float> heightCoordinate = new ArrayList<>();

        for (Evaluation evaluation : evaluationDao.getEvaluationOfCustomer(thisCustomer)){
            heightCoordinate.add(evaluation.getMeasurement().getHeight());
        }

        return heightCoordinate;
    }

    public ArrayList<Float> getLeanMassForGraph(){
        ArrayList<Float> leanMassCoordinate = new ArrayList<>();

        for (Evaluation evaluation : evaluationDao.getEvaluationOfCustomer(thisCustomer)){
            leanMassCoordinate.add(evaluation.getMeasurement().getLeanMass());
        }

        return leanMassCoordinate;
    }

    public ArrayList<Float> getFatMassForGraph(){
        ArrayList<Float> fatMassCoordinate = new ArrayList<>();

        for (Evaluation evaluation : evaluationDao.getEvaluationOfCustomer(thisCustomer)){
            fatMassCoordinate.add(evaluation.getMeasurement().getFatMass());
        }

        return fatMassCoordinate;
    }
}
