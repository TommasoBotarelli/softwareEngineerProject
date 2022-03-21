package com.app.model;

import java.util.Calendar;

public class Daily implements Subscription{
    private boolean used;
    private Calendar dayOfUse;


    Daily(Calendar dayOfUse){
        this.used = false;
        this.dayOfUse = dayOfUse;
    }


    @Override
    public boolean isValid(Calendar actualDate) {
        boolean canAccess = true;

        if (!used)
            used = true;
        else
            canAccess = false;

        return canAccess;
    }
}
