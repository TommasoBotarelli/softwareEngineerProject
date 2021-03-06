package dao.interfaceClass;

import domainModel.Receptionist;

import java.util.ArrayList;

public interface ReceptionistDao {
    ArrayList<Receptionist> getAllReceptionist();
    Receptionist getReceptionistFromNameSurnamePhoneNumber(String name, String surname, String phoneNumber);
    void addReceptionist(Receptionist receptionist);
    void deleteReceptionist(Receptionist receptionist);
    boolean contains(Receptionist receptionist);
    void deleteAll();
}
