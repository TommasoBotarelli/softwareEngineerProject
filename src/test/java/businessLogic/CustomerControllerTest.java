package businessLogic;

import dao.factoryClass.DaoFactory;
import dao.interfaceClass.*;
import domainModel.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerControllerTest {

    private static CustomerController customerController = new CustomerController();

    private static CustomerDao customerDao;
    private static TrainingCardDao trainingCardDao;
    private static PersonalTrainerDao personalTrainerDao;
    private static EvaluationDao evaluationDao;
    private static TrialSubscriptionDao trialSubscriptionDao;
    private static SubscriptionDao subscriptionDao;
    private static DailyDao dailyDao;

    @BeforeEach
    void beforeEach(){
        trialSubscriptionDao.deleteAll();
        subscriptionDao.deleteAll();
        dailyDao.deleteAll();
        customerDao.deleteAll();
        trainingCardDao.deleteAll();
        personalTrainerDao.deleteAll();
        evaluationDao.deleteAll();
    }

    @BeforeAll
    static void setUp(){
        trialSubscriptionDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getTrialSubscriptionDao();
        subscriptionDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getSubscriptionDao();
        dailyDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getDailyDao();
        customerDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getCustomerDao();
        trainingCardDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getTrainingCardDao();
        personalTrainerDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getPersonalTrainerDao();
        evaluationDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getEvaluationDao();
    }

    @Test
    void setCurrentUser() {
        Customer customer = new Customer("Tommaso", "Botarelli", "123456789");
        customerDao.add(customer);

        customerController.setCurrentUser(customer);

        assertEquals(customer, customerController.getThisCustomer());
    }

    @Test
    void getMyTypeOfAccess() {
        Customer customer = new Customer("Tommaso", "Botarelli", "123456789");
        customerDao.add(customer);

        customerController.setCurrentUser(customer);

        Bill genericBill = new Bill(20f, LocalDateTime.now());

        TrialSubscription trialSubscription1 = new TrialSubscription(customer, LocalDate.now());
        Subscription subscription2 = new Subscription(LocalDate.now().plusMonths(1), TypeOfSub.MONTHLY, customer, genericBill);
        Daily daily1 = new Daily(LocalDate.now().plusMonths(3), customer, genericBill);

        ArrayList<AccessType> typeOfAccessOfCustomer = new ArrayList<>();

        typeOfAccessOfCustomer.add(trialSubscription1);
        typeOfAccessOfCustomer.add(daily1);
        typeOfAccessOfCustomer.add(subscription2);

        trialSubscriptionDao.add(trialSubscription1);
        subscriptionDao.add(subscription2);
        dailyDao.addDaily(daily1);

        ArrayList<AccessType> myTypeOfAccess = customerController.getMyAccessType();
        assertEquals(myTypeOfAccess, typeOfAccessOfCustomer);
    }

    @Test
    void getListOfMyTrainingCard() {
        Customer customer = new Customer("Tommaso", "Botarelli", "123456789");
        customerDao.add(customer);

        customerController.setCurrentUser(customer);

        PersonalTrainer personalTrainer = new PersonalTrainer("Sandro", "Giusti", "763581610");
        personalTrainerDao.add(personalTrainer);

        TrainingCard trainingCard1 = new TrainingCard("Some exercises", 2, false, personalTrainer);
        TrainingCard trainingCard2 = new TrainingCard("Some exercises", 5, false, personalTrainer);
        trainingCard1.setCustomer(customer);
        trainingCard2.setCustomer(customer);

        trainingCardDao.addTrainingCard(trainingCard1);
        trainingCardDao.addTrainingCard(trainingCard2);

        ArrayList<TrainingCard> trainingCards = new ArrayList<>();

        trainingCards.add(trainingCard1);
        trainingCards.add(trainingCard2);

        assertEquals(trainingCards, customerController.getListOfMyTrainingCard());
    }

    @Test
    void getListOfMyEvaluation() {
        Customer customer = new Customer("Tommaso", "Botarelli", "123456789");
        customerDao.add(customer);

        customerController.setCurrentUser(customer);

        PersonalTrainer personalTrainer = new PersonalTrainer("Sandro", "Giusti", "763581610");
        personalTrainerDao.add(personalTrainer);

        Measurement measurement = new Measurement(1.8f, 80f, 70f, 10f);
        Evaluation evaluation1 = new Evaluation(LocalDate.now(), measurement, customer);
        Evaluation evaluation2 = new Evaluation(LocalDate.now().plusMonths(1), measurement, customer);

        evaluationDao.addEvaluation(evaluation1);
        evaluationDao.addEvaluation(evaluation2);

        ArrayList<Evaluation> evaluations = new ArrayList<>();
        evaluations.add(evaluation1);
        evaluations.add(evaluation2);

        ArrayList<Evaluation> myEvaluations = customerController.getListOfMyEvaluation();
        assertEquals(evaluations, myEvaluations);
    }

    @Test
    void getMyCurrentTrainingCard(){
        LocalDate date = LocalDate.now();

        Customer customer = new Customer("Tommaso", "Botarelli", "123456789");
        customerDao.add(customer);

        customerController.setCurrentUser(customer);

        PersonalTrainer personalTrainer = new PersonalTrainer("Sandro", "Giusti", "763581610");
        personalTrainerDao.add(personalTrainer);

        TrainingCard trainingCard1 = new TrainingCard("Some exercises", 2, false, personalTrainer);
        TrainingCard trainingCard2 = new TrainingCard("Some exercises", 5, false, personalTrainer);

        trainingCard1.setCustomer(customer);
        trainingCard1.setEmission(LocalDate.now());
        trainingCard1.setExpiration(LocalDate.now().plusMonths(1));

        trainingCard2.setCustomer(customer);
        trainingCard2.setEmission(LocalDate.now().plusMonths(2));
        trainingCard2.setExpiration(LocalDate.now().plusMonths(3));

        trainingCardDao.addTrainingCard(trainingCard1);
        trainingCardDao.addTrainingCard(trainingCard2);

        try{
            assertEquals(trainingCard1, customerController.getMyCurrentTrainingCard(date.getDayOfMonth(), date.getMonthValue(), date.getYear()).get(0));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        try{
            LocalDate secondDate = date.plusDays(35);
            assertEquals(0, customerController.getMyCurrentTrainingCard(secondDate.getDayOfMonth(), secondDate.getMonthValue(), secondDate.getYear()).size());
        }
        catch (Exception e){
            assertEquals("Nessuna scheda da visualizzare", e.getMessage());
        }
    }
}