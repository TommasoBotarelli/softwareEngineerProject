package domainModel;

import java.util.Calendar;

public class Access {

    private Costumer costumer;
    private final boolean accessValid;
    private Calendar date;


    Access(Costumer costumer, Calendar actualDate) {
        this.costumer = costumer;
        this.date = actualDate;
        accessValid = costumer.canAccess(actualDate);
        costumer.setAccess(actualDate);
    }

}
