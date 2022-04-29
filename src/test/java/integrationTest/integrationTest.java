package integrationTest;

import businessLogic.GymManagerController;
import businessLogic.LoginController;
import businessLogic.ReceptionistController;
import domainModel.GymManager;
import domainModel.Receptionist;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/*
TODO
    the login page uses the other controllers not a special one, so you must remove the loginController and
    when a user want to access the loginWindow uses directly the controller of the type of user!!!
 */

public class integrationTest {

    private static LoginController loginWindow = new LoginController();
    private static GymManagerController gymManagerWindow = new GymManagerController();
    private static ReceptionistController receptionistWindow = new ReceptionistController();

    @BeforeAll
    static void createGym() {
        /*
        For the moment there is only a default gymManager. He wants to create a new GymManager "account". He uses the
        default gymManager account to create his own account. First step access to system with the default "login", with
        the combobox on "Gym Manager".
         */
        boolean result = gymManagerWindow.setCurrentUser("name", "surname", "");

        /*
        From the presentation layer is possible to:
        1- Show an error dialog if result is false;
        2- Show the main window if result is true;
         */

        /*
        if (!result)
            show error dialog
         */

        /*
        He creates an own "user".
         */
         gymManagerWindow.addGymManager("Tommaso", "Botarelli", "123456789");

         /*
         He tries again the "login".
          */
        gymManagerWindow.setCurrentUser("Tommaso", "Botarelli", "123456789");

        /*
        He adds a receptionist and a personalTrainer.
         */

        gymManagerWindow.addReceptionist("Marco", "Bianchi", "174676878");
        gymManagerWindow.addPersonalTrainer("Elia", "Rossi", "345678909");

        /*
        A costumer come to the gym, the receptionist Marco "login" and add the costumer.
         */

        receptionistWindow.setCurrentUser("Marco", "Bianchi", "174676878");
        receptionistWindow.addCostumer("Marco", "De Luca", "3456789087");

        /*
        Then the costumer want to start immediately the trial subscription. The receptionist add this to the system.
         */
    }

    @Test
    void firstAccessOfCostumer() {
        /*
        The receptionist Marco Bianchi access to the system and want to register the new costumer.
         */
        Receptionist receptionist = loginWindow.createReceptionistSession("Marco", "Bianchi", "174676878");

        /*
        if (gymManager == null)
            show error dialog
         */
    }
}
