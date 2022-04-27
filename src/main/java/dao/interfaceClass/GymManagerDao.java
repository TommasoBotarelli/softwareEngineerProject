package dao.interfaceClass;

import domainModel.GymManager;

public interface GymManagerDao {
    GymManager getGymManager(String name, String surname, String phoneNumber);
    boolean addGymManager(String name, String surname, String phoneNumber);
}
