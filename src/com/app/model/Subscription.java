package com.app.model;

import java.util.Calendar;

public interface Subscription {
    boolean isValid(Calendar actualDate);
}
