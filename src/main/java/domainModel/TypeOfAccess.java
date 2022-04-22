package domainModel;

import java.time.LocalDate;

public interface TypeOfAccess {
    Costumer getCostumer();
    LocalDate getEmission();
    LocalDate getExpiration();
    void setExpiration(LocalDate date);
    void setEmission(LocalDate date);
    void setCostumer(Costumer costumer);
    void addAccess();
    long getBillID();
    void setBillID(long id);
}
