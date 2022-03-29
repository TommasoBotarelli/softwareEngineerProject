package dao;

import domainModel.Receptionist;

import java.util.ArrayList;

public class FakeReceptionistDao implements ReceptionistDao{

    ArrayList<Receptionist> receptionists;

    public FakeReceptionistDao() {
        this.receptionists = new ArrayList<>();
    }

    @Override
    public ArrayList<Receptionist> getAllReceptionist() {
        return receptionists;
    }

    @Override
    public Receptionist getReceptionistFromNameSurnamePhoneNumber(String name, String surname, String phoneNumber) {
        return (Receptionist) receptionists.stream().filter(receptionist -> receptionist.equals(new Receptionist(name, surname, phoneNumber)));
    }
}
