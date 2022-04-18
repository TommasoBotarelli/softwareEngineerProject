package businessLogic;

import dao.AccessDao;
import dao.FakeAccessDao;
import domainModel.Access;
import domainModel.Badge;
import domainModel.Turnstile;

import java.time.LocalDate;

public class TurnstileController {
    Turnstile thisTurnstile;
    AccessDao accessDao;

    TurnstileController(boolean isOpen, boolean isOperative){
        this.thisTurnstile = new Turnstile(isOperative, isOpen);
        accessDao = new FakeAccessDao();
    }

    public void addAccess(String id, LocalDate date){
        /*
        TODO qui servirebbe un dao per i badge, poi dal dao ricerco il mio oggetto badge, e quindi il costumer. A questo punto faccio i controlli
         */

    }
}
