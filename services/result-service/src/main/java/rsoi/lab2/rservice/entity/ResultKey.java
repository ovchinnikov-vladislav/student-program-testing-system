package rsoi.lab2.rservice.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import java.io.Serializable;
import java.util.UUID;

public class ResultKey implements Serializable {

    private UUID idTask;
    private UUID idUser;

    public UUID getIdTask() {
        return idTask;
    }

    public void setIdTask(UUID idTask) {
        this.idTask = idTask;
    }

    public UUID getIdUser() {
        return idUser;
    }

    public void setIdUser(UUID idUser) {
        this.idUser = idUser;
    }
}
