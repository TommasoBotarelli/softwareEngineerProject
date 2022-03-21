package com.app.model;

import java.util.Calendar;
import java.util.Date;

public class Test implements Subscription{

    private Calendar emission;
    private Calendar expiration;
    private int accessCounter;


    Test(Calendar emission){
        this.emission = emission;
        this.expiration = Calendar.getInstance();
        expiration.setLenient(true);
        expiration.set(Calendar.DAY_OF_MONTH, emission.get(Calendar.DAY_OF_MONTH) + 14);
        this.accessCounter = 0;
    }


    private boolean isExpired(Calendar actualDate){
        return this.expiration.get(Calendar.MONTH) < actualDate.get(Calendar.MONTH) ||
                (this.expiration.get(Calendar.MONTH) == actualDate.get(Calendar.MONTH) &&
                        this.expiration.get(Calendar.DAY_OF_MONTH) < actualDate.get(Calendar.DAY_OF_MONTH));
    }


    @Override
    public boolean isValid(Calendar actualDate) {
        boolean access = true;

        if(accessCounter >= 3 || isExpired(actualDate))
            access = false;
        else
            accessCounter++;

        return access;
    }
}
