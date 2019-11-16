package rsoi.lab2.teservice.entity;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "completed_task")
public class CompletedTask implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_completed_task")
    private Long idCompletedTask;

    @NotEmpty
    @Column(name = "source_code")
    private String sourceCode;

    @NotNull
    @Column(name = "count_successful_tests")
    @Value("${some.key:0}")
    private Integer countSuccessfulTests;

    @NotNull
    @Column(name = "count_failed_tests")
    @Value("${some.key:0}")
    private Integer countFailedTests;

    @NotNull
    @Column(name = "count_all_tests")
    @Value("${some.key:0}")
    private Integer countAllTests;

    @NotNull
    @Column(name = "was_successful")
    private Byte wasSuccessful;

    @NotNull
    @DecimalMin(value = "1")
    @Column(name = "id_task")
    private Long idTask;

    @NotNull
    @DecimalMin(value = "1")
    @Column(name = "id_test")
    private Long idTest;

    @NotNull
    @DecimalMin(value = "1")
    @Column(name = "id_user")
    private Long idUser;

    public Long getIdCompletedTask() {
        return idCompletedTask;
    }

    public void setIdCompletedTask(Long idCompletedTask) {
        this.idCompletedTask = idCompletedTask;
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

    public Long getIdTask() {
        return idTask;
    }

    public void setIdTask(Long idTask) {
        this.idTask = idTask;
    }

    public Long getIdTest() {
        return idTest;
    }

    public void setIdTest(Long idTest) {
        this.idTest = idTest;
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
        CompletedTask that = (CompletedTask) o;
        return Objects.equals(idCompletedTask, that.idCompletedTask) &&
                Objects.equals(sourceCode, that.sourceCode) &&
                Objects.equals(wasSuccessful, that.wasSuccessful) &&
                Objects.equals(countSuccessfulTests, that.countSuccessfulTests) &&
                Objects.equals(countFailedTests, that.countFailedTests) &&
                Objects.equals(countAllTests, that.countAllTests) &&
                Objects.equals(idTask, that.idTask) &&
                Objects.equals(idTest, that.idTest) &&
                Objects.equals(idUser, that.idUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCompletedTask, sourceCode, wasSuccessful, countSuccessfulTests, countFailedTests, countAllTests, idTask, idTest, idUser);
    }

    @Override
    public String toString() {
        return "CompletedTask{" +
                "idCompletedTask=" + idCompletedTask +
                ", sourceCode='" + sourceCode + '\'' +
                ", wasSuccessful='" + wasSuccessful + '\'' +
                ", countSuccessfulTests=" + countSuccessfulTests +
                ", countFailedTests=" + countFailedTests +
                ", countAllTests=" + countAllTests +
                ", idTask=" + idTask +
                ", idTest=" + idTest +
                ", idUser=" + idUser +
                '}';
    }
}
