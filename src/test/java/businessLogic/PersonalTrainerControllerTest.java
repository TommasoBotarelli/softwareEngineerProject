package businessLogic;

import dao.*;
import domainModel.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PersonalTrainerControllerTest {

    private PersonalTrainerController personalTrainerController = new PersonalTrainerController();

    @BeforeEach
    void setUp(){
        FakePersonalTrainerDao.getInstance().deleteAll();
        FakeTrainingCardDao.getInstance().deleteAll();
    }

    @Test
    void setThisPersonalTrainer() {
        FakePersonalTrainerDao.getInstance().add(new PersonalTrainer("Tommaso", "Botarelli", "7576143571"));
        personalTrainerController.setThisPersonalTrainer("Tommaso", "Botarelli", "7576143571");
        assertEquals(FakePersonalTrainerDao.getInstance().getPersonalTrainer("Tommaso", "Botarelli", "7576143571"),
                personalTrainerController.getThisPersonalTrainer());
    }

    @Test
    void getMyStandardTrainingCard() {
        PersonalTrainer personalTrainer = new PersonalTrainer("Tommaso", "Botarelli", "7576143571");

        FakePersonalTrainerDao.getInstance().add(personalTrainer);

        personalTrainerController.setThisPersonalTrainer("Tommaso", "Botarelli", "7576143571");

        TrainingCard trainingCard1 = new TrainingCard("Exercises", 1, true, personalTrainer);
        TrainingCard trainingCard2 = new TrainingCard("Exercises", 2,true, personalTrainer);
        TrainingCard trainingCard3 = new TrainingCard("Exercises", 2,false, personalTrainer);

        trainingCard1.setEmission(LocalDate.of(2022, 4, 22));
        trainingCard2.setEmission(LocalDate.of(2022, 4, 22));
        trainingCard3.setEmission(LocalDate.of(2022, 4, 22));

        FakeTrainingCardDao.getInstance().addTrainingCard(trainingCard1);
        FakeTrainingCardDao.getInstance().addTrainingCard(trainingCard2);
        FakeTrainingCardDao.getInstance().addTrainingCard(trainingCard3);

        ArrayList<TrainingCard> standardTrainingCards = new ArrayList<>();

        standardTrainingCards.add(trainingCard1);
        standardTrainingCards.add(trainingCard2);

        assertEquals(standardTrainingCards, personalTrainerController.getMyStandardTrainingCard());
    }

    @Test
    void addCustomizeTrainingCard() {
        PersonalTrainer personalTrainer = new PersonalTrainer("Tommaso", "Botarelli", "7576143571");

        FakePersonalTrainerDao.getInstance().add(personalTrainer);

        Costumer costumer = new Costumer("Sandro", "Giusti", "7862345");

        personalTrainerController.setThisPersonalTrainer("Tommaso", "Botarelli", "7576143571");

        personalTrainerController.addCustomizeTrainingCard(costumer, "Exercise", 1,
                22, 4, 2022, 22, 5, 2022);

        assertFalse(FakeTrainingCardDao.getInstance().getTrainingCardFromCostumer(costumer).isEmpty());
    }

    @Test
    void addStandardTrainingCard(){
        PersonalTrainer personalTrainer = new PersonalTrainer("Tommaso", "Botarelli", "7576143571");

        FakePersonalTrainerDao.getInstance().add(personalTrainer);

        personalTrainerController.setThisPersonalTrainer("Tommaso", "Botarelli", "7576143571");

        personalTrainerController.addStandardTrainingCard("Exercise", 1);

        ArrayList<TrainingCard> standardTrainingCard = new ArrayList<>();

        for (TrainingCard t : FakeTrainingCardDao.getInstance().getTrainingCardFromPersonalTrainer(personalTrainer)){
            if (t.isStandard())
                standardTrainingCard.add(t);
        }

        assertEquals("Exercise", FakeTrainingCardDao.getInstance().getTrainingCardFromPersonalTrainer(personalTrainer).get(0).getExercises());
    }

    @Test
    void addEvaluation() {
        PersonalTrainer personalTrainer = new PersonalTrainer("Tommaso", "Botarelli", "7576143571");

        FakePersonalTrainerDao.getInstance().add(personalTrainer);

        Costumer costumer = new Costumer("Sandro", "Giusti", "7862345");

        personalTrainerController.setThisPersonalTrainer("Tommaso", "Botarelli", "7576143571");

        personalTrainerController.addEvaluation(costumer, 2022, 4, 22, "Everything is ok", 2,
                1.80f, 80.0f, 70.0f, 10.0f);

        assertFalse(FakeEvaluationDao.getInstance().getEvaluationOfCostumer(costumer).isEmpty());
    }

    @Test
    void getEvaluationOfCostumer() {
        PersonalTrainer personalTrainer = new PersonalTrainer("Tommaso", "Botarelli", "7576143571");

        FakePersonalTrainerDao.getInstance().add(personalTrainer);

        Costumer costumer1 = new Costumer("Sandro", "Giusti", "7862345");
        Costumer costumer2 = new Costumer("Gianluca", "Righi", "4353241324");
        Costumer costumer3 = new Costumer("Filippo", "Rossi", "5234312421");

        personalTrainerController.setThisPersonalTrainer("Tommaso", "Botarelli", "7576143571");

        Evaluation evaluation1 = new Evaluation(LocalDate.of(2022, 4, 22),
                new Measurement(1.80f, 80.0f, 70.0f, 10.0f), costumer1);
        Evaluation evaluation2 = new Evaluation(LocalDate.of(2022, 4, 22),
                new Measurement(1.80f, 80.0f, 70.0f, 10.0f), costumer2);
        Evaluation evaluation3 = new Evaluation(LocalDate.of(2022, 4, 22),
                new Measurement(1.80f, 80.0f, 70.0f, 10.0f), costumer3);

        FakeEvaluationDao.getInstance().addEvaluation(evaluation1);
        FakeEvaluationDao.getInstance().addEvaluation(evaluation2);
        FakeEvaluationDao.getInstance().addEvaluation(evaluation3);

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

        FakePersonalTrainerDao.getInstance().add(personalTrainer);

        Costumer costumer1 = new Costumer("Sandro", "Giusti", "7862345");
        Costumer costumer2 = new Costumer("Gianluca", "Righi", "4353241324");
        Costumer costumer3 = new Costumer("Filippo", "Rossi", "5234312421");

        personalTrainerController.setThisPersonalTrainer("Tommaso", "Botarelli", "7576143571");

        Evaluation evaluation1 = new Evaluation(LocalDate.of(2022, 4, 22),
                new Measurement(1.80f, 80.0f, 70.0f, 10.0f), costumer1);
        Evaluation evaluation2 = new Evaluation(LocalDate.of(2022, 4, 22),
                new Measurement(1.80f, 80.0f, 70.0f, 10.0f), costumer2);
        Evaluation evaluation3 = new Evaluation(LocalDate.of(2022, 4, 22),
                new Measurement(1.80f, 80.0f, 70.0f, 10.0f), costumer3);

        FakeEvaluationDao.getInstance().addEvaluation(evaluation1);
        FakeEvaluationDao.getInstance().addEvaluation(evaluation2);
        FakeEvaluationDao.getInstance().addEvaluation(evaluation3);

        personalTrainerController.deleteEvaluation(evaluation1);
        personalTrainerController.deleteEvaluation(evaluation2);
        personalTrainerController.deleteEvaluation(evaluation3);

        assertEquals(0, FakeEvaluationDao.getInstance().getAllEvaluation().size());
    }
}