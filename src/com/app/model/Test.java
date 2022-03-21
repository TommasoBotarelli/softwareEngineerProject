package com.app.model;

import java.util.Calendar;
import java.util.Date;

public class Test implements Subscription{

    private Calendar emission;
    private Calendar expiration;
    private int accessCounter;
    private int validityCounter;


    Test(Calendar emission, int validityCounter){
        this.emission = emission;
        this.expiration = Calendar.getInstance();
        expiration.setLenient(true);
        expiration.set(Calendar.DAY_OF_MONTH, emission.get(Calendar.DAY_OF_MONTH) + 14);
        this.accessCounter = 0;
        this.validityCounter = validityCounter;
    }


    private boolean isExpired(Calendar actualDate){
        return this.expiration.get(Calendar.MONTH) < actualDate.get(Calendar.MONTH) ||
                (this.expiration.get(Calendar.MONTH) == actualDate.get(Calendar.MONTH) &&
                        this.expiration.get(Calendar.DAY_OF_MONTH) < actualDate.get(Calendar.DAY_OF_MONTH));
    }


    @Override
    public boolean isValid(Calendar actualDate) {
        return accessCounter < validityCounter || !isExpired(actualDate);
    }
}
