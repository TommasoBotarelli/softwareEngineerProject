package domainModel;

import java.time.LocalDate;

public class Access {

    private Costumer costumer;
    private boolean accessValid;
    private LocalDate date;


    public Access(Costumer costumer, LocalDate actualDate, boolean valid) {
        this.costumer = costumer;
        this.date = actualDate;
        this.accessValid = valid;
    }


    public Costumer getCostumer() {
        return costumer;
    }

    public boolean isAccessValid() {
        return accessValid;
    }

    public void setAccessValid(boolean accessValid) {
        this.accessValid = accessValid;
    }
}
