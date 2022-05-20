package businessLogic;

import dao.concreteClass.FakeCustomerDao;
import dao.concreteClass.FakeEvaluationDao;
import dao.concreteClass.FakePersonalTrainerDao;
import dao.concreteClass.FakeTrainingCardDao;
import dao.interfaceClass.CustomerDao;
import dao.interfaceClass.EvaluationDao;
import dao.interfaceClass.PersonalTrainerDao;
import dao.interfaceClass.TrainingCardDao;
import domainModel.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class PersonalTrainerController {
    private PersonalTrainer thisPersonalTrainer;

    private PersonalTrainerDao personalTrainerDao;
    private TrainingCardDao trainingCardDao;
    private CustomerDao customerDao;
    private EvaluationDao evaluationDao;

    public PersonalTrainerController(){
        personalTrainerDao = FakePersonalTrainerDao.getInstance();
        trainingCardDao = FakeTrainingCardDao.getInstance();
        customerDao = FakeCustomerDao.getInstance();
        evaluationDao = FakeEvaluationDao.getInstance();
    }

    public void setThisPersonalTrainer(PersonalTrainer personalTrainer){
        this.thisPersonalTrainer = personalTrainer;
    }

    public ArrayList<TrainingCard> getMyStandardTrainingCard(){
        ArrayList<TrainingCard> allMyTrainingCard = trainingCardDao.getTrainingCardFromPersonalTrainer(thisPersonalTrainer);
        ArrayList<TrainingCard> standardTrainingCard = new ArrayList<>();

        for (TrainingCard t : allMyTrainingCard){
            if (t.isStandard())
                standardTrainingCard.add(t);
        }

        return standardTrainingCard;
    }

    public void addCustomizeTrainingCard(Customer customer, String exercises, int level, int emissionDay, int emissionMonth, int
                                emissionYear, int expirationDay, int expirationMonth, int expirationYear, String name){

        TrainingCard trainingCard = new TrainingCard(exercises, level,false, thisPersonalTrainer);

        trainingCard.setCustomer(customer);
        trainingCard.setEmission(LocalDate.of(emissionYear, emissionMonth, emissionDay));
        trainingCard.setExpiration(LocalDate.of(expirationYear, expirationMonth, expirationDay));
        if(!name.isEmpty())
            trainingCard.setName(name);

        trainingCardDao.addTrainingCard(trainingCard);
    }

    public void addStandardTrainingCard(String exercises, int level, String name){
        TrainingCard trainingCard = new TrainingCard(exercises, level, true, thisPersonalTrainer);

        if(!name.isEmpty())
            trainingCard.setName(name);

        trainingCardDao.addTrainingCard(trainingCard);
    }

    public ArrayList<Customer> getAllCustomer(){
        return customerDao.getAll();
    }

    public ArrayList<Customer> findCustomer(String name, String surname){
        return customerDao.getFromNameSurname(name, surname);
    }

    public void addEvaluation(Customer customer, int year, int month, int day, String comments, int progressLevel, float height,
                              float weight, float leanMass, float fatMass){
        LocalDate date = LocalDate.of(year, month, day);
        Measurement measurement = new Measurement(height, weight, leanMass, fatMass);
        Evaluation evaluation = new Evaluation(date, measurement, customer);
        evaluation.setPersonalTrainer(thisPersonalTrainer);
        evaluationDao.addEvaluation(evaluation);
    }

    public ArrayList<Evaluation> getEvaluationOfCustomer(Customer customer){
        return evaluationDao.getEvaluationOfCustomer(customer);
    }

    public void deleteEvaluation(Evaluation evaluation){
        evaluationDao.deleteEvaluation(evaluation);
    }

    public PersonalTrainer getThisPersonalTrainer(){
        return thisPersonalTrainer;
    }

    public ArrayList<TrainingCard> getMyTrainingCard(){
        return trainingCardDao.getTrainingCardFromPersonalTrainer(thisPersonalTrainer);
    }

    public void copyTrainingCard(TrainingCard trainingCard, Customer customer, LocalDate emission, LocalDate expiration, String name){
        TrainingCard trainingCardCopied = new TrainingCard(trainingCard);
        trainingCardCopied.setCustomer(customer);
        trainingCardCopied.setEmission(emission);
        trainingCardCopied.setExpiration(expiration);
        trainingCardCopied.setStandard(false);
        if (name.isEmpty())
            trainingCardCopied.setName(trainingCard.getName() + " (COPIED)");
        trainingCardCopied.setName(name);
        trainingCardDao.addTrainingCard(trainingCardCopied);
    }

    public void modifyExercises(String exercises, TrainingCard trainingCard) throws Exception{
        boolean result = trainingCardDao.deleteTrainingCard(trainingCard);
        if (result) {
            trainingCard.setExercises(exercises);
            trainingCardDao.addTrainingCard(trainingCard);
        }
        else
            throw new Exception("La scheda di allenamento selezionata non esiste");
    }

    public Customer selectCustomer(String name, String surname, String phoneNumber){
        return customerDao.getSelectedCustomer(name, surname, phoneNumber);
    }

    public TrainingCard getMyDefaultTrainingCard(int progressLevel){
        ArrayList<TrainingCard> defaultTrainingCard = trainingCardDao.getTrainingCardFromPersonalTrainer(thisPersonalTrainer);

        for (TrainingCard t : defaultTrainingCard){
            if (t.isStandard() && t.getLevel()==progressLevel)
                return t;
        }

        return null;
    }
}
