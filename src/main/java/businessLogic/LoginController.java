package businessLogic;

import dao.factoryClass.DaoFactory;
import dao.interfaceClass.CostumerDao;
import dao.interfaceClass.GymManagerDao;
import dao.interfaceClass.PersonalTrainerDao;
import dao.interfaceClass.ReceptionistDao;
import domainModel.Costumer;
import domainModel.GymManager;
import domainModel.PersonalTrainer;
import domainModel.Receptionist;

import java.util.Objects;

public class LoginController {
    private CostumerDao costumerDao;
    private ReceptionistDao receptionistDao;
    private PersonalTrainerDao personalTrainerDao;
    private GymManagerDao gymManagerDao;

    public LoginController(){
        costumerDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getCostumerDao();
        receptionistDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getReceptionistDao();
        personalTrainerDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getPersonalTrainerDao();
        gymManagerDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getGymManagerDao();
    }

    public Costumer createCostumerSession(String name, String surname, String phoneNumber){
        return costumerDao.getSelectedCostumer(name, surname, phoneNumber);
    }

    public Receptionist createReceptionistSession(String name, String surname, String phoneNumber){
        return receptionistDao.getReceptionistFromNameSurnamePhoneNumber(name, surname, phoneNumber);
    }

    public PersonalTrainer createPersonalTrainerSession(String name, String surname, String phoneNumber){
        return personalTrainerDao.getPersonalTrainer(name, surname, phoneNumber);
    }

    public GymManager createGymManagerSession(String name, String surname, String phoneNumber){
        return gymManagerDao.getGymManager(name, surname, phoneNumber);
    }
}
