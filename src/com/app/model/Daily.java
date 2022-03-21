package com.app.model;

import java.util.Calendar;

public class Daily implements TypeOfAccess {
    private boolean used;


    Daily(){
        this.used = false;
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
