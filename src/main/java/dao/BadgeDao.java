package dao;

import domainModel.Badge;
import domainModel.Costumer;

public interface BadgeDao {
    long addBadge(Badge badge);
    void deleteBadge(Badge badge);
    Costumer searchCostumerFromId(long id);
    Badge getFromCostumer(Costumer costumer);
}
