package rsoi.lab5.seservice.model.user;

import rsoi.lab5.seservice.entity.user.Role;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface UserModel  {
    UUID getId();
    String getUsername();
    String getFirstName();
    String getLastName();
    String getEmail();
    List<Role> getRoles();
    Date getCreatedAt();
    Date getUpdatedAt();
}
