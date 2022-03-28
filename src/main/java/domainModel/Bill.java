package domainModel;

import java.time.LocalDate;

public class Bill {

    private float payment;
    private LocalDate date;
    private long id;


    public Bill(float payment, LocalDate date){
        this.payment = payment;
        this.date = date;
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

    public LocalDate getDate() {
        return date;
    }
}
