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
        PersonalTrainer personalTrainer = new PersonalTrainer("Tommaso", "Botarelli", "7576143571");

        personalTrainerDao.add(personalTrainer);

        Costumer costumer = new Costumer("Sandro", "Giusti", "7862345");

        personalTrainerController.setThisPersonalTrainer(personalTrainer);

        personalTrainerController.addCustomizeTrainingCard(costumer, "Exercise", 1,
                22, 4, 2022, 22, 5, 2022);

        assertFalse(trainingCardDao.getTrainingCardFromCostumer(costumer).isEmpty());
    }

    @Test
    void addStandardTrainingCard(){
        PersonalTrainer personalTrainer = new PersonalTrainer("Tommaso", "Botarelli", "7576143571");

        personalTrainerDao.add(personalTrainer);

        personalTrainerController.setThisPersonalTrainer(personalTrainer);

        personalTrainerController.addStandardTrainingCard("Exercise", 1);

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

        Costumer costumer = new Costumer("Sandro", "Giusti", "7862345");

        personalTrainerController.setThisPersonalTrainer(personalTrainer);

        personalTrainerController.addEvaluation(costumer, 2022, 4, 22, "Everything is ok", 2,
                1.80f, 80.0f, 70.0f, 10.0f);

        assertFalse(evaluationDao.getEvaluationOfCostumer(costumer).isEmpty());
    }

    @Test
    void getEvaluationOfCostumer() {
        PersonalTrainer personalTrainer = new PersonalTrainer("Tommaso", "Botarelli", "7576143571");

        personalTrainerDao.add(personalTrainer);

        Costumer costumer1 = new Costumer("Sandro", "Giusti", "7862345");
        Costumer costumer2 = new Costumer("Gianluca", "Righi", "4353241324");
        Costumer costumer3 = new Costumer("Filippo", "Rossi", "5234312421");

        personalTrainerController.setThisPersonalTrainer(personalTrainer);

        Evaluation evaluation1 = new Evaluation(LocalDate.of(2022, 4, 22),
                new Measurement(1.80f, 80.0f, 70.0f, 10.0f), costumer1);
        Evaluation evaluation2 = new Evaluation(LocalDate.of(2022, 4, 22),
                new Measurement(1.80f, 80.0f, 70.0f, 10.0f), costumer2);
        Evaluation evaluation3 = new Evaluation(LocalDate.of(2022, 4, 22),
                new Measurement(1.80f, 80.0f, 70.0f, 10.0f), costumer3);

        evaluationDao.addEvaluation(evaluation1);
        evaluationDao.addEvaluation(evaluation2);
        evaluationDao.addEvaluation(evaluation3);

        ArrayList<Evaluation> evaluationsOfCostumer1 = personalTrainerController.getEvaluationOfCostumer(costumer1);
        ArrayList<Evaluation> evaluationsOfCostumer2 = personalTrainerController.getEvaluationOfCostumer(costumer2);
        ArrayList<Evaluation> evaluationsOfCostumer3 = personalTrainerController.getEvaluationOfCostumer(costumer3);

        assertEquals(evaluation1, evaluationsOfCostumer1.get(0));
        assertEquals(evaluation2, evaluationsOfCostumer2.get(0));
        assertEquals(evaluation3, evaluationsOfCostumer3.get(0));
    }

    @Test
    void deleteEvaluation() {
        PersonalTrainer personalTrainer = new PersonalTrainer("Tommaso", "Botarelli", "7576143571");

        personalTrainerDao.add(personalTrainer);

        Costumer costumer1 = new Costumer("Sandro", "Giusti", "7862345");
        Costumer costumer2 = new Costumer("Gianluca", "Righi", "4353241324");
        Costumer costumer3 = new Costumer("Filippo", "Rossi", "5234312421");

        personalTrainerController.setThisPersonalTrainer(personalTrainer);

        Evaluation evaluation1 = new Evaluation(LocalDate.of(2022, 4, 22),
                new Measurement(1.80f, 80.0f, 70.0f, 10.0f), costumer1);
        Evaluation evaluation2 = new Evaluation(LocalDate.of(2022, 4, 22),
                new Measurement(1.80f, 80.0f, 70.0f, 10.0f), costumer2);
        Evaluation evaluation3 = new Evaluation(LocalDate.of(2022, 4, 22),
                new Measurement(1.80f, 80.0f, 70.0f, 10.0f), costumer3);

        evaluationDao.addEvaluation(evaluation1);
        evaluationDao.addEvaluation(evaluation2);
        evaluationDao.addEvaluation(evaluation3);

        personalTrainerController.deleteEvaluation(evaluation1);
        personalTrainerController.deleteEvaluation(evaluation2);
        personalTrainerController.deleteEvaluation(evaluation3);

        assertEquals(0, evaluationDao.getAllEvaluation().size());
    }
}