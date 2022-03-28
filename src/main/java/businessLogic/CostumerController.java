package businessLogic;

import dao.*;
import domainModel.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class CostumerController {
    TypeOfAccessDao typeOfAccessDao = new FakeTypeOfAccessDao();
    CostumerDao costumerDao = new FakeCostumerDao();
    BillDao billDao = new FakeBilldao();
    TrainingDiaryDao trainingDiaryDao = new FakeTrainingDiaryDao();

    Costumer thisCostumer;

    public boolean fakeLogin(String name, String surname, String phoneNumber){
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
}
