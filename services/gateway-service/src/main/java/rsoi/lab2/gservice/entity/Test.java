package rsoi.lab2.gservice.entity;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Test implements Serializable {

    private Long idTest;

    @NotNull
    private String sourceCode;
    private String description;
    @NotNull
    private Date createDate;
    @NotNull
    @DecimalMin(value = "1")
    private Long idTask;
    @NotNull
    @DecimalMin(value = "1")
    private Long idUser;

    public Long getIdTest() {
        return idTest;
    }

    public void setIdTest(Long idTest) {
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
