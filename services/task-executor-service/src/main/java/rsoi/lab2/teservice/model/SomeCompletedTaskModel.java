package rsoi.lab2.teservice.model;

import java.util.UUID;

public interface SomeCompletedTaskModel {
    UUID getIdCompletedTask();
    Integer getCountSuccessfulTests();
    Integer getCountFailedTests();
    Integer getCountAllTests();
    UUID getIdTask();
    UUID getIdTest();
    UUID getIdUser();
}
