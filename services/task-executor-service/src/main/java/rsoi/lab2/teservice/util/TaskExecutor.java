package rsoi.lab2.teservice.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rsoi.lab2.teservice.TaskExecutorServiceApp;
import rsoi.lab2.teservice.exception.NotFoundNameClassException;
import rsoi.lab2.teservice.exception.NotRunTestException;
import rsoi.lab2.teservice.model.ExecuteTaskRequest;
import rsoi.lab2.teservice.model.ResultTest;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.TERMINATE;

public class TaskExecutor {

    private static final Logger logger = LoggerFactory.getLogger(TaskExecutor.class);

    public static ResultTest execute(ExecuteTaskRequest request) throws IOException, ClassNotFoundException {
        UUID idUser = request.getIdUser();
        UUID idTask = request.getIdTask();
        UUID idTest = request.getIdTest();
        String sourceTask = "package rsoi.lab2.teservice.model; " + request.getSourceTask();
        String sourceTest = "package rsoi.lab2.teservice.model; " + request.getSourceTest();

        File file = File.createTempFile("files_test", "");
        setTempDirectory(file);

        String directory = file.getAbsolutePath() + File.separator + "files_test" + File.separator + idUser + idTask;
        String packageName = "rsoi" + File.separator + "lab2" + File.separator + "teservice" + File.separator + "model";

        boolean isCreateDirectory = createAndPrepareDirectories(directory, packageName, sourceTask, sourceTest);

        if (!isCreateDirectory) {
            deleteFileOrFolder(Paths.get(directory));
            throw new NotRunTestException("Test isn't executed.");
        }

        Process process = null;
        try {
            process = executeTask(directory, packageName);
        } catch (IOException exc) {
            logger.error(exc.getMessage());
            exc.printStackTrace();
        }

        if (process == null) {
            deleteFileOrFolder(Paths.get(directory));
            throw new NotRunTestException("Test isn't executed.");
        }

        ResultTest result = null;

        if (Files.exists(Paths.get(directory + File.separator + "Result.object"))) {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(
                    new FileInputStream(directory + File.separator + "Result.object"))) {
                result = (ResultTest) objectInputStream.readObject();
                changeCodeFails(result.getFails());
                logger.info("\t" + result);
            }
        } else {
            deleteFileOrFolder(Paths.get(directory));
            throw new NotRunTestException("Test isn't executed.");
        }

        deleteFileOrFolder(Paths.get(directory));
        process.destroy();

        return result;
    }

    private static void setTempDirectory(File file) {
        boolean isDeleteTempFile = file.delete();
        if (!isDeleteTempFile) {
            logger.error("Temp directory wasn't created");
            throw new NotRunTestException("Test dont execute.");
        }

        boolean isTempCreateDir = file.mkdir();
        if (!isTempCreateDir) {
            logger.error("Temp directory wasn't created");
            throw new NotRunTestException("Test dont execute.");
        }
        logger.info("Temp directory was created");
    }

    private static void changeCodeFails(List<String> fails) throws IOException {
        if (fails != null) {
            for (int i = 0; i < fails.size(); i++) {
                String fail = fails.get(i);
                if (fail != null) {
                    fails.set(i, new String(fail.getBytes("windows-1251"), StandardCharsets.UTF_8)
                            .replaceAll("expected", "получено").replaceAll("but was", ", должно быть"));
                }
            }
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

            Files.copy(TaskExecutorServiceApp.class.getResourceAsStream("/test_start/junit.jar"), Paths.get(directory + File.separator + "junit.jar"));
            Files.copy(TaskExecutorServiceApp.class.getResourceAsStream("/test_start/hamcrest.jar"), Paths.get(directory + File.separator + "hamcrest.jar"));

            logger.info("Directory was created.");
            return true;
        } catch (FileSystemNotFoundException fsexc) {
            try {
                final URI junitURI = TaskExecutorServiceApp.class.getResource("/test_start/junit.jar").toURI();
                final URI hamcrestURI = TaskExecutorServiceApp.class.getResource("/test_start/hamcrest.jar").toURI();
                Map<String, String> env = new HashMap<>();
                env.put("create", "true");
                try {
                    FileSystem uriFS = FileSystems.newFileSystem(TaskExecutorServiceApp.class.getResource("/test_start").toURI(), env);
                } catch (FileSystemAlreadyExistsException ignore) { }
                Files.copy(TaskExecutorServiceApp.class.getResourceAsStream("/test_start/junit.jar"), Paths.get(directory + File.separator + "junit.jar"));
                Files.copy(TaskExecutorServiceApp.class.getResourceAsStream("/test_start/hamcrest.jar"), Paths.get(directory + File.separator + "hamcrest.jar"));
                logger.info("Directory was created.");
                return true;
            } catch (Exception exc) {
                exc.printStackTrace();
                return false;
            }
        } catch (Exception exc) {
            logger.error("Directory wasn't created");
            return false;
        }
    }

    private static Process executeTask(String directory, String packageName) throws IOException {
        String commandSeparator = ";";
        String terminal = "cmd";
        String command = "/c";
        if (CheckOS.isUnix() || CheckOS.isMac()) {
            logger.info("It's Unix or Max");
            commandSeparator = ":";
            terminal = "sh";
            command = "-c";
        } else {
            logger.info("It's Windows");
        }
        String javac = String.format(
                "javac -sourcepath %2$s. -cp \"%2$s.%3$s%1$sjunit.jar%3$s%1$shamcrest.jar\" %2$s*.java ",
                directory + File.separator, directory + File.separator + packageName + File.separator, commandSeparator);
        String java = String.format(
                "&& java -cp \"%1$s.%3$s%1$sjunit.jar%3$s%1$shamcrest.jar\" %2$sRunTest %1$s",
                directory + File.separator, "rsoi.lab2.teservice.model.", commandSeparator);

        Process process = Runtime.getRuntime().exec(new String[] {terminal, command, javac + java});
        try {
            logger.info("Process is executed.");
            process.waitFor();
        } catch (InterruptedException exc) {
            logger.error(exc.getMessage());
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
        return "package rsoi.lab2.teservice.model;import java.io.Serializable;import java.util.List;" +
                "public class ResultTest implements Serializable {public static final long serialVersionUID = 1234;private int countAllTests;private " +
                "int countFailedTests;private int countSuccessfulTests;private int wasSuccessful;private " +
                "List<String> fails;public int getCountAllTests() {return countAllTests;}" +
                "public void setCountAllTests(int countAllTests) {this.countAllTests = countAllTests;}" +
                "public int getCountFailedTests() {return countFailedTests;}" +
                "public void setCountFailedTests(int countFailedTests) {this.countFailedTests = countFailedTests;}" +
                "public int getCountSuccessfulTests() {return countSuccessfulTests;}" +
                "public void setCountSuccessfulTests(int countSuccessfulTests) {this.countSuccessfulTests = countSuccessfulTests;}" +
                "public int getWasSuccessful() {return wasSuccessful;}" +
                "public void setWasSuccessful(int wasSuccessful) {this.wasSuccessful = wasSuccessful;}" +
                "public List<String> getFails() {return fails;}" +
                "public void setFails(List<String> fails) {this.fails = fails;}}";
    }

    private static String runTestCode() {
        return "package rsoi.lab2.teservice.model;import org.junit.runner.JUnitCore;" +
                "import org.junit.runner.Result;import org.junit.runner.notification.Failure;" +
                "import java.io.File;import java.io.FileOutputStream;import java.io.IOException;" +
                "import java.io.ObjectOutputStream;import java.util.ArrayList;import java.util.List;" +
                "public class RunTest {public static void main(String... args) throws IOException " +
                "{JUnitCore core = new JUnitCore();Result result = core.runClasses([test_classes]);" +
                "List<Failure> failures = result.getFailures();ResultTest resultTest = new ResultTest();" +
                "resultTest.setCountAllTests(result.getRunCount() + result.getIgnoreCount());" +
                "resultTest.setCountFailedTests(result.getFailureCount());" +
                "resultTest.setCountSuccessfulTests(resultTest.getCountAllTests() - " +
                "result.getIgnoreCount() - result.getFailureCount());" +
                "resultTest.setWasSuccessful(result.wasSuccessful() ? 1 : 0);" +
                "List<String> fails = new ArrayList<>();resultTest.setFails(fails);" +
                "for (Failure f : failures) fails.add(f.getMessage());" +
                "FileOutputStream stream = new FileOutputStream(args[0] + File.separator + \"Result.object\");" +
                "try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(stream)) " +
                "{objectOutputStream.writeObject(resultTest);objectOutputStream.flush();}}}";
    }

}
