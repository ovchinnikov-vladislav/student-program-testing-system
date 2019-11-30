package rsoi.lab2.testservice.model;

import java.util.Date;
import java.util.UUID;

public interface SomeTestsModel {
    UUID getIdTest();
    Date getCreateDate();
    UUID getIdTask();
    UUID getIdUser();
}
