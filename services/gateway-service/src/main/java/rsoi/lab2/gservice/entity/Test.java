package rsoi.lab2.gservice.entity;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class Test implements Serializable {

    private UUID idTest;

    @NotEmpty
    @Size(max=10000)
    private String sourceCode;
    private String description;
    private Date createDate;
    private UUID idTask;
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
