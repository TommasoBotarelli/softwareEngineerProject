package com.app.model;

import java.util.Calendar;

public class Monthly implements Subscription{

    private Calendar emission;
    private Calendar expiration;
    private int accessCounter;
    private final TypeOfMonthlySub type;


    Monthly(Calendar emission, TypeOfMonthlySub type){
        this.type = type;
        this.emission = emission;
        this.expiration = Calendar.getInstance();
        expiration.setLenient(true);
        expiration.set(Calendar.MONTH, emission.get(Calendar.MONTH) + type.nMonth);
        this.accessCounter = 0;
    }


    private boolean isExpired(Calendar actualDate){
        return this.expiration.get(Calendar.MONTH) < actualDate.get(Calendar.MONTH) ||
                (this.expiration.get(Calendar.MONTH) == actualDate.get(Calendar.MONTH) &&
                        this.expiration.get(Calendar.DAY_OF_MONTH) < actualDate.get(Calendar.DAY_OF_MONTH));
    }


    @Override
    public boolean isValid(Calendar actualDate) {
        return !isExpired(actualDate);
    }
}
