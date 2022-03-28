package domainModel;


public class Costumer {

    private String name;
    private String surname;
    private String phoneNumber;

    private Badge myBadge;


    public Costumer(String name, String surname, String phoneNumber){
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
    }


    public void setBadge(Badge badge){
        this.myBadge = badge;
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
        return this.name.equalsIgnoreCase(((Costumer)obj).getName()) &&
                this.surname.equalsIgnoreCase(((Costumer)obj).getSurname()) &&
                this.phoneNumber.equalsIgnoreCase(((Costumer)obj).getPhoneNumber());
    }
}
