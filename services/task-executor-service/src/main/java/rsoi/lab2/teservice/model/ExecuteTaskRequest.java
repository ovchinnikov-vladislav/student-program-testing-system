package rsoi.lab2.teservice.model;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class ExecuteTaskRequest {

    @NotNull
    @DecimalMin(value = "1")
    private Long idTask;
    @NotNull
    @DecimalMin(value = "1")
    private Long idTest;
    @NotNull
    @DecimalMin(value = "1")
    private Long idUser;

    @NotNull
    private String sourceTask;

    @NotNull
    private String sourceTest;

    public Long getIdTask() {
        return idTask;
    }

    public void setIdTask(Long idTask) {
        this.idTask = idTask;
    }

    public Long getIdTest() {
        return idTest;
    }

    public void setIdTest(Long idTest) {
        this.idTest = idTest;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getSourceTask() {
        return sourceTask;
    }

    public void setSourceTask(String sourceTask) {
        this.sourceTask = sourceTask;
    }

    public String getSourceTest() {
        return sourceTest;
    }

    public void setSourceTest(String sourceTest) {
        this.sourceTest = sourceTest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExecuteTaskRequest that = (ExecuteTaskRequest) o;
        return Objects.equals(idTask, that.idTask) &&
                Objects.equals(idTest, that.idTest) &&
                Objects.equals(idUser, that.idUser) &&
                Objects.equals(sourceTask, that.sourceTask) &&
                Objects.equals(sourceTest, that.sourceTest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTask, idTest, idUser, sourceTask, sourceTest);
    }

    @Override
    public String toString() {
        return "ExecuteTaskRequest{" +
                "idTask=" + idTask +
                ", idTest=" + idTest +
                ", idUser=" + idUser +
                ", sourceTask=" + sourceTask +
                ", sourceTest=" + sourceTest +
                '}';
    }
}
