package rsoi.lab2.gservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rsoi.lab2.gservice.client.*;
import rsoi.lab2.gservice.client.TaskClient;
import rsoi.lab2.gservice.entity.*;
import rsoi.lab2.gservice.exception.HttpCanNotCreateException;
import rsoi.lab2.gservice.exception.HttpNotFoundException;
import rsoi.lab2.gservice.model.ExecuteTask;
import rsoi.lab2.gservice.model.ExecuteTaskRequest;
import rsoi.lab2.gservice.model.PageCustom;
import rsoi.lab2.gservice.model.ResultTest;
import rsoi.lab2.gservice.service.TaskExecutorService;
import rsoi.lab2.gservice.service.TaskService;

import java.util.Arrays;
import java.util.Date;

@Service
public class TaskExecutorServiceImpl implements TaskExecutorService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    private TestClient testClient;
    @Autowired
    private TaskExecutorClient taskExecutorClient;
    @Autowired
    private ResultClient resultClient;

    @Override
    public CompletedTask findById(Long id) {
        logger.info("findById() method called:");
        CompletedTask result = taskExecutorClient.findById(id)
                .orElseThrow(() -> new HttpNotFoundException("CompletedTask could not be found with id: " + id));
        logger.info("\t" + result);
        return result;
    }

    @Override
    public CompletedTask findByUserIdAndCompletedTaskId(Long idUser, Long idCompletedTask) {
        logger.info("findByUserIdAndCompletedTaskId() method called:");
        CompletedTask result = taskExecutorClient.findByUserIdAndCompletedTaskId(idUser, idCompletedTask)
                .orElseThrow(() -> new HttpNotFoundException("CompletedTask could not be found with idUser: " + idUser + " and idCompletedTask: " + idCompletedTask));
        logger.info("\t" + result);
        return result;
    }

    @Override
    public CompletedTask findByTestIdAndCompletedTaskId(Long idTest, Long idCompletedTask) {
        logger.info("findByTestIdAndCompletedTaskId() method called:");
        CompletedTask result = taskExecutorClient.findByTestIdAndCompletedTaskId(idTest, idCompletedTask)
                .orElseThrow(() -> new HttpNotFoundException("CompletedTask could not be found with idTest: " + idTest + " and idCompletedTask: " + idCompletedTask));
        logger.info("\t" + result);
        return result;
    }

    @Override
    public PageCustom<CompletedTask> findAll(Integer page, Integer size) {
        logger.info("findAll() method called:");
        PageCustom<CompletedTask> results = taskExecutorClient.findAll(page, size);
        logger.info("\t" + results.getContent());
        return results;
    }

    @Override
    public PageCustom<CompletedTask> findByUserId(Long id, Integer page, Integer size) {
        logger.info("findByUserId() method called:");
        PageCustom<CompletedTask> results = taskExecutorClient.findByUserId(id, page, size);
        logger.info("\t" + results.getContent());
        return results;
    }

    @Override
    public PageCustom<CompletedTask> findByTaskId(Long id, Integer page, Integer size) {
        logger.info("findByTaskId() method called:");
        PageCustom<CompletedTask> results = taskExecutorClient.findByTaskId(id, page, size);
        logger.info("\t" + results.getContent());
        return results;
    }

    @Override
    public PageCustom<CompletedTask> findByTestId(Long id, Integer page, Integer size) {
        logger.info("findByTestId() method called:");
        PageCustom<CompletedTask> results = taskExecutorClient.findByTestId(id, page, size);
        logger.info("\t" + results.getContent());
        return results;
    }

    @Override
    public PageCustom<CompletedTask> findByUserIdAndTaskId(Long idUser, Long idTask, Integer page, Integer size) {
        logger.info("findByUserIdAndTaskId() method called:");
        PageCustom<CompletedTask> results = taskExecutorClient.findByUserIdAndTaskId(idUser, idTask, page, size);
        logger.info("\t" + results.getContent());
        return results;
    }

    @Override
    public ResultTest execute(ExecuteTaskRequest request) {
        logger.info("execute() method called:");
        Test test = testClient.findByTaskId(request.getIdTask())
                .orElseThrow(() -> new HttpNotFoundException("Test could not be found with idTask: " + request.getIdTask()));

        ExecuteTask executeTask = new ExecuteTask();
        executeTask.setIdTask(request.getIdTask());
        executeTask.setIdTest(test.getIdTest());
        executeTask.setIdUser(request.getIdUser());
        executeTask.setSourceTest(test.getSourceCode());
        executeTask.setSourceTask(request.getSourceTask());

        ResultTest resultTest = taskExecutorClient.execute(executeTask)
                .orElseThrow(() -> new HttpCanNotCreateException("Task could not be executed"));


        double mark = resultTest.getCountSuccessfulTests() * 100. / resultTest.getCountAllTests()
                - resultTest.getCountFailedTests() * 100. / resultTest.getCountAllTests();
        mark = (mark < 0) ? 0 : mark;
        try {
            Result result = resultClient.findByUserIdAndTaskId(executeTask.getIdUser(), executeTask.getIdTask())
                    .orElseThrow(() -> new HttpNotFoundException("Result could not be found with idUser: " + executeTask.getIdUser()
                            + " and idTask: " + executeTask.getIdTask()));
            result.setCountAttempt(result.getCountAttempt());
            result.setMark(mark);
            resultClient.update(executeTask.getIdUser(), executeTask.getIdTask(), result);
            logger.info("\tupdated " + result);
        } catch (Exception exc) {
            Result result = new Result();
            result.setIdTask(executeTask.getIdTask());
            result.setIdUser(executeTask.getIdUser());
            result.setCountAttempt(0);
            result.setCreateDate(new Date());
            result.setMark(mark);
            result = resultClient.create(result).orElseThrow(() -> new HttpCanNotCreateException("Result could not be created"));
            logger.info("\tcreated " + result);
        }

        return resultTest;
    }
}
