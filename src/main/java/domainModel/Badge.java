package domainModel;

public class Badge {
    private final Costumer costumer;
    public final String id;

    Badge(Costumer costumer, String id){
        this.costumer = costumer;
        this.id = id;
    }

    public Costumer getCostumer(){
        return costumer;
    }
}
