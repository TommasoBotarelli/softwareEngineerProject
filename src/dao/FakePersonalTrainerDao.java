package dao;

import domainModel.PersonalTrainer;

import java.util.ArrayList;

public class FakePersonalTrainerDao implements PersonalTrainerDao{

    ArrayList<PersonalTrainer> personalTrainers;

    public FakePersonalTrainerDao(){
        personalTrainers = new ArrayList<>();
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
}
