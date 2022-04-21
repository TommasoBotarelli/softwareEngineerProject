package dao;

import domainModel.Badge;
import domainModel.Costumer;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public long addBadge(Badge badge) {
        badges.add(badge);
        badge.setId(count);
        return count++;
    }

    @Override
    public void deleteBadge(Badge badge) {
        badges.remove(badge);
    }

    @Override
    public Costumer searchCostumerFromId(long id){
        if (badges.stream().anyMatch(badge -> badge.getId() == id)){
            return badges.stream().filter(badge -> badge.getId() == id).collect(Collectors.toList()).get(0).getCostumer();
        }
        else
            return null;
    }
}
