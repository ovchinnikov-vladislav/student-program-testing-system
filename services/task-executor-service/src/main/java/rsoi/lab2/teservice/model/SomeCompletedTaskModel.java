package rsoi.lab2.teservice.model;

public interface SomeCompletedTaskModel {
    Long getIdCompletedTask();
    Integer getCountSuccessfulTests();
    Integer getCountFailedTests();
    Integer getCountAllTests();
    Long getIdTask();
    Long getIdTest();
    Long getIdUser();
}
