package rsoi.lab2.teservice.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "completed_task")
@EntityListeners(AuditingEntityListener.class)
public class CompletedTask extends BaseEntity implements Serializable {

    @NotEmpty
    @Column(name = "source_code", nullable = false)
    @Size(max=10000)
    private String sourceCode;

    @Column(name = "count_successful_tests", nullable = false)
    @DecimalMin(value = "0")
    private Integer countSuccessfulTests;

    @Column(name = "count_failed_tests", nullable = false)
    @DecimalMin(value = "0")
    private Integer countFailedTests;

    @Column(name = "count_all_tests", nullable = false)
    @DecimalMin(value = "0")
    private Integer countAllTests;

    @Column(name = "was_successful", nullable = false)
    private Byte wasSuccessful;

    @NotNull
    @Column(name = "id_task", nullable = false)
    private UUID idTask;

    @NotNull
    @Column(name = "id_test", nullable = false)
    private UUID idTest;

    @NotNull
    @Column(name = "id_user", nullable = false)
    private UUID idUser;

    @PrePersist
    public void prePersist() {
        if (countSuccessfulTests == null)
            countSuccessfulTests = 0;
        if (countAllTests == null)
            countAllTests = 0;
        if (countFailedTests == null)
            countFailedTests = 0;
        if (wasSuccessful == 0)
            wasSuccessful = 0;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public Byte getWasSuccessful() {
        return wasSuccessful;
    }

    public void setWasSuccessful(Byte wasSuccessful) {
        this.wasSuccessful = wasSuccessful;
    }

    public Integer getCountSuccessfulTests() {
        return countSuccessfulTests;
    }

    public void setCountSuccessfulTests(Integer countSuccessfulTests) {
        this.countSuccessfulTests = countSuccessfulTests;
    }

    public Integer getCountFailedTests() {
        return countFailedTests;
    }

    public void setCountFailedTests(Integer countFailedTests) {
        this.countFailedTests = countFailedTests;
    }

    public Integer getCountAllTests() {
        return countAllTests;
    }

    public void setCountAllTests(Integer countAllTests) {
        this.countAllTests = countAllTests;
    }

    public UUID getIdTask() {
        return idTask;
    }

    public void setIdTask(UUID idTask) {
        this.idTask = idTask;
    }

    public UUID getIdTest() {
        return idTest;
    }

    public void setIdTest(UUID idTest) {
        this.idTest = idTest;
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
        CompletedTask that = (CompletedTask) o;
        return Objects.equals(super.getId(), that.getId()) &&
                Objects.equals(sourceCode, that.sourceCode) &&
                Objects.equals(countSuccessfulTests, that.countSuccessfulTests) &&
                Objects.equals(countFailedTests, that.countFailedTests) &&
                Objects.equals(countAllTests, that.countAllTests) &&
                Objects.equals(wasSuccessful, that.wasSuccessful) &&
                Objects.equals(idTask, that.idTask) &&
                Objects.equals(idTest, that.idTest) &&
                Objects.equals(idUser, that.idUser) &&
                Objects.equals(super.getCreatedAt(), that.getCreatedAt()) &&
                Objects.equals(super.getUpdatedAt(), that.getUpdatedAt()) &&
                Objects.equals(super.getStatus(), that.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId(), sourceCode, countSuccessfulTests, countFailedTests,
                countAllTests, wasSuccessful, idTask, idTest, idUser, super.getCreatedAt(), super.getUpdatedAt());
    }

    @Override
    public String toString() {
        return "CompletedTask{" +
                "idCompletedTask=" + super.getId() +
                ", sourceCode='" + sourceCode + '\'' +
                ", countSuccessfulTests=" + countSuccessfulTests +
                ", countFailedTests=" + countFailedTests +
                ", countAllTests=" + countAllTests +
                ", wasSuccessful=" + wasSuccessful +
                ", idTask=" + idTask +
                ", idTest=" + idTest +
                ", idUser=" + idUser +
                ", createdAt=" + super.getCreatedAt() +
                ", updatedAt=" + super.getUpdatedAt() +
                ", status=" + super.getStatus() +
                '}';
    }
}
