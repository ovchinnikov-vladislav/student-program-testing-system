package rsoi.lab2.teservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rsoi.lab2.teservice.TaskExecutorServiceApp;
import rsoi.lab2.teservice.entity.CompletedTask;
import rsoi.lab2.teservice.exception.HttpNotFoundException;
import rsoi.lab2.teservice.exception.NotFoundNameClassException;
import rsoi.lab2.teservice.exception.NotRunTestException;
import rsoi.lab2.teservice.model.ExecuteTaskRequest;
import rsoi.lab2.teservice.model.ResultTest;
import rsoi.lab2.teservice.model.SomeCompletedTaskModel;
import rsoi.lab2.teservice.repository.CompletedTaskRepository;
import rsoi.lab2.teservice.service.TaskExecutorService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.TERMINATE;

@Service
public class TaskExecutorServiceImpl implements TaskExecutorService {

    private static final Logger logger = LoggerFactory.getLogger(TaskExecutorService.class);

    @Autowired
    private CompletedTaskRepository completedTaskRepository;

    @Override
    public List<SomeCompletedTaskModel> getAll() {
        logger.info("getAll() method called:");
        List<SomeCompletedTaskModel> results = completedTaskRepository.findAllCompletedTasks();
        logger.info("\t" + results);
        return results;
    }

    @Override
    public List<SomeCompletedTaskModel> getAll(Pageable pageable) {
        logger.info("getAll() method called:");
        List<SomeCompletedTaskModel> results = completedTaskRepository.findAllCompletedTasks(pageable);
        logger.info("\t" + results);
        return results;
    }

    @Override
    public List<SomeCompletedTaskModel> getByTaskId(Long id) {
        logger.info("getByTaskId() method called:");
        List<SomeCompletedTaskModel> results = completedTaskRepository.findByIdTask(id);
        logger.info("\t" + results);
        return results;
    }

    @Override
    public List<SomeCompletedTaskModel> getByTaskId(Long id, Pageable pageable) {
        logger.info("getByTaskId() method called:");
        List<SomeCompletedTaskModel> results = completedTaskRepository.findByIdTask(id, pageable);
        logger.info("\t" + results);
        return results;
    }

    @Override
    public List<SomeCompletedTaskModel> getByUserId(Long id) {
        logger.info("getByUserId() method called:");
        List<SomeCompletedTaskModel> results = completedTaskRepository.findByIdUser(id);
        logger.info("\t" + results);
        return results;
    }

    @Override
    public List<SomeCompletedTaskModel> getByUserId(Long id, Pageable pageable) {
        logger.info("getByUserId() method called:");
        List<SomeCompletedTaskModel> results = completedTaskRepository.findByIdUser(id, pageable);
        logger.info("\t" + results);
        return results;
    }

    @Override
    public List<SomeCompletedTaskModel> getByTestId(Long id) {
        logger.info("getByTestId() method called:");
        List<SomeCompletedTaskModel> results = completedTaskRepository.findByIdTest(id);
        logger.info("\t" + results);
        return results;
    }

    @Override
    public List<SomeCompletedTaskModel> getByTestId(Long id, Pageable pageable) {
        logger.info("getByTestId() method called:");
        List<SomeCompletedTaskModel> results = completedTaskRepository.findByIdTest(id, pageable);
        logger.info("\t" + results);
        return results;
    }

    @Override
    public CompletedTask getById(Long id) {
        logger.info("getById() method called:");
        CompletedTask result = completedTaskRepository
                .findById(id).orElseThrow(() -> new HttpNotFoundException("Not found CompletedTask by id = " + id));
        logger.info("\t" + result);
        return result;
    }

    @Override
    public CompletedTask getByUserIdAndCompletedTaskId(Long idUser, Long idCompletedTask) {
        logger.info("getByUserIdAndCompletedTaskId() method called:");
        CompletedTask result = completedTaskRepository
                .findByIdUserAndIdCompletedTask(idUser, idCompletedTask)
                .orElseThrow(() -> new HttpNotFoundException("Not found CompletedTask by idUser = " + idUser +
                        " and idCompletedTask = " + idCompletedTask));
        logger.info("\t" + result);
        return result;
    }

    @Override
    public CompletedTask getByTaskIdAndCompletedTaskId(Long idTask, Long idCompletedTask) {
        logger.info("getByTaskIdAndCompletedTaskId() method called:");
        CompletedTask result = completedTaskRepository
                .findByIdTaskAndIdCompletedTask(idTask, idCompletedTask)
                .orElseThrow(() -> new HttpNotFoundException("Not found CompletedTask by idTask = " + idTask +
                        " and idCompletedTask = " + idCompletedTask));
        logger.info("\t" + result);
        return result;
    }

    @Override
    public CompletedTask getByTestIdAndCompletedTaskId(Long idTest, Long idCompletedTask) {
        logger.info("getByTestIdAndCompletedTaskId() method called:");
        CompletedTask result = completedTaskRepository
                .findByIdTestAndIdCompletedTask(idTest, idCompletedTask)
                .orElseThrow(() -> new HttpNotFoundException("Not found CompletedTask by idTest = " + idTest +
                        " and idCompletedTask = " + idCompletedTask));
        logger.info("\t" + result);
        return result;
    }

    @Override
    public CompletedTask create(CompletedTask completedTask) {
        logger.info("create() method called:");
        CompletedTask result = completedTaskRepository.saveAndFlush(completedTask);
        logger.info("\t" + result);
        return result;
    }

    @Override
    public void update(CompletedTask completedTask) {
        logger.info("update() method called:");
        CompletedTask result = completedTaskRepository.saveAndFlush(completedTask);
        logger.info("\t" + result);
    }

    @Override
    public void delete(Long id) {
        logger.info("delete() method called.");
        completedTaskRepository.deleteById(id);
    }

    @Override
    public ResultTest execute(ExecuteTaskRequest executeTaskRequest) throws IOException, ClassNotFoundException {
        logger.info("execute() method called:");
        long idUser = executeTaskRequest.getIdUser();
        long idTask = executeTaskRequest.getIdTask();
        long idTest = executeTaskRequest.getIdTest();
        String sourceTask = "package rsoi.lab2.teservice.model; " + executeTaskRequest.getSourceTask();
        String sourceTest = "package rsoi.lab2.teservice.model; " + executeTaskRequest.getSourceTest();

        File file = File.createTempFile("files_test", "");
        boolean isDeleteTempFile = file.delete();
        if (!isDeleteTempFile) {
            throw new NotRunTestException("Test dont execute.");
        }

        boolean isTempCreateDir = file.mkdir();
        if (!isTempCreateDir) {
            throw new NotRunTestException("Test dont execute.");
        }

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

    private boolean createAndPrepareDirectories(String directory, String packageName, String sourceTask, String sourceTest) {
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

    private Process executeTask(String directory, String packageName) throws IOException {
        String javac = String.format(
                "javac -sourcepath %2$s. -cp \"%2$s.;%1$sjunit.jar;%1$shamcrest.jar\" %2$s." + File.separator + "*.java && ",
                directory + File.separator, directory + File.separator + packageName + File.separator);
        String java = String.format(
                "java -cp \"%1$s.;%1$sjunit.jar;%1$shamcrest.jar\" %2$sRunTest " + directory,
                directory + File.separator, "rsoi.lab2.teservice.model.");
        Process process = Runtime.getRuntime().exec("cmd /c " + javac + " cmd /c " + java);
        try {
            process.waitFor(10, TimeUnit.SECONDS);
        } catch (InterruptedException exc) {
            exc.printStackTrace();
        }
        return process;
    }

    private String searchNameClass(String source) {
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

    private String resultTestCode() {
        return "package rsoi.lab2.teservice.model;import java.io.Serializable;import java.util.List;" +
                "public class ResultTest implements Serializable {private int countAllTests;private " +
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
                "public void setFails(List<String> fails){this.fails = fails;}}";
    }

    private String runTestCode() {
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
                "for (Failure f : failures) {fails.add(f.getMessage());}" +
                "FileOutputStream stream = new FileOutputStream(args[0] + File.separator + \"Result.object\");" +
                "try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(stream)) " +
                "{objectOutputStream.writeObject(resultTest);objectOutputStream.flush();}}}";
    }
}
