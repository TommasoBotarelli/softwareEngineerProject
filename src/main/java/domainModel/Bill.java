package domainModel;

import java.time.LocalDateTime;

public class Bill {

    private float payment;
    private LocalDateTime dateTime;
    private long id;


    public Bill(float payment, LocalDateTime dateTime){
        this.payment = payment;
        this.dateTime = dateTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getPayment() {
        return payment;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
