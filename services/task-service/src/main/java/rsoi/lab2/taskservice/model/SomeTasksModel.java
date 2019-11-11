package rsoi.lab2.taskservice.model;

import java.util.Date;

public interface SomeTasksModel {
    Long getIdTask();
    String getNameTask();
    String getDescription();
    String getImage();
    Byte getComplexity();
    Date getCreateDate();
    Long getIdUser();
}
