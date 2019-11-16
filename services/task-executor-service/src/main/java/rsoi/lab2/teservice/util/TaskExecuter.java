package rsoi.lab2.teservice.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rsoi.lab2.teservice.TaskExecutorServiceApp;
import rsoi.lab2.teservice.exception.NotFoundNameClassException;
import rsoi.lab2.teservice.exception.NotRunTestException;
import rsoi.lab2.teservice.model.ExecuteTaskRequest;
import rsoi.lab2.teservice.model.ResultTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.TERMINATE;

public class TaskExecuter {

    private static final Logger logger = LoggerFactory.getLogger(TaskExecuter.class);

    public static ResultTest execute(ExecuteTaskRequest request) throws IOException, ClassNotFoundException {
        long idUser = request.getIdUser();
        long idTask = request.getIdTask();
        long idTest = request.getIdTest();
        String sourceTask = "package rsoi.lab2.teservice.model; " + request.getSourceTask();
        String sourceTest = "package rsoi.lab2.teservice.model; " + request.getSourceTest();

        File file = File.createTempFile("files_test", "");
        setTempDirectory(file);

        String directory = file.getAbsolutePath() + File.separator + "files_test" + File.separator + idUser + idTask + idTest;
        String packageName = "rsoi" + File.separator + "lab2" + File.separator + "teservice" + File.separator + "model";

        boolean isCreateDirectory = createAndPrepareDirectories(directory, packageName, sourceTask, sourceTest);

        if (!isCreateDirectory) {
            deleteFileOrFolder(Paths.get(directory));
            throw new NotRunTestException("Test dont execute.");
        }

        Process process = null;
        try {
            process = executeTask(directory, packageName);
        } catch (IOException exc) {
            exc.printStackTrace();
        }

        if (process == null) {
            deleteFileOrFolder(Paths.get(directory));
            throw new NotRunTestException("Test dont execute.");
        }

        ResultTest result = null;

        if (Files.exists(Paths.get(directory + File.separator + "Result.object"))) {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(
                    new FileInputStream(directory + File.separator + "Result.object"))) {
                result = (ResultTest) objectInputStream.readObject();
            }
        } else {
            deleteFileOrFolder(Paths.get(directory));
            throw new NotRunTestException("Test dont execute.");
        }

        deleteFileOrFolder(Paths.get(directory));
        process.destroy();

        if (result != null) {
            return result;
        }

        throw new NotRunTestException("Test dont execute.");
    }

    private static void setTempDirectory(File file) {
        boolean isDeleteTempFile = file.delete();
        if (!isDeleteTempFile) {
            throw new NotRunTestException("Test dont execute.");
        }

        boolean isTempCreateDir = file.mkdir();
        if (!isTempCreateDir) {
            throw new NotRunTestException("Test dont execute.");
        }
    }

    private static boolean createAndPrepareDirectories(String directory, String packageName, String sourceTask, String sourceTest) {
        String nameTaskClass = searchNameClass(sourceTask);
        String nameTestClass = searchNameClass(sourceTest);
        String sourceRunTest = runTestCode().replaceAll("\\[test_classes\\]", nameTestClass + ".class");
        sourceTest = sourceTest.replaceAll("\\[task_object\\]", nameTaskClass);

        try {
            Path path = Paths.get(directory + File.separator + packageName);
            Files.createDirectories(path);

            Path sourceTaskPath = Paths.get(directory + File.separator + packageName + File.separator + nameTaskClass + ".java");
            Files.writeString(sourceTaskPath, sourceTask);
            Path sourceTestPath = Paths.get(directory + File.separator + packageName + File.separator + nameTestClass + ".java");
            Files.writeString(sourceTestPath, sourceTest);
            Path sourceRunTestPath = Paths.get(directory + File.separator + packageName + File.separator + "RunTest.java");
            Files.writeString(sourceRunTestPath, sourceRunTest);
            Path sourceResultTestPath = Paths.get(directory + File.separator + packageName + File.separator + "ResultTest.java");
            Files.writeString(sourceResultTestPath, resultTestCode());

            Files.copy(Paths.get(TaskExecutorServiceApp.class.getResource("/test_start/junit.jar").toURI()), Paths.get(directory + File.separator + "junit.jar"));
            Files.copy(Paths.get(TaskExecutorServiceApp.class.getResource("/test_start/hamcrest.jar").toURI()), Paths.get(directory + File.separator + "hamcrest.jar"));
            return true;
        } catch (Exception exc) {
            logger.error("Directory dont create");
            return false;
        }
    }

    private static Process executeTask(String directory, String packageName) throws IOException {
        String commandSeparator = ";";
        String terminal = "cmd";
        String command = "/c";
        if (CheckOS.isUnix() || CheckOS.isMac()) {
            commandSeparator = ":";
            terminal = "sh";
            command = "-c";
        }
        String javac = String.format(
                "javac -sourcepath %2$s. -cp \"%2$s.%3$s%1$sjunit.jar%3$s%1$shamcrest.jar\" %2$s*.java ",
                directory + File.separator, directory + File.separator + packageName + File.separator, commandSeparator);
        String java = String.format(
                "&& java -cp \"%1$s.%3$s%1$sjunit.jar%3$s%1$shamcrest.jar\" %2$sRunTest %1$s",
                directory + File.separator, "rsoi.lab2.teservice.model.", commandSeparator);

        Process process = Runtime.getRuntime().exec(new String[] {terminal, command, javac + java});
        try {
            process.waitFor();
        } catch (InterruptedException exc) {
            exc.printStackTrace();
        }
        return process;
    }

    private static String searchNameClass(String source) {
        Pattern pattern = Pattern.compile("(?<=\\b(class|enum|interface)\\s\\b).*?(?=(\\b\\s(implements|extends)\\b)|\\{)");
        Matcher matcher = pattern.matcher(source);
        if (matcher.find()) {
            return matcher.group().trim();
        }
        throw new NotFoundNameClassException("Not found NameClass at the sourceTask.");
    }

    private static void deleteFileOrFolder(final Path path) throws IOException {
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs)
                    throws IOException {
                Files.delete(file);
                return CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(final Path file, final IOException e) {
                return handleException(e);
            }

            private FileVisitResult handleException(final IOException e) {
                e.printStackTrace();
                return TERMINATE;
            }

            @Override
            public FileVisitResult postVisitDirectory(final Path dir, final IOException e)
                    throws IOException {
                if (e != null) return handleException(e);
                Files.delete(dir);
                return CONTINUE;
            }
        });
    }

    private static String resultTestCode() {
        return "package rsoi.lab2.teservice.model;\nimport java.io.Serializable;\nimport java.util.List;\n" +
                "public class ResultTest implements Serializable {private int countAllTests;\nprivate " +
                "int countFailedTests;\nprivate int countSuccessfulTests;\nprivate int wasSuccessful;\nprivate " +
                "List<String> fails;\npublic int getCountAllTests() {\nreturn countAllTests;\n}\n" +
                "public void setCountAllTests(int countAllTests) {\nthis.countAllTests = countAllTests;\n}\n" +
                "public int getCountFailedTests() {\nreturn countFailedTests;\n}\n" +
                "public void setCountFailedTests(int countFailedTests) {\nthis.countFailedTests = countFailedTests;\n}\n" +
                "public int getCountSuccessfulTests() {\nreturn countSuccessfulTests;\n}\n" +
                "public void setCountSuccessfulTests(int countSuccessfulTests) {\nthis.countSuccessfulTests = countSuccessfulTests;\n}\n" +
                "public int getWasSuccessful() {\nreturn wasSuccessful;\n}\n" +
                "public void setWasSuccessful(int wasSuccessful) {\nthis.wasSuccessful = wasSuccessful;\n}\n" +
                "public List<String> getFails() {\nreturn fails;\n}\n" +
                "public void setFails(List<String> fails) {\nthis.fails = fails;\n}\n}";
    }

    private static String runTestCode() {
        return "package rsoi.lab2.teservice.model;\nimport org.junit.runner.JUnitCore;\n" +
                "import org.junit.runner.Result;\nimport org.junit.runner.notification.Failure;\n" +
                "import java.io.File;\nimport java.io.FileOutputStream;\nimport java.io.IOException;\n" +
                "import java.io.ObjectOutputStream;\nimport java.util.ArrayList;\nimport java.util.List;\n" +
                "public class RunTest {\npublic static void main(String... args) throws IOException " +
                "{\nJUnitCore core = new JUnitCore();\nResult result = core.runClasses([test_classes]);\n" +
                "List<Failure> failures = result.getFailures();\nResultTest resultTest = new ResultTest();\n" +
                "resultTest.setCountAllTests(result.getRunCount() + result.getIgnoreCount());\n" +
                "resultTest.setCountFailedTests(result.getFailureCount());\n" +
                "resultTest.setCountSuccessfulTests(resultTest.getCountAllTests() - " +
                "result.getIgnoreCount() - result.getFailureCount());\n" +
                "resultTest.setWasSuccessful(result.wasSuccessful() ? 1 : 0);\n" +
                "List<String> fails = new ArrayList<>();resultTest.setFails(fails);\n" +
                "for (Failure f : failures)\n fails.add(f.getMessage());\n" +
                "FileOutputStream stream = new FileOutputStream(args[0] + File.separator + \"Result.object\");\n" +
                "try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(stream)) " +
                "{\nobjectOutputStream.writeObject(resultTest);objectOutputStream.flush();\n}\n}\n}";
    }

}
