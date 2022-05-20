package dao.concreteClass;

import dao.interfaceClass.BadgeDao;
import domainModel.Badge;
import domainModel.Customer;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class FakeBadgeDao implements BadgeDao {

    int count = 0;
    ArrayList<Badge> badges;
    private static FakeBadgeDao instance = null;

    private FakeBadgeDao(){
        badges = new ArrayList<>();
    }

    public static FakeBadgeDao getInstance(){
        if (instance == null){
            instance = new FakeBadgeDao();
        }
        return instance;
    }

    @Override
    public long addBadge(Badge badge) {
        if (badges.stream().noneMatch(b -> b.getId() == count)){
            badges.add(badge);
            badge.setId(count);
            return count++;
        }
        else {
            count++;
            return this.addBadge(badge);
        }
    }

    @Override
    public void deleteBadge(Badge badge) {
        badges.remove(badge);
    }

    @Override
    public Customer searchCustomerFromId(long id){
        if (badges.stream().anyMatch(badge -> badge.getId() == id)){
            return badges.stream().filter(badge -> badge.getId() == id).collect(Collectors.toList()).get(0).getCustomer();
        }
        else
            return null;
    }

    @Override
    public Badge getFromCustomer(Customer customer) {
        if (badges.stream().anyMatch(badge -> badge.getCustomer().equals(customer))){
            return badges.stream().filter(badge -> badge.getCustomer().equals(customer)).collect(Collectors.toList()).get(0);
        }
        else{
            return null;
        }
    }
}
