package rsoi.lab2.uservice.model;

import java.util.UUID;

public interface SomeUsersModel {
    UUID getIdUser();
    String getUserName();
    String getFirstName();
    String getLastName();
    Byte getGroup();
    Byte getStatus();
    String getEmail();
}
