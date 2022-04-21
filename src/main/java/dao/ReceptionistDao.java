package dao;

import domainModel.Receptionist;

import java.util.ArrayList;

public interface ReceptionistDao {
    ArrayList<Receptionist> getAllReceptionist();
    Receptionist getReceptionistFromNameSurnamePhoneNumber(String name, String surname, String phoneNumber) throws Exception;
    void addReceptionist(Receptionist receptionist);
    void deleteReceptionist(Receptionist receptionist);
    boolean contains(Receptionist receptionist);
    void deleteAll();
}
