package dao.interfaceClass;

import domainModel.Badge;
import domainModel.Customer;

public interface BadgeDao {
    long addBadge(Badge badge);
    void deleteBadge(Badge badge);
    Customer searchCustomerFromId(long id);
    Badge getFromCustomer(Customer customer);
}
