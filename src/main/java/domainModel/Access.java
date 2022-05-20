package domainModel;

import java.time.LocalDateTime;

public class Access {

    private Customer customer;
    private LocalDateTime accessTime;


    public Access(Customer customer, LocalDateTime actualDate) {
        this.customer = customer;
        this.accessTime = actualDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDateTime getAccessTime() {
        return accessTime;
    }
}
