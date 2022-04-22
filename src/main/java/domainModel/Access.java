package domainModel;

import java.time.LocalDateTime;

public class Access {

    private Costumer costumer;
    private LocalDateTime accessTime;
    private Turnstile turnstile;


    public Access(Costumer costumer, LocalDateTime actualDate) {
        this.costumer = costumer;
        this.accessTime = actualDate;
    }

    public Access(Costumer costumer, LocalDateTime actualDate, Turnstile turnstile) {
        this.costumer = costumer;
        this.accessTime = actualDate;
        this.turnstile = turnstile;
    }

    public void setTurnstile(Turnstile turnstile){
        this.turnstile = turnstile;
    }


    public Costumer getCostumer() {
        return costumer;
    }

    public LocalDateTime getAccessTime() {
        return accessTime;
    }
}
