package domainModel;

import java.time.LocalDate;

public class Access {

    private Costumer costumer;
    private LocalDate accessTime;


    public Access(Costumer costumer, LocalDate actualDate) {
        this.costumer = costumer;
        this.accessTime = actualDate;
    }


    public Costumer getCostumer() {
        return costumer;
    }

    public LocalDate getAccessTime() {
        return accessTime;
    }
}
