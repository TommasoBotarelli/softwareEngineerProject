package domainModel;

import java.time.LocalDateTime;

public class Access {

    private Costumer costumer;
    private LocalDateTime accessTime;


    public Access(Costumer costumer, LocalDateTime actualDate) {
        this.costumer = costumer;
        this.accessTime = actualDate;
    }


    public Costumer getCostumer() {
        return costumer;
    }

    public LocalDateTime getAccessTime() {
        return accessTime;
    }
}
