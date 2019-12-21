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
    private UUID idTask;
    private UUID idUser;
    private Date createdAt;
    private Date updatedAt;

    @NotEmpty
    @Size(max=10000)
    private String sourceCode;

    @Size(max=1000)
    private String description;

    public UUID getIdTest() {
        return idTest;
    }

    public void setIdTest(UUID idTest) {
        this.idTest = idTest;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Test test = (Test) o;
        return Objects.equals(idTest, test.idTest) &&
                Objects.equals(idTask, test.idTask) &&
                Objects.equals(idUser, test.idUser) &&
                Objects.equals(createdAt, test.createdAt) &&
                Objects.equals(updatedAt, test.updatedAt) &&
                Objects.equals(sourceCode, test.sourceCode) &&
                Objects.equals(description, test.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTest, idTask, idUser, createdAt, updatedAt, sourceCode, description);
    }

    @Override
    public String toString() {
        return "Test{" +
                "idTest=" + idTest +
                ", idTask=" + idTask +
                ", idUser=" + idUser +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", sourceCode='" + sourceCode + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
