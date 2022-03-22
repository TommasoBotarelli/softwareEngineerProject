package domainModel;

import java.util.Calendar;

public class Receipt {

    private float payment;
    private Calendar date;


    Receipt(float payment, Calendar date){
        this.payment = payment;
        this.date = date;
    }
}
