package dao;

import domainModel.Receptionist;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class FakeReceptionistDao implements ReceptionistDao{

    ArrayList<Receptionist> receptionists;
    private static FakeReceptionistDao instance = null;

    private FakeReceptionistDao() {
        this.receptionists = new ArrayList<>();
    }

    public static FakeReceptionistDao getInstance(){
        if (instance == null){
            instance = new FakeReceptionistDao();
        }
        return instance;
    }

    @Override
    public ArrayList<Receptionist> getAllReceptionist() {
        return receptionists;
    }

    @Override
    public Receptionist getReceptionistFromNameSurnamePhoneNumber(String name, String surname, String phoneNumber){
        if (receptionists.stream().anyMatch(receptionist -> receptionist.equals(new Receptionist(name, surname, phoneNumber)))){
            return receptionists.stream().filter
                    (receptionist -> receptionist.equals(new Receptionist(name, surname, phoneNumber))).collect(Collectors.toList()).get(0);
        }
        else
            return null;
    }

    @Override
    public void addReceptionist(Receptionist receptionist) {
        if (!receptionists.contains(receptionist)){
            receptionists.add(receptionist);
        }
    }

    public void deleteReceptionist(Receptionist receptionist){
        receptionists.remove(receptionist);
    }

    @Override
    public boolean contains(Receptionist receptionist) {
        return receptionists.contains(receptionist);
    }

    @Override
    public void deleteAll() {
        receptionists.clear();
    }
}
