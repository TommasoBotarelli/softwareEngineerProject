package domainModel;

import java.time.LocalDate;

public class Access {

    private Costumer costumer;
    private boolean accessValid;
    private LocalDate date;


    public Access(Costumer costumer, LocalDate actualDate) {
        this.costumer = costumer;
        this.date = actualDate;
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
