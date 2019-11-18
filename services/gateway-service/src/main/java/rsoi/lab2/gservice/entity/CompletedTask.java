package rsoi.lab2.gservice.entity;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

public class CompletedTask implements Serializable {

    private Long idCompletedTask;

    @NotEmpty
    @Size(max=10000)
    private String sourceCode;
    private Byte wasSuccessful;
    @NotNull
    @Value("${some.key:0}")
    private Integer countSuccessfulTests;
    @NotNull
    @Value("${some.key:0}")
    private Integer countFailedTests;
    @NotNull
    @Value("${some.key:0}")
    private Integer countAllTests;
    @NotNull
    @DecimalMin(value = "1")
    private Long idTask;
    @NotNull
    @DecimalMin(value = "1")
    private Long idTest;
    @NotNull
    @DecimalMin(value = "1")
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
