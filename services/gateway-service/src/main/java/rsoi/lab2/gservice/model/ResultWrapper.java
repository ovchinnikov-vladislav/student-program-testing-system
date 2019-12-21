package rsoi.lab2.gservice.model;

import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultWrapper that = (ResultWrapper) o;
        return Objects.equals(idCompletedTask, that.idCompletedTask) &&
                Objects.equals(resultTest, that.resultTest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCompletedTask, resultTest);
    }

    @Override
    public String toString() {
        return "ResultWrapper{" +
                "idCompletedTask=" + idCompletedTask +
                ", resultTest=" + resultTest +
                '}';
    }
}
