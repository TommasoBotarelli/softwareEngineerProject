package com.app.model;

public enum TypeOfMonthlySub {
    MENSILE(1, 50),
    TRIMESTRALE(3, 120),
    SEMESTRALE(6, 250),
    ANNUALE(12, 400);


    final int nMonth;
    final float cost;


    TypeOfMonthlySub(int nMonth, float cost){
        this.nMonth = nMonth;
        this.cost = cost;
    }

}
