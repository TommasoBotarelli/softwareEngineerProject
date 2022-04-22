package domainModel;

public class Badge {
    private Costumer costumer;
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

    public void setCostumer(Costumer costumer) {
        this.costumer = costumer;
    }

    @Override
    public boolean equals(Object obj) {
        return this.id == ((Badge)obj).id &&
                this.costumer == ((Badge)obj).costumer;
    }
}
