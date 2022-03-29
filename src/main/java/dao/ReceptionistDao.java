package dao;

import domainModel.Receptionist;

import java.util.ArrayList;

public interface ReceptionistDao {
    ArrayList<Receptionist> getAllReceptionist();
    Receptionist getReceptionistFromNameSurnamePhoneNumber(String name, String surname, String phoneNumber);
}
