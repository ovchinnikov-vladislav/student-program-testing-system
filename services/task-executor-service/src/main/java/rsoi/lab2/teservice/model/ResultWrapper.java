package rsoi.lab2.teservice.model;

import java.util.UUID;

public class ResultWrapper {

    private UUID idCompletedTask;
    private ResultTest resultTest;

    public UUID getIdCompletedTask() {
        return idCompletedTask;
    }

    public void setIdCompletedTask(UUID idCompletedTask) {
        this.idCompletedTask = idCompletedTask;
    }

    public ResultTest getResultTest() {
        return resultTest;
    }

    public void setResultTest(ResultTest resultTest) {
        this.resultTest = resultTest;
    }
}
