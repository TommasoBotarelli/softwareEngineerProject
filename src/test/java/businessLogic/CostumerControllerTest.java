package businessLogic;

import dao.*;
import domainModel.*;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;

class CostumerControllerTest {

    private static CostumerController costumerController = new CostumerController();
    private static ArrayList<TypeOfAccess> typeOfAccessOfCostumer = new ArrayList<>();
    private static ArrayList<TrainingCard> trainingCards = new ArrayList<>();
    private static ArrayList<Evaluation> evaluations = new ArrayList<>();

    @BeforeAll
    static void setUp(){
        /*
        Si finge di avere un costumer con vari dati già nel database, questo metodo è utilizzato per
        mettere tutti i dati nel posto giusto.
         */
        LocalDate firstDate = LocalDate.of(2022, 4, 20);
        Costumer costumer = new Costumer("Tommaso", "Botarelli", "123456789");
        CostumerDao costumerDao = FakeCostumerDao.getInstance();
        costumerDao.add(costumer);
        Bill bill = new Bill(200, firstDate);
        BillDao billDao = FakeBillDao.getInstance();
        billDao.add(bill);
        Subscription freeSubscription = new Subscription(firstDate, TypeOfSub.PROVA, costumer);
        Subscription subscription = new Subscription(firstDate.plusDays(14), TypeOfSub.SEMESTRALE, costumer);
        subscription.setBillID(bill.getId());
        TypeOfAccessDao typeOfAccessDao = FakeTypeOfAccessDao.getInstance();
        typeOfAccessDao.add(freeSubscription);
        typeOfAccessDao.add(subscription);
        typeOfAccessOfCostumer.add(freeSubscription);
        typeOfAccessOfCostumer.add(subscription);
        costumerController.setCurrentUser("Tommaso", "Botarelli", "123456789");
        PersonalTrainer aPersonalTrainer = new PersonalTrainer("Mario", "Rossi", "987654321");
        TrainingCard myFirstTrainingCard = new TrainingCard("Some exercises", 1, firstDate, firstDate.plusDays(14), true, aPersonalTrainer);
        myFirstTrainingCard.setCostumer(costumer);
        TrainingCard mySecondTrainingCard = new TrainingCard("Some exercises", 2, firstDate.plusDays(14), firstDate.plusMonths(1), true, aPersonalTrainer);
        mySecondTrainingCard.setCostumer(costumer);
        TrainingCardDao trainingCardDao = FakeTrainingCardDao.getInstance();
        trainingCardDao.addTrainingCard(myFirstTrainingCard);
        trainingCardDao.addTrainingCard(mySecondTrainingCard);
        trainingCards.add(myFirstTrainingCard);
        trainingCards.add(mySecondTrainingCard);
        Measurement firstMeasurement = new Measurement(1.85f, 80.0f, 70.0f, 10.0f);
        Evaluation firstEvaluation = new Evaluation(firstDate, firstMeasurement, costumer);
        firstEvaluation.setProgressLevel(1);
        Evaluation secondEvaluation = new Evaluation(firstDate.plusDays(14), firstMeasurement, costumer);
        secondEvaluation.setProgressLevel(2);
        EvaluationDao evaluationDao = FakeEvaluationDao.getInstance();
        evaluationDao.addEvaluation(firstEvaluation);
        evaluationDao.addEvaluation(secondEvaluation);
        evaluations.add(firstEvaluation);
        evaluations.add(secondEvaluation);
    }

    @Test
    void setCurrentUser() {
        Assertions.assertEquals("Tommaso", costumerController.getName());
        Assertions.assertEquals("Botarelli", costumerController.getSurname());
        Assertions.assertEquals("123456789", costumerController.getPhoneNumber());
    }

    @Test
    void getMyTypeOfAccess() {
        ArrayList<TypeOfAccess> myTypeOfAccess = costumerController.getMyTypeOfAccess();
        Assertions.assertEquals(myTypeOfAccess, typeOfAccessOfCostumer);
    }

    @Test
    void getListOfMyTrainingCard() {
        ArrayList<TrainingCard> myTrainingsCards = costumerController.getListOfMyTrainingCard();
        Assertions.assertEquals(trainingCards, myTrainingsCards);
    }

    @Test
    void getListOfMyEvaluation() {
        ArrayList<Evaluation> myEvaluations = costumerController.getListOfMyEvaluation();
        Assertions.assertEquals(evaluations, myEvaluations);
    }

    @Test
    void getMyCurrentTrainingCard() throws FileNotFoundException {
        ArrayList<TrainingCard> myActualTrainingCard = costumerController.getMyCurrentTrainingCard(25, 4, 2022);
        Assertions.assertEquals(myActualTrainingCard.size(), 1);
        Assertions.assertEquals(myActualTrainingCard.get(0), trainingCards.get(0));
    }
}