package domainModel;

import java.util.Calendar;

public class Bill {

    private float payment;
    private Calendar date;


    Bill(float payment, Calendar date){
        this.payment = payment;
        this.date = date;
    }


    public float getPayment() {
        return payment;
    }

    public Calendar getDate() {
        return date;
    }
}
