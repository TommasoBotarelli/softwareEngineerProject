package domainModel;

import java.time.LocalDate;

public interface AccessType {
    boolean isValid(LocalDate date);
}
