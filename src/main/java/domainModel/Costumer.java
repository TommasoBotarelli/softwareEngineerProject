package domainModel;

import java.util.ArrayList;


public class Costumer {

    private String name;
    private String surname;
    private String phoneNumber;

    private Badge myBadge;
    private TrainingDiary myTrainingDiary;

    private ArrayList<TypeOfAccess> myTypeOfAccess;


    public Costumer(String name, String surname, String phoneNumber){
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        myTypeOfAccess = new ArrayList<>();
    }


    public void setBadge(Badge badge){
        this.myBadge = badge;
    }

    public void addTypeOfAccess(TypeOfAccess access){
        myTypeOfAccess.add(access);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
