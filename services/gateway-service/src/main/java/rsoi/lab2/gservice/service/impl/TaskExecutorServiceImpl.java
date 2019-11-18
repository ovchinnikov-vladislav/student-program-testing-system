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
    private TaskClient taskClient;
    @Autowired
    private TaskExecutorClient taskExecutorClient;
    @Autowired
    private ResultClient resultClient;

    @Override
    public CompletedTask findById(Long id) {
        logger.info("findById() method called:");
        CompletedTask result = taskExecutorClient.findById(id)
                .orElseThrow(() -> new HttpNotFoundException("Not found CompletedTask by id = " + id));
        logger.info("\t" + result);
        return result;
    }

    @Override
    public CompletedTask findByUserIdAndCompletedTaskId(Long idUser, Long idCompletedTask) {
        logger.info("findByUserIdAndCompletedTaskId() method called:");
        CompletedTask result = taskExecutorClient.findByUserIdAndCompletedTaskId(idUser, idCompletedTask)
                .orElseThrow(() -> new HttpNotFoundException("Not found CompletedTask by idUser = " + idUser + " and idCompletedTask = " + idCompletedTask));
        logger.info("\t" + result);
        return result;
    }

    @Override
    public CompletedTask findByTestIdAndCompletedTaskId(Long idTest, Long idCompletedTask) {
        logger.info("findByTestIdAndCompletedTaskId() method called:");
        CompletedTask result = taskExecutorClient.findByTestIdAndCompletedTaskId(idTest, idCompletedTask)
                .orElseThrow(() -> new HttpNotFoundException("Not found CompletedTask by idTest = " + idTest + " and idCompletedTask = " + idCompletedTask));
        logger.info("\t" + result);
        return result;
    }

    @Override
    public CompletedTask[] findAll(Integer page, Integer size) {
        logger.info("findAll() method called:");
        CompletedTask[] results = taskExecutorClient.findAll(page, size);
        logger.info("\t" + Arrays.toString(results));
        return results;
    }

    @Override
    public CompletedTask[] findByUserId(Long id, Integer page, Integer size) {
        logger.info("findByUserId() method called:");
        CompletedTask[] results = taskExecutorClient.findByUserId(id, page, size);
        logger.info("\t" + Arrays.toString(results));
        return results;
    }

    @Override
    public CompletedTask[] findByTaskId(Long id, Integer page, Integer size) {
        logger.info("findByTaskId() method called:");
        CompletedTask[] results = taskExecutorClient.findByTaskId(id, page, size);
        logger.info("\t" + Arrays.toString(results));
        return results;
    }

    @Override
    public CompletedTask[] findByTestId(Long id, Integer page, Integer size) {
        logger.info("findByTestId() method called:");
        CompletedTask[] results = taskExecutorClient.findByTestId(id, page, size);
        logger.info("\t" + Arrays.toString(results));
        return results;
    }

    @Override
    public ResultTest execute(ExecuteTaskRequest request) {
        logger.info("execute() method called:");
        Test test = testClient.findByTaskId(request.getIdTask())
                .orElseThrow(() -> new HttpNotFoundException("Not found Test by idTask = " + request.getIdTask()));

        ExecuteTask executeTask = new ExecuteTask();
        executeTask.setIdTask(request.getIdTask());
        executeTask.setIdTest(test.getIdTest());
        executeTask.setIdUser(request.getIdUser());
        executeTask.setSourceTest(test.getSourceCode());
        executeTask.setSourceTask(request.getSourceTask());

        ResultTest resultTest = taskExecutorClient.execute(executeTask)
                .orElseThrow(() -> new HttpCanNotCreateException("Task cannot execute"));

        double mark = resultTest.getCountFailedTests() * 100. / resultTest.getCountAllTests();
        try {
            Result result = resultClient.findByUserIdAndTaskId(executeTask.getIdUser(), executeTask.getIdTask())
                    .orElseThrow(() -> new HttpNotFoundException("Result don't found"));
            result.setCountAttempt(result.getCountAttempt());
            resultClient.update(executeTask.getIdUser(), executeTask.getIdTask(), result);
            logger.info("\tupdated " + result);
        } catch (Exception exc) {
            Result result = new Result();
            result.setIdTask(executeTask.getIdTask());
            result.setIdUser(executeTask.getIdUser());
            result.setCountAttempt(0);
            result.setCreateDate(new Date());
            result.setMark(mark);
            result = resultClient.create(result).orElseThrow(() -> new HttpCanNotCreateException("Result cannot create"));
            logger.info("\tcreated " + result);
        }

        return resultTest;
    }
}
