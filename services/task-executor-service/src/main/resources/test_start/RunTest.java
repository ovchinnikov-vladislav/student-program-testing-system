package rsoi.lab2.teservice.model;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class RunTest {

    public static void main(String... args) throws IOException {
        JUnitCore core = new JUnitCore();
        Result result = core.runClasses(SourceTest.class);
        List<Failure> failures = result.getFailures();

        ResultTest resultTest = new ResultTest();
        resultTest.setCountAllTests(result.getRunCount() + result.getIgnoreCount());
        resultTest.setCountFailedTests(result.getFailureCount());
        resultTest.setCountSuccessfulTests(resultTest.getCountAllTests() - result.getIgnoreCount() - result.getFailureCount());
        resultTest.setWasSuccessful(result.wasSuccessful() ? 1 : 0);
        List<String> fails = new ArrayList<>();
        resultTest.setFails(fails);
        for (Failure f : failures) {
            fails.add(f.getMessage());
        }

        FileOutputStream stream = new FileOutputStream(args[0] + File.separator + "Result.object");
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(stream)) {
            objectOutputStream.writeObject(resultTest);
            objectOutputStream.flush();
        }
    }
}
