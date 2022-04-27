package businessLogic;

import dao.factoryClass.DaoFactory;
import dao.interfaceClass.CostumerDao;
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
        Costumer goodCostumer = loginController.createCostumerSession("Tommaso", "Botarelli", "56751862798");
        assertNotNull(goodCostumer);

        Costumer badCostumer = loginController.createCostumerSession("Gianluca", "Rossi", "865736789");
        assertNull(badCostumer);
    }

    @Test
    void createReceptionistSession() {
        Receptionist goodReceptionist = loginController.createReceptionistSession("Gianluca", "Rossi", "865736789");
        assertNotNull(goodReceptionist);

        Receptionist badReceptionist = loginController.createReceptionistSession("Gianluca", "Biraghi", "67457687");
        assertNull(badReceptionist);
    }

    @Test
    void createPersonalTrainerSession() {
        PersonalTrainer goodPersonalTrainer = loginController.createPersonalTrainerSession("Tommaso", "Botarelli", "232453431");
        assertNotNull(goodPersonalTrainer);

        PersonalTrainer badPersonalTrainer = loginController.createPersonalTrainerSession("Gianluca", "Biraghi", "67457687");
        assertNull(badPersonalTrainer);
    }

    @Test
    void createGymManagerSession() {
        GymManager gymManagerStandard = loginController.createGymManagerSession("Default", "GymManager", "123456789");
        assertNotNull(gymManagerStandard);

        GymManager badGymManager = loginController.createGymManagerSession("Prova", "GymManager", "56753465768");
        assertNull(badGymManager);
    }
}