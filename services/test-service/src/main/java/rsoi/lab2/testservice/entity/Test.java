package rsoi.lab2.testservice.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "test")
public class Test implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id_test", updatable = false, nullable = false)
    private UUID idTest;

    @NotEmpty
    @Column(name = "source_code", length = 10000, nullable = false)
    private String sourceCode;

    @Column(name = "description", length = 1000, nullable = false)
    private String description;

    @NotNull
    @Column(name = "create_date", nullable = false)
    private Date createDate;

    @NotNull
    @Column(name = "id_task", unique = true, nullable = false)
    private UUID idTask;

    @NotNull
    @Column(name = "id_user", nullable = false)
    private UUID idUser;

    public UUID getIdTest() {
        return idTest;
    }

    public void setIdTest(UUID idTest) {
        this.idTest = idTest;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Test test = (Test) o;
        return Objects.equals(idTest, test.idTest) &&
                Objects.equals(sourceCode, test.sourceCode) &&
                Objects.equals(description, test.description) &&
                Objects.equals(createDate, test.createDate) &&
                Objects.equals(idTask, test.idTask) &&
                Objects.equals(idUser, test.idUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTest, sourceCode, description, createDate, idTask, idUser);
    }

    @Override
    public String toString() {
        return "Test{" +
                "idTest=" + idTest +
                ", sourceCode='" + sourceCode + '\'' +
                ", description='" + description + '\'' +
                ", createDate=" + createDate +
                ", idTask=" + idTask +
                ", idUser=" + idUser +
                '}';
    }
}
