package businessLogic;

import dao.*;
import domainModel.*;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;

public class CostumerController {
    TypeOfAccessDao typeOfAccessDao = new FakeTypeOfAccessDao();
    CostumerDao costumerDao = new FakeCostumerDao();
    BillDao billDao = new FakeBilldao();
    TrainingDiaryDao trainingDiaryDao = new FakeTrainingDiaryDao();

    Costumer thisCostumer;

    public boolean setCurrentUser(String name, String surname, String phoneNumber){
        this.thisCostumer = costumerDao.getSelectedCostumer(name, surname, phoneNumber);
        return thisCostumer != null;
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
        return trainingDiaryDao.getTrainingDiaryFromCostumer(thisCostumer).getTrainingCardsContainer();
    }

    public ArrayList<Evaluation> getListOfMyEvaluation(){
        return trainingDiaryDao.getTrainingDiaryFromCostumer(thisCostumer).getEvaluationHistory();
    }

    public TrainingCard getMyCurrentTrainingCard() throws FileNotFoundException {
        if (!trainingDiaryDao.getTrainingDiaryFromCostumer(thisCostumer).getTrainingCardsContainer().isEmpty()){
            ArrayList<TrainingCard> trainingCards = trainingDiaryDao.getTrainingDiaryFromCostumer(thisCostumer).getTrainingCardsContainer();

            TrainingCard lastTrainingCard = trainingCards.get(0);
            int i = 1;
            while (i < trainingCards.size()){
                if (lastTrainingCard.getExpiration().isBefore(trainingCards.get(i).getExpiration()))
                    lastTrainingCard = trainingCards.get(i);
            }

            return lastTrainingCard;
        }
        else
            throw new FileNotFoundException("Nessuna scheda da visualizzare");
    }

    public ArrayList<LocalDate> getXCoordinateForGraph(){
        ArrayList<LocalDate> xCoordinate = new ArrayList<>();

        for (Evaluation evaluation : trainingDiaryDao.getEvaluationOfCostumer(thisCostumer)){
            xCoordinate.add(evaluation.getDate());
        }

        return xCoordinate;
    }

    public ArrayList<Float> getWeightForGraph(){
        ArrayList<Float> weightCoordinate = new ArrayList<>();

        for (Evaluation evaluation : trainingDiaryDao.getEvaluationOfCostumer(thisCostumer)){
            weightCoordinate.add(evaluation.getMeasurement().getWeight());
        }

        return weightCoordinate;
    }

    public ArrayList<Float> getHeightForGraph(){
        ArrayList<Float> heightCoordinate = new ArrayList<>();

        for (Evaluation evaluation : trainingDiaryDao.getEvaluationOfCostumer(thisCostumer)){
            heightCoordinate.add(evaluation.getMeasurement().getHeight());
        }

        return heightCoordinate;
    }

    public ArrayList<Float> getLeanMassForGraph(){
        ArrayList<Float> leanMassCoordinate = new ArrayList<>();

        for (Evaluation evaluation : trainingDiaryDao.getEvaluationOfCostumer(thisCostumer)){
            leanMassCoordinate.add(evaluation.getMeasurement().getLeanMass());
        }

        return leanMassCoordinate;
    }

    public ArrayList<Float> getFatMassForGraph(){
        ArrayList<Float> fatMassCoordinate = new ArrayList<>();

        for (Evaluation evaluation : trainingDiaryDao.getEvaluationOfCostumer(thisCostumer)){
            fatMassCoordinate.add(evaluation.getMeasurement().getFatMass());
        }

        return fatMassCoordinate;
    }
}
