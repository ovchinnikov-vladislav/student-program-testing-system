package rsoi.lab2.testservice.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
public class Test extends BaseEntity implements Serializable {

    @NotEmpty
    @Size(max=10000)
    @Column(name = "source_code", length = 10000, nullable = false)
    private String sourceCode;

    @Size(max=1000)
    @Column(name = "description", length = 1000, nullable = false)
    private String description;

    @NotNull
    @Column(name = "id_task", unique = true, nullable = false)
    private UUID idTask;

    @NotNull
    @Column(name = "id_user", nullable = false)
    private UUID idUser;

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
        return Objects.equals(super.getId(), test.getId()) &&
                Objects.equals(sourceCode, test.sourceCode) &&
                Objects.equals(description, test.description) &&
                Objects.equals(idTask, test.idTask) &&
                Objects.equals(idUser, test.idUser) &&
                Objects.equals(super.getCreatedAt(), test.getCreatedAt()) &&
                Objects.equals(super.getUpdatedAt(), test.getUpdatedAt()) &&
                Objects.equals(super.getStatus(), test.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId(), sourceCode, description, idTask, idUser,
                super.getCreatedAt(), super.getUpdatedAt(), super.getStatus());
    }

    @Override
    public String toString() {
        return "Test{" +
                "idTest=" + super.getId() +
                ", sourceCode='" + sourceCode + '\'' +
                ", description='" + description + '\'' +
                ", idTask=" + idTask +
                ", idUser=" + idUser +
                ", createdAt=" + super.getCreatedAt() +
                ", updatedAt=" + super.getUpdatedAt() +
                ", status=" + super.getStatus() +
                '}';
    }
}
