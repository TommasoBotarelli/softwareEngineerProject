package domainModel;

public enum TypeOfSub {

    TRIAL(0, 14, 0),
    MONTHLY(1, 0, 50),
    QUARTERLY(3, 0, 120),
    HALFYEARLY(6, 0, 200),
    YEARLY(12, 0, 350);


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

    public int getnMonth() {
        return nMonth;
    }

    public int getnDay() {
        return nDay;
    }

    public float getCost() {
        return cost;
    }
}
