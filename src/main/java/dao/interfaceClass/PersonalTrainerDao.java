package dao.interfaceClass;

import domainModel.PersonalTrainer;
import domainModel.TrainingCard;

import java.util.ArrayList;

public interface PersonalTrainerDao {
    void add(PersonalTrainer personalTrainer);
    void delete(PersonalTrainer personalTrainer);
    ArrayList<PersonalTrainer> getAllPersonalTrainers();
    PersonalTrainer getPersonalTrainer(String name, String surname, String phoneNumber);
    void deleteAll();
}
