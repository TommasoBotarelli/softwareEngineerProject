package businessLogic;

import dao.factoryClass.DaoFactory;
import dao.interfaceClass.CustomerDao;
import dao.interfaceClass.GymManagerDao;
import dao.interfaceClass.PersonalTrainerDao;
import dao.interfaceClass.ReceptionistDao;
import domainModel.Customer;
import domainModel.PersonalTrainer;
import domainModel.Receptionist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest {

    private LoginController loginController = new LoginController();

    private CustomerDao customerDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getCustomerDao();
    private ReceptionistDao receptionistDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getReceptionistDao();
    private PersonalTrainerDao personalTrainerDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getPersonalTrainerDao();
    private GymManagerDao gymManagerDao = Objects.requireNonNull(DaoFactory.getDaoFactory(1)).getGymManagerDao();

    @BeforeEach
    void setUp(){
        Customer customer1 = new Customer("Tommaso", "Botarelli", "56751862798");
        Customer customer2 = new Customer("Marco", "De Luca", "8935131");
        Customer customer3 = new Customer("Luigi", "Bianchi", "8975574657689");

        customerDao.add(customer1);
        customerDao.add(customer2);
        customerDao.add(customer3);

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
    void createCustomerSession() {
        try {
            CustomerController goodCustomer = loginController.createCustomerSession("Tommaso",
                    "Botarelli",
                    "56751862798"
            );
            assertNotNull(goodCustomer);
            CustomerController badCustomer = loginController.createCustomerSession("Gianluca",
                    "Rossi",
                    "865736789"
            );
        }
        catch (Exception e){
            assertEquals("A Customer with this data doesn't exist", e.getMessage());
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