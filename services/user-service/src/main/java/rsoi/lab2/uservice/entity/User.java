package rsoi.lab2.uservice.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(
        name = "UNQ_USERNAME_EMAIL",
        columnNames = { "user_name", "email" }
))
public class User implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id_user", updatable = false, nullable = false)
    private UUID idUser;

    @NotEmpty
    @Size(min = 5, max = 50)
    @Column(name = "user_name", nullable = false)
    private String userName;

    @NotEmpty
    @Size(min = 8)
    @Column(name = "pass", nullable = false)
    private String password;

    @NotEmpty
    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "gr", columnDefinition = "integer default 1", nullable = false)
    private Byte group;

    @Column(name = "status", columnDefinition = "integer default 0", nullable = false)
    private Byte status;

    public UUID getIdUser() {
        return idUser;
    }

    public void setIdUser(UUID idUser) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(idUser, user.idUser) &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(group, user.group) &&
                Objects.equals(status, user.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, userName, password, email, firstName, lastName, group, status);
    }

    @Override
    public String toString() {
        return String.format("User [idUser: %s, userName: %s, password: %s, email: %s, firstName: %s, lastName: %s, group: %d, status: %d]", idUser, userName, password, email, firstName, lastName, group, status);
    }
}
