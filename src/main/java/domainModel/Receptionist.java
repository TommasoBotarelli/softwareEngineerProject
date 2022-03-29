package domainModel;

public class Receptionist {
    String name;
    String surname;
    String phoneNumber;

    public Receptionist(String name, String surname, String phoneNumber){
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
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

    @Override
    public boolean equals(Object obj) {
        Receptionist otherReceptionist = (Receptionist) obj;
        return this.name.equalsIgnoreCase(otherReceptionist.getName()) &&
                this.surname.equalsIgnoreCase(otherReceptionist.getSurname()) &&
                this.phoneNumber.equalsIgnoreCase(otherReceptionist.getPhoneNumber());
    }
}
