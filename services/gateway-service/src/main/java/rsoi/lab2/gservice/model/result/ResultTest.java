package rsoi.lab2.gservice.model.result;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class ResultTest implements Serializable {

    public static final long serialVersionUID = 1234;
    private int countAllTests;
    private int countFailedTests;
    private int countSuccessfulTests;
    private int wasSuccessful;
    private List<String> fails;

    public int getCountAllTests() {
        return countAllTests;
    }

    public void setCountAllTests(int countAllTests) {
        this.countAllTests = countAllTests;
    }

    public int getCountFailedTests() {
        return countFailedTests;
    }

    public void setCountFailedTests(int countFailedTests) {
        this.countFailedTests = countFailedTests;
    }

    public int getCountSuccessfulTests() {
        return countSuccessfulTests;
    }

    public void setCountSuccessfulTests(int countSuccessfulTests) {
        this.countSuccessfulTests = countSuccessfulTests;
    }

    public int getWasSuccessful() {
        return wasSuccessful;
    }

    public void setWasSuccessful(int wasSuccessful) {
        this.wasSuccessful = wasSuccessful;
    }

    public List<String> getFails() {
        return fails;
    }

    public void setFails(List<String> fails) {
        this.fails = fails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultTest that = (ResultTest) o;
        return countAllTests == that.countAllTests &&
                countFailedTests == that.countFailedTests &&
                countSuccessfulTests == that.countSuccessfulTests &&
                wasSuccessful == that.wasSuccessful &&
                Objects.equals(fails, that.fails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countAllTests, countFailedTests, countSuccessfulTests, wasSuccessful, fails);
    }

    @Override
    public String toString() {
        return "ResultTest{" +
                "countAllTests=" + countAllTests +
                ", countFailedTests=" + countFailedTests +
                ", countSuccessfulTests=" + countSuccessfulTests +
                ", wasSuccessful=" + wasSuccessful +
                ", fails=" + fails +
                '}';
    }
}
