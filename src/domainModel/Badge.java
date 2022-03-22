package domainModel;

public class Badge {
    private Costumer costumer;

    Badge(Costumer costumer){
        this.costumer = costumer;
    }

    public Costumer getCostumer(){
        return costumer;
    }
}
