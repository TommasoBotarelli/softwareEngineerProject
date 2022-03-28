package domainModel;

public class PersonalTrainer {
    private String name;
    private String surname;
    private String phoneNumber;

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

    public PersonalTrainer(String name, String surname, String phoneNumber){
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object obj) {
        return this.name.equalsIgnoreCase(((PersonalTrainer)obj).getName()) &&
                this.surname.equalsIgnoreCase(((PersonalTrainer)obj).getSurname()) &&
                this.phoneNumber.equalsIgnoreCase(((PersonalTrainer)obj).getPhoneNumber());
    }

    public boolean isEqualInInfo(String name, String surname, String phoneNumber){
        return this.name.equalsIgnoreCase(name) && this.surname.equalsIgnoreCase(surname)
                && this.phoneNumber.equalsIgnoreCase(phoneNumber);
    }
}
