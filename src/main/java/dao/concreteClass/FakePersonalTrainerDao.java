package dao.concreteClass;

import dao.interfaceClass.PersonalTrainerDao;
import domainModel.PersonalTrainer;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class FakePersonalTrainerDao implements PersonalTrainerDao {

    private ArrayList<PersonalTrainer> personalTrainers;
    private static FakePersonalTrainerDao instance = null;

    private FakePersonalTrainerDao(){
        personalTrainers = new ArrayList<>();
    }

    public static FakePersonalTrainerDao getInstance(){
        if (instance == null){
            instance = new FakePersonalTrainerDao();
        }
        return instance;
    }

    @Override
    public void add(PersonalTrainer personalTrainer) {
        personalTrainers.add(personalTrainer);
    }

    @Override
    public void delete(PersonalTrainer personalTrainer) {
        personalTrainers.remove(personalTrainer);
    }

    @Override
    public ArrayList<PersonalTrainer> getAllPersonalTrainers() {
        return personalTrainers;
    }

    @Override
    public PersonalTrainer getPersonalTrainer(String name, String surname, String phoneNumber) {
        if (personalTrainers.contains(new PersonalTrainer(name, surname, phoneNumber))){
            return personalTrainers.stream().filter
                    (personalTrainer -> personalTrainer.equals(new PersonalTrainer(name, surname, phoneNumber)))
                    .collect(Collectors.toList()).get(0);
        }
        else
            return null;
    }

    @Override
    public void deleteAll() {
        personalTrainers.clear();
    }
}
