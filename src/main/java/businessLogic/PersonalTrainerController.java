package businessLogic;

import dao.FakePersonalTrainerDao;
import dao.FakeTrainingDiaryDao;
import dao.PersonalTrainerDao;
import dao.TrainingDiaryDao;
import domainModel.PersonalTrainer;
import domainModel.TrainingCard;

import java.util.ArrayList;

public class PersonalTrainerController {
    PersonalTrainer thisPersonalTrainer;

    PersonalTrainerDao personalTrainerDao = new FakePersonalTrainerDao();
    TrainingDiaryDao trainingDiaryDao = new FakeTrainingDiaryDao();

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

}
