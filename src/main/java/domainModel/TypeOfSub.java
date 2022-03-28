package domainModel;

public enum TypeOfSub {

    PROVA(0, 14, 0),
    MENSILE(1, 0, 50),
    TRIMESTRALE(3, 0, 120),
    SEMESTRALE(6, 0, 200),
    ANNUALE(12, 0, 350);


    final int nMonth;
    final int nDay;
    final float cost;


    TypeOfSub(int nMonth, int nDay, float cost){
        this.nMonth = nMonth;
        this.nDay = nDay;
        this.cost = cost;
    }

    TypeOfSub(TypeOfSub typeOfSub){
        this.nDay = typeOfSub.nDay;
        this.nMonth = typeOfSub.nMonth;
        this.cost = typeOfSub.cost;
    }

}
