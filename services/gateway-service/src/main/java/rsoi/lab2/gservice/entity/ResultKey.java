package rsoi.lab2.gservice.entity;

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
