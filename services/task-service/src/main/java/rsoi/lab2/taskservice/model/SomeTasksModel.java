package rsoi.lab2.taskservice.model;

import java.util.Date;
import java.util.UUID;

public interface SomeTasksModel {
    UUID getIdTask();
    String getNameTask();
    String getDescription();
    String getImage();
    Byte getComplexity();
    Date getCreatedAt();
    Date getUpdatedAt();
    UUID getIdUser();
}
