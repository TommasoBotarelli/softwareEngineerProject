package dao;

import domainModel.Badge;
import domainModel.Costumer;

import java.util.ArrayList;

public class FakeBadgeDao implements BadgeDao{

    int count = 0;
    ArrayList<Badge> badges = new ArrayList<>();

    @Override
    public void addBadge(Badge badge) {
        badge.setId(++count);
        badges.add(badge);
    }

    @Override
    public void deleteBadge(Badge badge) {
        badges.remove(badge);
    }

    @Override
    public Costumer searchCostumerFromId(int id){
        return ((Badge)badges.stream().filter(badge -> badge.id == id)).getCostumer();
    }
}
