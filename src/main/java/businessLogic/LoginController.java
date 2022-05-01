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
    public CostumerController createCostumerSession(String name, String surname, String phoneNumber) throws Exception{
        Costumer user = costumerDao.getSelectedCostumer(name, surname, phoneNumber);
        if (user != null) {
            CostumerController controller = new CostumerController();
            controller.setCurrentUser(user);
            return controller;
        }
        else
            throw new Exception("A costumer with this data doesn't exist");
    }

    public ReceptionistController createReceptionistSession(String name, String surname, String phoneNumber) throws Exception{
        Receptionist user = receptionistDao.getReceptionistFromNameSurnamePhoneNumber(name, surname, phoneNumber);
        if (user != null){
            ReceptionistController controller = new ReceptionistController();
            controller.setCurrentReceptionist(user);
            return controller;
        }
        else
            throw new Exception("A receptionist with this data doesn't exist");
    }

    public PersonalTrainerController createPersonalTrainerSession(String name, String surname, String phoneNumber) throws Exception{
        PersonalTrainer user = personalTrainerDao.getPersonalTrainer(name, surname, phoneNumber);
        if (user != null){
            PersonalTrainerController controller = new PersonalTrainerController();
            controller.setThisPersonalTrainer(user);
            return controller;
        }
        else
            throw new Exception("A personal trainer with this data doesn't exist");
    }

    public GymManagerController createGymManagerSession(String name, String surname, String phoneNumber) throws Exception{
        GymManager user = gymManagerDao.getGymManager(name, surname, phoneNumber);
        if (user != null){
            GymManagerController controller = new GymManagerController();
            controller.setThisGymManager(user);
            return controller;
        }
        else
            throw new Exception("A gym manager with this data doesn't exist");
    }
}
