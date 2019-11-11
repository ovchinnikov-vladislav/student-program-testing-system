package rsoi.lab2.rservice.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import java.io.Serializable;

public class ResultKey implements Serializable {

    private Long idTask;
    private Long idUser;

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
}
