package dao.concreteClass;

import dao.interfaceClass.GymManagerDao;
import domainModel.GymManager;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class FakeGymManagerDao implements GymManagerDao {
    private static FakeGymManagerDao instance = null;

    private ArrayList<GymManager> gymManagers;

    private FakeGymManagerDao(){
        gymManagers = new ArrayList<>();
        gymManagers.add(new GymManager("Default", "GymManager", "123456789"));
    }

    public static FakeGymManagerDao getInstance(){
        if (instance == null)
            instance = new FakeGymManagerDao();
        return instance;
    }

    @Override
    public GymManager getGymManager(String name, String surname, String phoneNumber) {
        GymManager compareGymManager = new GymManager(name, surname, phoneNumber);

        if (gymManagers.contains(compareGymManager))
            return gymManagers.stream().filter(gymManager -> gymManager.equals(compareGymManager)).collect(Collectors.toList()).get(0);
        else
            return null;
    }

    @Override
    public boolean addGymManager(String name, String surname, String phoneNumber) {
        GymManager newGymManager = new GymManager(name, surname, phoneNumber);

        if (!gymManagers.contains(newGymManager)){
            gymManagers.add(newGymManager);
            return true;
        }

        return false;
    }
}
