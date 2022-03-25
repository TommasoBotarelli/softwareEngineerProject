package domainModel;

public class PersonalTrainer {
    private EmployeeInfo personalTrainerInfo;

    public PersonalTrainer(String name, String surname, String phoneNumber){
        this.personalTrainerInfo = new EmployeeInfo(name, surname, phoneNumber);
    }

    public EmployeeInfo getPersonalTrainerInfo() {
        return personalTrainerInfo;
    }
}
