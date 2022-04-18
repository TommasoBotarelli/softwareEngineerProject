package dao;

import domainModel.Badge;
import domainModel.Costumer;

public interface BadgeDao {
    void addBadge(Badge badge);
    void deleteBadge(Badge badge);
    Costumer searchCostumerFromId(int id);
}
