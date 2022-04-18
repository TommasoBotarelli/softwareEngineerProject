package businessLogic;

import dao.*;
import domainModel.Costumer;
import domainModel.PersonalTrainer;
import domainModel.TrainingCard;
import domainModel.TrainingDiary;

import java.time.LocalDate;
import java.util.ArrayList;

public class PersonalTrainerController {
    PersonalTrainer thisPersonalTrainer;

    PersonalTrainerDao personalTrainerDao = new FakePersonalTrainerDao();
    TrainingDiaryDao trainingDiaryDao = new FakeTrainingDiaryDao();
    CostumerDao costumerDao = new FakeCostumerDao();

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

    public ArrayList<Costumer> getAllCostumer(){
        return costumerDao.getAll();
    }

    public TrainingDiary getTrainingDiaryOfCostumer(Costumer selectedCostumer){
        return trainingDiaryDao.getTrainingDiaryFromCostumer(selectedCostumer);
    }

}