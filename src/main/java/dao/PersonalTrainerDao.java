package dao;

import domainModel.PersonalTrainer;

import java.util.ArrayList;

public interface PersonalTrainerDao {
    void add(PersonalTrainer personalTrainer);
    void delete(PersonalTrainer personalTrainer);
    ArrayList<PersonalTrainer> getAllPersonalTrainers();
    PersonalTrainer getPersonalTrainer(String name, String surname, String phoneNumber);
}
