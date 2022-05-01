package integrationTest;

import businessLogic.*;
import domainModel.GymManager;
import domainModel.Receptionist;
import domainModel.TrainingCard;
import domainModel.TypeOfSub;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/*
TODO
    the login page uses the other controllers not a special one, so you must remove the loginController and
    when a user want to access the loginWindow uses directly the controller of the type of user!!!
 */

public class integrationTest {

    private LoginController loginWindow = new LoginController();
    private GymManagerController gymManagerWindow;
    private ReceptionistController receptionistWindow;
    private PersonalTrainerController personalTrainerWindow;
    private CostumerController costumerWindow;

    @Test
    void testOfTheSystem() {
        /*
        For the moment there is only a default gymManager. He wants to create a new GymManager "account". He uses the
        default gymManager account to create his own account. First step access to system with the default "login", with
        the combobox on "Gym Manager".
         */
        try {
            gymManagerWindow = loginWindow.createGymManagerSession("name", "surname", "");
        }
        catch(Exception e){
            System.out.println(e.getMessage() + " NO STAMPA");
        }

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
        try {
            gymManagerWindow = loginWindow.createGymManagerSession("name", "surname", "");
        }
        catch(Exception e){
            System.out.println(e.getMessage() + " NO STAMPA");
        }

        /*
        He adds a receptionist and a personalTrainer.
         */

        gymManagerWindow.addReceptionist("Marco", "Bianchi", "174676878");
        gymManagerWindow.addPersonalTrainer("Elia", "Rossi", "345678909");

        /*
        A costumer come to the gym, the receptionist Marco "login" and add the costumer.
         */

        try {
            receptionistWindow = loginWindow.createReceptionistSession("Marco", "Bianchi", "174676878");
        }
        catch(Exception e){
            System.out.println(e.getMessage() + " NO STAMPA");
        }

        receptionistWindow.addCostumer("Marco", "De Luca", "3456789087");

        /*
        Then the costumer want to start immediately the trial subscription. The receptionist add this to the system.
         */
        receptionistWindow.addAccessType("subscription", "trial", 0, LocalDate.now(),
                receptionistWindow.selectCostumer("Marco", "De Luca", "3456789087"));

        /*
        The receptionist release a new badge to the costumer and register this into the system.
         */

        receptionistWindow.releaseBadge(receptionistWindow.selectCostumer("Marco", "De Luca", "3456789087"));

        /*
        The costumer now can access to the gym and now uses the badge. In the after line of code we suppose that the sensor
        recognize the id of the badge.
         */

        try{
            receptionistWindow.addAccessForCostumerFromBadge(0, LocalDateTime.now());
        }
        catch(Exception e){
            /*
            The system show the error dialog if the badge is not registered into the database, or if
            the costumer can't access to the gym
             */
            System.out.println(e.getMessage() + " NON DEVE STAMPARE");
        }

        /*
        Suppose that before the gym open the first personal trainer created some default trainingCard.
        So the personal trainer login to his main window.
         */

        try {
            personalTrainerWindow = loginWindow.createPersonalTrainerSession("Elia", "Rossi", "345678909");
        }
        catch(Exception e){
            System.out.println(e.getMessage() + "NO STAMPA");
        }

        personalTrainerWindow.addStandardTrainingCard("Some exercise", 1, "Training Card");
        personalTrainerWindow.addStandardTrainingCard("Some exercise", 2, "Training Card");
        personalTrainerWindow.addStandardTrainingCard("Some exercise", 3, "Training Card");
        personalTrainerWindow.addStandardTrainingCard("Some exercise", 4, "Training Card");
        personalTrainerWindow.addStandardTrainingCard("Some exercise", 5, "Training Card");

        /*
        First of all the personal trainer do a first evaluation of the new costumer.
         */

        personalTrainerWindow.addEvaluation(
                personalTrainerWindow.selectCostumer("Marco", "De Luca", "3456789087"),
                LocalDate.now().getYear(),
                LocalDate.now().getMonthValue(),
                LocalDate.now().getDayOfMonth(),
                "Some comments about the measurement and an evaluation",
                2,
                1.80f,
                70f,
                62,
                8
        );

        /*
        The personal trainer set a default training card (progress level 2) to the costumer.
         */

        personalTrainerWindow.copyTrainingCard(
                personalTrainerWindow.getMyDefaultTrainingCard(2),
                personalTrainerWindow.selectCostumer("Marco", "De Luca", "3456789087"),
                LocalDate.now(),
                LocalDate.now().plusDays(14)
        );

        /*
        The costumer can see the training card from his window, first of all the costumer login to the system.
         */

        try {
            costumerWindow = loginWindow.createCostumerSession("Marco", "De Luca", "3456789087");
        }
        catch(Exception e){
            System.out.println(e.getMessage() + " NO STAMPA");
        }

        try {
            costumerWindow.getMyCurrentTrainingCard(
                    LocalDate.now().getDayOfMonth(),
                    LocalDate.now().getMonthValue(),
                    LocalDate.now().getYear()
            );
        }
        catch(Exception e){
            /*
            If the system recognize that the costumer has no trainingCard show an error dialog.
             */
            System.out.println(e.getMessage() + " NO");
        }

        /*
        In the next days the costumer access to the gym.
         */

        try{
            receptionistWindow.addAccessForCostumer(
                    receptionistWindow.selectCostumer("Marco", "De Luca", "3456789087"),
                    LocalDateTime.now()
            );
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        try {
            receptionistWindow.addAccessForCostumerFromBadge(0, LocalDateTime.now().plusDays(10));
        }
        catch(Exception e){
            System.out.println(e.getMessage() + " NO");
        }

        /*
        Then with the badge want to access to the gym for the 4th time but the system show an error dialog to the receptionist,
        because he can't access to the gym.
         */

        try{
            receptionistWindow.addAccessForCostumerFromBadge(0, LocalDateTime.now().plusDays(12));
        }
        catch(Exception e){
            System.out.println("PRIMA STAMPA: " + e.getMessage());
        }

        /*
        The receptionist receive the payment and a new subscription for the costumer is added to the system.
         */

        receptionistWindow.addAccessType("subscription",
                "monthly",
                receptionistWindow.addPayment(TypeOfSub.MONTHLY, LocalDateTime.now().plusMonths(1)),
                LocalDate.now().plusMonths(1),
                receptionistWindow.selectCostumer("Marco", "De Luca", "3456789087"));

        /*
        The costumer use the badge for the access. (suppose that the sensor recognize the badge id)
         */

        try {
            receptionistWindow.addAccessForCostumerFromBadge(0, LocalDateTime.now().plusMonths(1));
        } catch (Exception e) {
            System.out.println(e.getMessage() + " (NO STAMPA)");
        }

        /*
        The personal trainer add a new customize training card for the costumer.
         */

        personalTrainerWindow.addCustomizeTrainingCard(
                personalTrainerWindow.selectCostumer("Marco", "De Luca", "3456789087"),
                "Exercises",
                2,
                LocalDate.now().plusMonths(1).getDayOfMonth(),
                LocalDate.now().plusMonths(1).getMonthValue(),
                LocalDate.now().plusMonths(1).getYear(),
                LocalDate.now().plusMonths(2).getDayOfMonth(),
                LocalDate.now().plusMonths(2).getMonthValue(),
                LocalDate.now().plusMonths(2).getYear(),
                "Customize TrainingCard"
        );

        /*
        The costumer use the training card.
         */

        try {
            TrainingCard trainingCard = costumerWindow.getMyCurrentTrainingCard(
                    LocalDate.now().plusMonths(1).getDayOfMonth(),
                    LocalDate.now().plusMonths(1).getMonthValue(),
                    LocalDate.now().plusMonths(1).getYear()
            ).get(0);
            assertEquals("Customize TrainingCard", trainingCard.getName());
        } catch (Exception e) {
            System.out.println(e.getMessage() + " NO STAMPA");
        }
    }

    @Test
    void firstAccessOfCostumer() {
        /*
        The receptionist Marco Bianchi access to the system and want to register the new costumer.

        Receptionist receptionist = loginWindow.createReceptionistSession("Marco", "Bianchi", "174676878");


        if (gymManager == null)
            show error dialog
         */
    }
}
