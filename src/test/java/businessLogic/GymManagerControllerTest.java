package businessLogic;

import dao.*;
import domainModel.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GymManagerControllerTest {

    private GymManagerController gymManagerController = new GymManagerController();

    private static ArrayList<PersonalTrainer> personalTrainers = new ArrayList<>();
    private static ArrayList<Receptionist> receptionists = new ArrayList<>();
    private static ArrayList<Costumer> costumers = new ArrayList<>();

    @BeforeAll
    static void setUp(){
        LocalDate actualDate = LocalDate.of(2022, 4, 22);
        LocalDate pastDate = LocalDate.of(2022, 4, 21);
        LocalDate futureDate = LocalDate.of(2022, 5, 10);

        PersonalTrainer personalTrainer1 = new PersonalTrainer("Mario", "Rossi", "123456789");
        PersonalTrainer personalTrainer2 = new PersonalTrainer("Marco", "Bianchi", "24681012");
        personalTrainers.add(personalTrainer1);
        personalTrainers.add(personalTrainer2);
        PersonalTrainerDao personalTrainerDao = FakePersonalTrainerDao.getInstance();
        personalTrainerDao.add(personalTrainer1);
        personalTrainerDao.add(personalTrainer2);
        Costumer costumer1 = new Costumer("Tommaso", "Botarelli", "8163582");
        Costumer costumer2 = new Costumer("Marco", "Verdi", "826724617");
        costumers.add(costumer1);
        costumers.add(costumer2);
        Receptionist receptionist1 = new Receptionist("Laura", "Rossi", "186753187");
        Receptionist receptionist2 = new Receptionist("Maria", "Neri", "9828375725");
        receptionists.add(receptionist1);
        receptionists.add(receptionist2);

        Bill bill1c1 = new Bill(200, actualDate);
        Bill bill1c2 = new Bill(200, futureDate);
        FakeBillDao.getInstance().add(bill1c1);
        FakeBillDao.getInstance().add(bill1c2);

        Subscription freeSubC1 = new Subscription(pastDate, TypeOfSub.PROVA, costumer1);
        Subscription freeSubC2 = new Subscription(pastDate, TypeOfSub.PROVA, costumer2);

        Subscription paySubC1 = new Subscription(actualDate, TypeOfSub.SEMESTRALE, costumer1);
        paySubC1.setBillID(bill1c1.getId());

        Subscription paySubC2 = new Subscription(futureDate, TypeOfSub.MENSILE, costumer2);
        paySubC2.setBillID(bill1c2.getId());

        TrainingCard standardTrainingCardPT1 = new TrainingCard("Some exercises", 1, pastDate, true, personalTrainer1);
        TrainingCard standardTrainingCardPT2 = new TrainingCard("Some exercises", 2, pastDate, true, personalTrainer2);

        TrainingCard customizeTrainingCardPT1 = new TrainingCard("Some exercise", 2, actualDate, false, personalTrainer1);
        TrainingCard customizeTrainingCardPT2 = new TrainingCard("Some exercise", 3, actualDate, false, personalTrainer1);
        customizeTrainingCardPT1.setCostumer(costumer1);
        customizeTrainingCardPT2.setCostumer(costumer2);

        TrainingCardDao trainingCardDao = FakeTrainingCardDao.getInstance();
        trainingCardDao.addTrainingCard(customizeTrainingCardPT1);
        trainingCardDao.addTrainingCard(customizeTrainingCardPT2);

        Evaluation evaluation1C1 = new Evaluation(actualDate, new Measurement(1.85f, 80.0f, 70.0f, 10.0f), costumer1);
        Evaluation evaluation1C2 = new Evaluation(actualDate, new Measurement(1.90f, 83.0f, 70.0f, 13.0f), costumer2);
        EvaluationDao evaluationDao = FakeEvaluationDao.getInstance();
        evaluationDao.addEvaluation(evaluation1C1);
        evaluationDao.addEvaluation(evaluation1C2);
    }

    @Test
    void getAllPersonalTrainer() {
        ArrayList<PersonalTrainer> allPersonalTrainer = gymManagerController.getAllPersonalTrainer();
        assertEquals(allPersonalTrainer, personalTrainers);
    }

    @Test
    void addPersonalTrainer() {
        gymManagerController.addPersonalTrainer("Test", "Test", "87385723721");
        PersonalTrainerDao personalTrainerDao = FakePersonalTrainerDao.getInstance();
        assertEquals(new PersonalTrainer("Test", "Test", "87385723721"), personalTrainerDao.getPersonalTrainer("Test", "Test", "87385723721"));
    }

    @Test
    void deletePersonalTrainer() {
        PersonalTrainer deletePersonalTrainer = new PersonalTrainer("Test", "Test", "87385723721");
        gymManagerController.deletePersonalTrainer(deletePersonalTrainer);
        PersonalTrainerDao personalTrainerDao = FakePersonalTrainerDao.getInstance();
        ArrayList<PersonalTrainer> allPersonalTrainers = personalTrainerDao.getAllPersonalTrainers();
        assertFalse(allPersonalTrainers.contains(deletePersonalTrainer));
    }

    @Test
    void getAllBills() {
        ArrayList<Bill> allBills = gymManagerController.getAllBills();
        assertEquals(2, allBills.size());
        assertEquals(allBills.get(0).getId(), FakeBillDao.getInstance().getAll().get(0).getId());
        assertEquals(allBills.get(1).getId(), FakeBillDao.getInstance().getAll().get(1).getId());
    }

    @Test
    void getBillsOfTheDay() {
        ArrayList<Bill> billsOfTheDay = gymManagerController.getBillsOfTheDay(22, 4, 2022);
        assertEquals(billsOfTheDay.get(0).getId(), FakeBillDao.getInstance().getAll().get(0).getId());
    }

    @Test
    void getAllAccess() {
        LocalDate actualDate = LocalDate.of(2022, 4, 22);

        Costumer costumer1 = new Costumer("Prova", "Test", "863715361");
        Costumer costumer2 = new Costumer("Ludovico", "Siciliani", "976425842");

        Access access1 = new Access(costumer1, actualDate, true);
        Access access2 = new Access(costumer2, actualDate, true);
        Access access3 = new Access(costumer2, actualDate.plusDays(2), false);
        Access access4 = new Access(costumer2, actualDate.plusDays(1), true);

        FakeAccessDao.getInstance().add(access1);
        FakeAccessDao.getInstance().add(access2);
        FakeAccessDao.getInstance().add(access3);
        FakeAccessDao.getInstance().add(access4);

        ArrayList<Access> allAccess = gymManagerController.getAllAccess();

        assertEquals(access1, allAccess.get(0));
        assertEquals(access2, allAccess.get(1));
        assertEquals(access3, allAccess.get(2));
        assertEquals(access4, allAccess.get(3));
    }

    @Test
    void getAllAccessFromDate() {
    }

    @Test
    void getAllCostumers() {
    }

    @Test
    void getSubOfCostumer() {
    }

    @Test
    void getBillOfSub() {
    }

    @Test
    void getAllReceptionist() {
    }

    @Test
    void addReceptionist() {
    }

    @Test
    void deleteReceptionist() {
    }
}