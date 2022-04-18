package domainModel;

public class Badge {
    private final Costumer costumer;
    public int id;

    Badge(Costumer costumer){
        this.costumer = costumer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Costumer getCostumer(){
        return costumer;
    }
}
