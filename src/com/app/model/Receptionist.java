package com.app.model;

public class Receptionist {
    private EmployeeInfo receptionistInfo;

    Receptionist(String name, String surname, String phoneNumber){
        receptionistInfo = new EmployeeInfo(name, surname, phoneNumber);
    }

    public EmployeeInfo getInfo() {
        return receptionistInfo;
    }
}
