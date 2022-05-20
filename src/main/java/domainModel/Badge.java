package domainModel;

public class Badge {
    private Customer customer;
    private long id;

    public Badge(Customer customer){
        this.customer = customer;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId(){
        return this.id;
    }

    public Customer getCustomer(){
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object obj) {
        return this.id == ((Badge)obj).id &&
                this.customer == ((Badge)obj).customer;
    }
}
