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
import rsoi.lab2.teservice.util.TaskExecuter;

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
        ResultTest resultTest = TaskExecuter.execute(executeTaskRequest);

        CompletedTask task = new CompletedTask();
        task.setIdUser(executeTaskRequest.getIdUser());
        task.setIdTask(executeTaskRequest.getIdTask());
        task.setSourceCode(executeTaskRequest.getSourceTask());
        task.setIdTest(executeTaskRequest.getIdTest());
        task.setCountSuccessfulTests(resultTest.getCountSuccessfulTests());
        task.setCountFailedTests(resultTest.getCountFailedTests());
        task.setCountAllTests(resultTest.getCountAllTests());
        task.setWasSuccessful(task.getWasSuccessful());

        this.create(task);

        return resultTest;
    }
}
