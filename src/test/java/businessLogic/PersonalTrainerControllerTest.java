package businessLogic;

import dao.factoryClass.DaoFactory;
import dao.interfaceClass.EvaluationDao;
import dao.interfaceClass.PersonalTrainerDao;
import dao.interfaceClass.TrainingCardDao;
import domainModel.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class PersonalTrainerControllerTest {

    private PersonalTrainerController personalTrainerController = new PersonalTrainerController();
    private static PersonalTrainerDao personalTrainerDao;
    private static TrainingCardDao trainingCardDao;
    private static EvaluationDao evaluationDao;
    
    @BeforeAll
    static void beforeAll(){
        personalTrainerDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getPersonalTrainerDao();
        trainingCardDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getTrainingCardDao();
        evaluationDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getEvaluationDao();
    }

    @BeforeEach
    void setUp(){
        personalTrainerDao.deleteAll();
        trainingCardDao.deleteAll();
        evaluationDao.deleteAll();
    }

    @Test
    void setThisPersonalTrainer() {
        PersonalTrainer personalTrainer = new PersonalTrainer("Tommaso", "Botarelli", "7576143571");
        personalTrainerDao.add(personalTrainer);
        personalTrainerController.setThisPersonalTrainer(personalTrainer);
        assertEquals(personalTrainerDao.getPersonalTrainer("Tommaso", "Botarelli", "7576143571"),
                personalTrainerController.getThisPersonalTrainer());
    }

    @Test
    void getMyStandardTrainingCard() {
        PersonalTrainer personalTrainer = new PersonalTrainer("Tommaso", "Botarelli", "7576143571");

        personalTrainerDao.add(personalTrainer);

        personalTrainerController.setThisPersonalTrainer(personalTrainer);

        TrainingCard trainingCard1 = new TrainingCard("Exercises", 1, true, personalTrainer);
        TrainingCard trainingCard2 = new TrainingCard("Exercises", 2,true, personalTrainer);
        TrainingCard trainingCard3 = new TrainingCard("Exercises", 2,false, personalTrainer);

        trainingCard1.setEmission(LocalDate.of(2022, 4, 22));
        trainingCard2.setEmission(LocalDate.of(2022, 4, 22));
        trainingCard3.setEmission(LocalDate.of(2022, 4, 22));

        trainingCardDao.addTrainingCard(trainingCard1);
        trainingCardDao.addTrainingCard(trainingCard2);
        trainingCardDao.addTrainingCard(trainingCard3);

        ArrayList<TrainingCard> standardTrainingCards = new ArrayList<>();

        standardTrainingCards.add(trainingCard1);
        standardTrainingCards.add(trainingCard2);

        assertEquals(standardTrainingCards, personalTrainerController.getMyStandardTrainingCard());
    }

    @Test
    void addCustomizeTrainingCard() {
        PersonalTrainer personalTrainer = new PersonalTrainer("Tommaso",
                "Botarelli",
                "7576143571"
        );

        personalTrainerDao.add(personalTrainer);

        Customer customer = new Customer("Sandro", "Giusti", "7862345");

        personalTrainerController.setThisPersonalTrainer(personalTrainer);

        personalTrainerController.addCustomizeTrainingCard(
                customer, "Exercise", 1,
                22, 4, 2022, 22, 5, 2022,
                "TrainingCard"
        );

        assertFalse(trainingCardDao.getTrainingCardFromCustomer(customer).isEmpty());
    }

    @Test
    void addStandardTrainingCard(){
        PersonalTrainer personalTrainer = new PersonalTrainer("Tommaso", "Botarelli", "7576143571");

        personalTrainerDao.add(personalTrainer);

        personalTrainerController.setThisPersonalTrainer(personalTrainer);

        personalTrainerController.addStandardTrainingCard("Exercise", 1,"TrainingCard");

        ArrayList<TrainingCard> standardTrainingCard = new ArrayList<>();

        for (TrainingCard t : trainingCardDao.getTrainingCardFromPersonalTrainer(personalTrainer)){
            if (t.isStandard())
                standardTrainingCard.add(t);
        }

        assertEquals("Exercise", trainingCardDao.getTrainingCardFromPersonalTrainer(personalTrainer).get(0).getExercises());
    }

    @Test
    void addEvaluation() {
        PersonalTrainer personalTrainer = new PersonalTrainer("Tommaso", "Botarelli", "7576143571");

        personalTrainerDao.add(personalTrainer);

        Customer customer = new Customer("Sandro", "Giusti", "7862345");

        personalTrainerController.setThisPersonalTrainer(personalTrainer);

        personalTrainerController.addEvaluation(customer, 2022, 4, 22, "Everything is ok", 2,
                1.80f, 80.0f, 70.0f, 10.0f);

        assertFalse(evaluationDao.getEvaluationOfCustomer(customer).isEmpty());
    }

    @Test
    void getEvaluationOfCustomer() {
        PersonalTrainer personalTrainer = new PersonalTrainer("Tommaso", "Botarelli", "7576143571");

        personalTrainerDao.add(personalTrainer);

        Customer customer1 = new Customer("Sandro", "Giusti", "7862345");
        Customer customer2 = new Customer("Gianluca", "Righi", "4353241324");
        Customer customer3 = new Customer("Filippo", "Rossi", "5234312421");

        personalTrainerController.setThisPersonalTrainer(personalTrainer);

        Evaluation evaluation1 = new Evaluation(LocalDate.of(2022, 4, 22),
                new Measurement(1.80f, 80.0f, 70.0f, 10.0f), customer1);
        Evaluation evaluation2 = new Evaluation(LocalDate.of(2022, 4, 22),
                new Measurement(1.80f, 80.0f, 70.0f, 10.0f), customer2);
        Evaluation evaluation3 = new Evaluation(LocalDate.of(2022, 4, 22),
                new Measurement(1.80f, 80.0f, 70.0f, 10.0f), customer3);

        evaluationDao.addEvaluation(evaluation1);
        evaluationDao.addEvaluation(evaluation2);
        evaluationDao.addEvaluation(evaluation3);

        ArrayList<Evaluation> evaluationsOfCustomer1 = personalTrainerController.getEvaluationOfCustomer(customer1);
        ArrayList<Evaluation> evaluationsOfCustomer2 = personalTrainerController.getEvaluationOfCustomer(customer2);
        ArrayList<Evaluation> evaluationsOfCustomer3 = personalTrainerController.getEvaluationOfCustomer(customer3);

        assertEquals(evaluation1, evaluationsOfCustomer1.get(0));
        assertEquals(evaluation2, evaluationsOfCustomer2.get(0));
        assertEquals(evaluation3, evaluationsOfCustomer3.get(0));
    }

    @Test
    void deleteEvaluation() {
        PersonalTrainer personalTrainer = new PersonalTrainer("Tommaso", "Botarelli", "7576143571");

        personalTrainerDao.add(personalTrainer);

        Customer customer1 = new Customer("Sandro", "Giusti", "7862345");
        Customer customer2 = new Customer("Gianluca", "Righi", "4353241324");
        Customer customer3 = new Customer("Filippo", "Rossi", "5234312421");

        personalTrainerController.setThisPersonalTrainer(personalTrainer);

        Evaluation evaluation1 = new Evaluation(LocalDate.of(2022, 4, 22),
                new Measurement(1.80f, 80.0f, 70.0f, 10.0f), customer1);
        Evaluation evaluation2 = new Evaluation(LocalDate.of(2022, 4, 22),
                new Measurement(1.80f, 80.0f, 70.0f, 10.0f), customer2);
        Evaluation evaluation3 = new Evaluation(LocalDate.of(2022, 4, 22),
                new Measurement(1.80f, 80.0f, 70.0f, 10.0f), customer3);

        evaluationDao.addEvaluation(evaluation1);
        evaluationDao.addEvaluation(evaluation2);
        evaluationDao.addEvaluation(evaluation3);

        personalTrainerController.deleteEvaluation(evaluation1);
        personalTrainerController.deleteEvaluation(evaluation2);
        personalTrainerController.deleteEvaluation(evaluation3);

        assertEquals(0, evaluationDao.getAllEvaluation().size());
    }

    @Test
    void copyTrainingCard(){
        PersonalTrainer personalTrainer = new PersonalTrainer("Tommaso", "Botarelli", "7576143571");

        personalTrainerDao.add(personalTrainer);

        personalTrainerController.setThisPersonalTrainer(personalTrainer);

        TrainingCard trainingCard = new TrainingCard(
                "Some exercises",
                1,
                true,
                personalTrainer
        );

        Customer customer1 = new Customer("Sandro", "Giusti", "7862345");

        personalTrainerController.copyTrainingCard(
                trainingCard,
                customer1,
                LocalDate.now(),
                LocalDate.now().plusMonths(1),
                "CopiedTrainingCard"
                );

        TrainingCard trainingCardCopied = new TrainingCard("Some exercises", 1, false, personalTrainer);
        trainingCardCopied.setName("CopiedTrainingCard");
        trainingCardCopied.setEmission(LocalDate.now());
        trainingCardCopied.setExpiration(LocalDate.now().plusMonths(1));
        trainingCardCopied.setCustomer(customer1);

        assertEquals(trainingCardCopied, trainingCardDao.getTrainingCardFromCustomer(customer1).get(0));
    }

    @Test
    void modifyExercises(){
        PersonalTrainer personalTrainer = new PersonalTrainer("Tommaso", "Botarelli", "7576143571");

        personalTrainerDao.add(personalTrainer);

        personalTrainerController.setThisPersonalTrainer(personalTrainer);

        TrainingCard trainingCard = new TrainingCard(
                "Some exercises",
                1,
                false,
                personalTrainer
        );

        Customer customer1 = new Customer("Sandro", "Giusti", "7862345");

        trainingCard.setCustomer(customer1);

        //Simulazione di una scheda che non si trova nel database

        try{
            personalTrainerController.modifyExercises("Some exercises", trainingCard);
        }
        catch (Exception e){
            assertEquals("La scheda di allenamento selezionata non esiste", e.getMessage());
        }

        trainingCardDao.addTrainingCard(trainingCard);

        try{
            personalTrainerController.modifyExercises("Modified exercises", trainingCard);
            assertEquals(trainingCard.getExercises(), trainingCardDao.getTrainingCardFromCustomer(customer1).get(0).getExercises());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}