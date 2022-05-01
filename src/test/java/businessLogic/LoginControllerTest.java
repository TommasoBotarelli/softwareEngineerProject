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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest {

    private LoginController loginController = new LoginController();

    private CostumerDao costumerDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getCostumerDao();
    private ReceptionistDao receptionistDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getReceptionistDao();
    private PersonalTrainerDao personalTrainerDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getPersonalTrainerDao();
    private GymManagerDao gymManagerDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getGymManagerDao();

    @BeforeEach
    void setUp(){
        Costumer costumer1 = new Costumer("Tommaso", "Botarelli", "56751862798");
        Costumer costumer2 = new Costumer("Marco", "De Luca", "8935131");
        Costumer costumer3 = new Costumer("Luigi", "Bianchi", "8975574657689");

        costumerDao.add(costumer1);
        costumerDao.add(costumer2);
        costumerDao.add(costumer3);

        Receptionist receptionist1 = new Receptionist("Gianluca", "Rossi", "865736789");
        Receptionist receptionist2 = new Receptionist("Filippo", "Bianchi", "5234124124");

        receptionistDao.addReceptionist(receptionist1);
        receptionistDao.addReceptionist(receptionist2);

        PersonalTrainer personalTrainer1 = new PersonalTrainer("Tommaso", "Botarelli", "232453431");
        PersonalTrainer personalTrainer2 = new PersonalTrainer("Filippo", "Bianchi", "5234124124");

        personalTrainerDao.add(personalTrainer1);
        personalTrainerDao.add(personalTrainer2);
    }

    @Test
    void createCostumerSession() {
        try {
            CostumerController goodCostumer = loginController.createCostumerSession("Tommaso", "Botarelli", "56751862798");
            assertNotNull(goodCostumer);
            CostumerController badCostumer = loginController.createCostumerSession("Gianluca", "Rossi", "865736789");
        }
        catch (Exception e){
            assertEquals("A costumer with this data doesn't exist", e.getMessage());
        }
    }

    @Test
    void createReceptionistSession() {
        try {
            ReceptionistController goodReceptionist = loginController.createReceptionistSession("Gianluca", "Rossi", "865736789");
            assertNotNull(goodReceptionist);
            ReceptionistController badReceptionist = loginController.createReceptionistSession("Marco", "De Luca", "8935131");
        }
        catch (Exception e){
            assertEquals("A receptionist with this data doesn't exist", e.getMessage());
        }
    }

    @Test
    void createPersonalTrainerSession() {
        try {
            PersonalTrainerController goodPersonalTrainer = loginController.createPersonalTrainerSession("Tommaso", "Botarelli", "232453431");
            assertNotNull(goodPersonalTrainer);
            PersonalTrainerController badPersonalTrainer = loginController.createPersonalTrainerSession("Marco", "De Luca", "8935131");
        }
        catch (Exception e){
            assertEquals("A personal trainer with this data doesn't exist", e.getMessage());
        }
    }

    @Test
    void createGymManagerSession() {
        try {
            GymManagerController goodGymManager = loginController.createGymManagerSession("name", "surname", "");
            assertNotNull(goodGymManager);
            GymManagerController badGymManager = loginController.createGymManagerSession("Marco", "De Luca", "8935131");
        }
        catch (Exception e){
            assertEquals("A gym manager with this data doesn't exist", e.getMessage());
        }
    }
}