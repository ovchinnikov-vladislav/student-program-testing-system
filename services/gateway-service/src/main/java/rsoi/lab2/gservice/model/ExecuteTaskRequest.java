package rsoi.lab2.gservice.model;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class ExecuteTaskRequest {

    @NotNull
    @DecimalMin(value = "1")
    private Long idTask;
    @NotNull
    @DecimalMin(value = "1")
    private Long idUser;

    @NotEmpty
    private String sourceTask;

    public Long getIdTask() {
        return idTask;
    }

    public void setIdTask(Long idTask) {
        this.idTask = idTask;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExecuteTaskRequest that = (ExecuteTaskRequest) o;
        return Objects.equals(idTask, that.idTask) &&
                Objects.equals(idUser, that.idUser) &&
                Objects.equals(sourceTask, that.sourceTask);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTask, idUser, sourceTask);
    }

    @Override
    public String toString() {
        return "ExecuteTaskRequest{" +
                "idTask=" + idTask +
                ", idUser=" + idUser +
                ", sourceTask=" + sourceTask +
                '}';
    }
}
