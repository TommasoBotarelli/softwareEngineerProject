package domainModel;

import java.time.LocalDate;

public interface TypeOfAccess {
    boolean isValid(LocalDate actualDate);
    Costumer getCostumer();
    LocalDate getEmission();
    LocalDate getExpiration();
    void setExpiration(LocalDate date);
    void setEmission(LocalDate date);
    void setCostumer(Costumer costumer);
    void addAccess();
}
