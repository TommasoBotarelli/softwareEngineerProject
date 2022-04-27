package domainModel;

import java.util.Objects;

public class GymManager {
    public GymManager(String name, String surname, String phoneNumber) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
    }

    private String name;
    private String surname;
    private String phoneNumber;

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public boolean equals(Object obj) {
        GymManager gymManager = (GymManager) obj;

        return Objects.equals(this.name, gymManager.name) && Objects.equals(this.surname, gymManager.surname) &&
                Objects.equals(this.phoneNumber, gymManager.phoneNumber);
    }
}
