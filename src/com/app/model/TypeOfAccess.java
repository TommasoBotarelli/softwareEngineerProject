package com.app.model;

import java.util.Calendar;

public interface TypeOfAccess {
    boolean isValid(Calendar actualDate);
    void addAccess();
}
