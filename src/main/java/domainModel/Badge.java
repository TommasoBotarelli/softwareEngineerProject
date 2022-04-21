package domainModel;

public class Badge {
    private final Costumer costumer;
    private long id;

    public Badge(Costumer costumer){
        this.costumer = costumer;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId(){
        return this.id;
    }

    public Costumer getCostumer(){
        return costumer;
    }
}
