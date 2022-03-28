package domainModel;

import java.time.LocalDate;

public class Bill {

    private float payment;
    private LocalDate date;


    public Bill(float payment, LocalDate date){
        this.payment = payment;
        this.date = date;
    }


    public float getPayment() {
        return payment;
    }

    public LocalDate getDate() {
        return date;
    }
}
