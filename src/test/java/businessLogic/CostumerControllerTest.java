package businessLogic;

import dao.concreteClass.*;
import dao.factoryClass.DaoFactory;
import dao.interfaceClass.*;
import domainModel.*;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CostumerControllerTest {

    private static CostumerController costumerController = new CostumerController();

    private static CostumerDao costumerDao;
    private static TypeOfAccessDao typeOfAccessDao;
    private static TrainingCardDao trainingCardDao;
    private static PersonalTrainerDao personalTrainerDao;
    private static EvaluationDao evaluationDao;

    @BeforeEach
    void beforeEach(){
        costumerDao.deleteAll();
        typeOfAccessDao.deleteAll();
        trainingCardDao.deleteAll();
        personalTrainerDao.deleteAll();
        evaluationDao.deleteAll();
    }

    @BeforeAll
    static void setUp(){
        costumerDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getCostumerDao();
        typeOfAccessDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getTypeOfAccessDao();
        trainingCardDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getTrainingCardDao();
        personalTrainerDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getPersonalTrainerDao();
        evaluationDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getEvaluationDao();
    }

    @Test
    void setCurrentUser() {
        LocalDate firstDate = LocalDate.now();

        Costumer costumer = new Costumer("Tommaso", "Botarelli", "123456789");
        costumerDao.add(costumer);

        costumerController.setCurrentUser("Tommaso", "Botarelli", "123456789");

        assertEquals(costumer, costumerController.getThisCostumer());
    }

    @Test
    void getMyTypeOfAccess() {
        Costumer costumer = new Costumer("Tommaso", "Botarelli", "123456789");
        costumerDao.add(costumer);

        costumerController.setCurrentUser("Tommaso", "Botarelli", "123456789");

        Subscription subscription1 = new Subscription(LocalDate.now(), TypeOfSub.TRIAL, costumer);
        Subscription subscription2 = new Subscription(LocalDate.now().plusMonths(1), TypeOfSub.MONTHLY, costumer);
        Daily daily1 = new Daily(LocalDate.now().plusMonths(3), costumer);

        ArrayList<TypeOfAccess> typeOfAccessOfCostumer = new ArrayList<>();

        typeOfAccessOfCostumer.add(subscription1);
        typeOfAccessOfCostumer.add(subscription2);
        typeOfAccessOfCostumer.add(daily1);

        typeOfAccessDao.addWithBill(subscription1, 0);
        typeOfAccessDao.addWithBill(subscription2, 1);
        typeOfAccessDao.addWithBill(daily1, 2);

        ArrayList<TypeOfAccess> myTypeOfAccess = costumerController.getMyTypeOfAccess();
        assertEquals(myTypeOfAccess, typeOfAccessOfCostumer);
    }

    @Test
    void getListOfMyTrainingCard() {
        Costumer costumer = new Costumer("Tommaso", "Botarelli", "123456789");
        costumerDao.add(costumer);

        costumerController.setCurrentUser("Tommaso", "Botarelli", "123456789");

        PersonalTrainer personalTrainer = new PersonalTrainer("Sandro", "Giusti", "763581610");
        personalTrainerDao.add(personalTrainer);

        TrainingCard trainingCard1 = new TrainingCard("Some exercises", 2, false, personalTrainer);
        TrainingCard trainingCard2 = new TrainingCard("Some exercises", 5, false, personalTrainer);
        trainingCard1.setCostumer(costumer);
        trainingCard2.setCostumer(costumer);

        trainingCardDao.addTrainingCard(trainingCard1);
        trainingCardDao.addTrainingCard(trainingCard2);

        ArrayList<TrainingCard> trainingCards = new ArrayList<>();

        trainingCards.add(trainingCard1);
        trainingCards.add(trainingCard2);

        assertEquals(trainingCards, costumerController.getListOfMyTrainingCard());
    }

    @Test
    void getListOfMyEvaluation() {
        Costumer costumer = new Costumer("Tommaso", "Botarelli", "123456789");
        costumerDao.add(costumer);

        costumerController.setCurrentUser("Tommaso", "Botarelli", "123456789");

        PersonalTrainer personalTrainer = new PersonalTrainer("Sandro", "Giusti", "763581610");
        personalTrainerDao.add(personalTrainer);

        Measurement measurement = new Measurement(1.8f, 80f, 70f, 10f);
        Evaluation evaluation1 = new Evaluation(LocalDate.now(), measurement, costumer);
        Evaluation evaluation2 = new Evaluation(LocalDate.now().plusMonths(1), measurement, costumer);

        evaluationDao.addEvaluation(evaluation1);
        evaluationDao.addEvaluation(evaluation2);

        ArrayList<Evaluation> evaluations = new ArrayList<>();
        evaluations.add(evaluation1);
        evaluations.add(evaluation2);

        ArrayList<Evaluation> myEvaluations = costumerController.getListOfMyEvaluation();
        assertEquals(evaluations, myEvaluations);
    }

    @Test
    void getMyCurrentTrainingCard(){
        LocalDate date = LocalDate.now();

        Costumer costumer = new Costumer("Tommaso", "Botarelli", "123456789");
        costumerDao.add(costumer);

        costumerController.setCurrentUser("Tommaso", "Botarelli", "123456789");

        PersonalTrainer personalTrainer = new PersonalTrainer("Sandro", "Giusti", "763581610");
        personalTrainerDao.add(personalTrainer);

        TrainingCard trainingCard1 = new TrainingCard("Some exercises", 2, false, personalTrainer);
        TrainingCard trainingCard2 = new TrainingCard("Some exercises", 5, false, personalTrainer);

        trainingCard1.setCostumer(costumer);
        trainingCard1.setEmission(LocalDate.now());
        trainingCard1.setExpiration(LocalDate.now().plusMonths(1));

        trainingCard2.setCostumer(costumer);
        trainingCard2.setEmission(LocalDate.now().plusMonths(2));
        trainingCard2.setExpiration(LocalDate.now().plusMonths(3));

        trainingCardDao.addTrainingCard(trainingCard1);
        trainingCardDao.addTrainingCard(trainingCard2);

        try{
            assertEquals(trainingCard1, costumerController.getMyCurrentTrainingCard(date.getDayOfMonth(), date.getMonthValue(), date.getYear()).get(0));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        try{
            LocalDate secondDate = date.plusDays(35);
            assertEquals(0, costumerController.getMyCurrentTrainingCard(secondDate.getDayOfMonth(), secondDate.getMonthValue(), secondDate.getYear()).size());
        }
        catch (Exception e){
            assertEquals("Nessuna scheda da visualizzare", e.getMessage());
        }
    }
}