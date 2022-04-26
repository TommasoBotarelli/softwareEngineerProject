package businessLogic;

import dao.concreteClass.FakeCostumerDao;
import dao.concreteClass.FakeEvaluationDao;
import dao.concreteClass.FakeTrainingCardDao;
import dao.concreteClass.FakeTypeOfAccessDao;
import dao.interfaceClass.CostumerDao;
import dao.interfaceClass.EvaluationDao;
import dao.interfaceClass.TrainingCardDao;
import dao.interfaceClass.TypeOfAccessDao;

import domainModel.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class CostumerController {
    private TypeOfAccessDao typeOfAccessDao;
    private CostumerDao costumerDao;
    private TrainingCardDao trainingCardDao;
    private EvaluationDao evaluationDao;

    private Costumer thisCostumer;

    public CostumerController(){
        typeOfAccessDao = FakeTypeOfAccessDao.getInstance();
        costumerDao = FakeCostumerDao.getInstance();
        trainingCardDao = FakeTrainingCardDao.getInstance();
        evaluationDao = FakeEvaluationDao.getInstance();
    }

    public boolean setCurrentUser(String name, String surname, String phoneNumber){
        this.thisCostumer = costumerDao.getSelectedCostumer(name, surname, phoneNumber);
        return thisCostumer != null;
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

    public ArrayList<TypeOfAccess> getMyTypeOfAccess(){
        return typeOfAccessDao.getFromCostumer(thisCostumer);
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
