package dao;

import domainModel.Badge;
import domainModel.Costumer;

import java.util.ArrayList;
import java.util.Objects;

public class FakeBadgeDao implements BadgeDao{

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
