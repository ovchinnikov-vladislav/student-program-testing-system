package rsoi.lab2.gservice.entity;

import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

    @DecimalMin(value = "1")
    private Long idUser;

    @NotEmpty
    @Size(min = 5, max = 50)
    private String userName;
    @NotEmpty
    @Size(min = 8)
    private String password;
    @NotEmpty
    @Email
    private String email;
    private String firstName;
    private String lastName;
    @Value("{some.key:1}")
    private Byte group;
    @Value("{some.key:0}")
    private Byte status;

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Byte getGroup() {
        return group;
    }

    public void setGroup(Byte group) {
        this.group = group;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return idUser == user.idUser &&
                group == user.group &&
                status == user.status &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, userName, password, email, firstName, lastName, group, status);
    }
}
