package rsoi.lab2.teservice.model;

import java.util.Date;
import java.util.UUID;

public interface SomeCompletedTaskModel {
    UUID getId();
    Integer getCountSuccessfulTests();
    Integer getCountFailedTests();
    Integer getCountAllTests();
    UUID getIdTask();
    UUID getIdTest();
    UUID getIdUser();
    Date getCreatedAt();
    Date getUpdatedAt();
}
