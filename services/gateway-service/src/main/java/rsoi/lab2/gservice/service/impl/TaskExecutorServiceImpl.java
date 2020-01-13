package rsoi.lab2.gservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rsoi.lab2.gservice.client.*;
import rsoi.lab2.gservice.conf.WebConfig;
import rsoi.lab2.gservice.entity.completedtask.CompletedTask;
import rsoi.lab2.gservice.entity.result.Result;
import rsoi.lab2.gservice.entity.test.Test;
import rsoi.lab2.gservice.exception.HttpCanNotCreateException;
import rsoi.lab2.gservice.exception.HttpNotFoundException;
import rsoi.lab2.gservice.exception.ServiceAccessException;
import rsoi.lab2.gservice.exception.feign.ClientAuthenticationExceptionWrapper;
import rsoi.lab2.gservice.exception.feign.ClientNotFoundExceptionWrapper;
import rsoi.lab2.gservice.exception.jwt.JwtAuthenticationException;
import rsoi.lab2.gservice.model.*;
import rsoi.lab2.gservice.model.executetask.ExecuteTask;
import rsoi.lab2.gservice.model.executetask.ExecuteTaskRequest;
import rsoi.lab2.gservice.model.result.ResultTest;
import rsoi.lab2.gservice.model.result.ResultWrapper;
import rsoi.lab2.gservice.service.TaskExecutorService;
import rsoi.lab2.gservice.service.TaskService;

import java.util.*;

@Service
public class TaskExecutorServiceImpl implements TaskExecutorService {

    private static final Logger logger = LoggerFactory.getLogger(TaskExecutorService.class);

    private static String testToken = null;
    private static String resultToken = null;
    private static String taskExecutorToken = null;

    private TestClient testClient;
    private TaskExecutorClient taskExecutorClient;
    private ResultClient resultClient;

    @Autowired
    public TaskExecutorServiceImpl(TestClient testClient, TaskExecutorClient taskExecutorClient, ResultClient resultClient) {
        this.testClient = testClient;
        this.taskExecutorClient = taskExecutorClient;
        this.resultClient = resultClient;
        testToken = testClient.getToken(String.format("Basic base64(%s:%s)", WebConfig.getAppKey(), WebConfig.getAppSecret()))
                .get("access_token");
        taskExecutorToken = taskExecutorClient.getToken(String.format("Basic base64(%s:%s)", WebConfig.getAppKey(), WebConfig.getAppSecret()))
                .get("access_token");
        resultToken = resultClient.getToken(String.format("Basic base64(%s:%s)", WebConfig.getAppKey(), WebConfig.getAppSecret()))
                .get("access_token");
    }

    @Override
    public CompletedTask findById(UUID id) {
        try {
            logger.info("findById() method called:");
            CompletedTask result = taskExecutorClient.findById(id, taskExecutorToken)
                    .orElseThrow(() -> new HttpNotFoundException("CompletedTask could not be found with id: " + id));

            UUID zeroUUID = new UUID(0, 0);
            if (result.getId().equals(zeroUUID))
                throw new ServiceAccessException("CompletedTask service unavailable.");

            logger.info("\t" + result);
            return result;
        } catch (ClientAuthenticationExceptionWrapper exc) {
            taskExecutorToken = taskExecutorClient.getToken(String.format("Basic base64(%s:%s)", WebConfig.getAppKey(), WebConfig.getAppSecret()))
                    .get("access_token");
            if (taskExecutorToken != null) {
                taskExecutorToken = "Bearer " + taskExecutorToken;
                return findById(id);
            }
            else
                throw new ServiceAccessException("CompletedTask service unavailable.");
        }
    }

    @Override
    public CompletedTask findByUserIdAndCompletedTaskId(UUID idUser, UUID idCompletedTask) {
        try {
            logger.info("findByUserIdAndCompletedTaskId() method called:");
            CompletedTask result = taskExecutorClient.findByUserIdAndCompletedTaskId(idUser, idCompletedTask, taskExecutorToken)
                    .orElseThrow(() -> new HttpNotFoundException("CompletedTask could not be found with idUser: " + idUser + " and idCompletedTask: " + idCompletedTask));

            UUID zeroUUID = new UUID(0, 0);
            if (result.getId().equals(zeroUUID))
                throw new ServiceAccessException("CompletedTask service unavailable.");

            logger.info("\t" + result);
            return result;
        } catch (ClientAuthenticationExceptionWrapper exc) {
            taskExecutorToken = taskExecutorClient.getToken(String.format("Basic base64(%s:%s)", WebConfig.getAppKey(), WebConfig.getAppSecret()))
                    .get("access_token");
            if (taskExecutorToken != null) {
                taskExecutorToken = "Bearer " + taskExecutorToken;
                return findByUserIdAndCompletedTaskId(idUser, idCompletedTask);
            }
            else
                throw new ServiceAccessException("CompletedTask service unavailable.");
        }
    }

    @Override
    public CompletedTask findByTestIdAndCompletedTaskId(UUID idTest, UUID idCompletedTask) {
        try {
            logger.info("findByTestIdAndCompletedTaskId() method called:");
            CompletedTask result = taskExecutorClient.findByTestIdAndCompletedTaskId(idTest, idCompletedTask, taskExecutorToken)
                    .orElseThrow(() -> new HttpNotFoundException("CompletedTask could not be found with idTest: " + idTest + " and idCompletedTask: " + idCompletedTask));

            UUID zeroUUID = new UUID(0, 0);
            if (result.getId().equals(zeroUUID))
                throw new ServiceAccessException("CompletedTask service unavailable.");

            logger.info("\t" + result);
            return result;
        } catch (ClientAuthenticationExceptionWrapper exc) {
            taskExecutorToken = taskExecutorClient.getToken(String.format("Basic base64(%s:%s)", WebConfig.getAppKey(), WebConfig.getAppSecret()))
                    .get("access_token");
            if (taskExecutorToken != null) {
                taskExecutorToken = "Bearer " + taskExecutorToken;
                return findByTestIdAndCompletedTaskId(idTest, idCompletedTask);
            }
            else
                throw new ServiceAccessException("CompletedTask service unavailable.");
        }
    }

    @Override
    public PageCustom<CompletedTask> findAll(Integer page, Integer size) {
        try {
            logger.info("findAll() method called:");
            PageCustom<CompletedTask> results = taskExecutorClient.findAll(page, size, taskExecutorToken);
            if (results == null)
                throw new ServiceAccessException("CompletedTask service unavailable.");

            logger.info("\t" + results.getContent());
            return results;
        } catch (ClientAuthenticationExceptionWrapper exc) {
            taskExecutorToken = taskExecutorClient.getToken(String.format("Basic base64(%s:%s)", WebConfig.getAppKey(), WebConfig.getAppSecret()))
                    .get("access_token");
            if (taskExecutorToken != null) {
                taskExecutorToken = "Bearer " + taskExecutorToken;
                return findAll(page, size);
            }
            else
                throw new ServiceAccessException("CompletedTask service unavailable.");
        }
    }

    @Override
    public PageCustom<CompletedTask> findByUserId(UUID id, Integer page, Integer size) {
        try {
            logger.info("findByUserId() method called:");
            PageCustom<CompletedTask> results = taskExecutorClient.findByUserId(id, page, size, taskExecutorToken);
            if (results == null)
                throw new ServiceAccessException("CompletedTask service unavailable.");

            logger.info("\t" + results.getContent());
            return results;
        } catch (ClientAuthenticationExceptionWrapper exc) {
            taskExecutorToken = taskExecutorClient.getToken(String.format("Basic base64(%s:%s)", WebConfig.getAppKey(), WebConfig.getAppSecret()))
                    .get("access_token");
            if (taskExecutorToken != null) {
                taskExecutorToken = "Bearer " + taskExecutorToken;
                return findByUserId(id, page, size);
            }
            else
                throw new ServiceAccessException("CompletedTask service unavailable.");
        }
    }

    @Override
    public PageCustom<CompletedTask> findByTaskId(UUID id, Integer page, Integer size) {
        try {
            logger.info("findByTaskId() method called:");
            PageCustom<CompletedTask> results = taskExecutorClient.findByTaskId(id, page, size, taskExecutorToken);
            if (results == null)
                throw new ServiceAccessException("CompletedTask service unavailable.");

            logger.info("\t" + results.getContent());
            return results;
        } catch (ClientAuthenticationExceptionWrapper exc) {
            taskExecutorToken = taskExecutorClient.getToken(String.format("Basic base64(%s:%s)", WebConfig.getAppKey(), WebConfig.getAppSecret()))
                    .get("access_token");
            if (taskExecutorToken != null) {
                taskExecutorToken = "Bearer " + taskExecutorToken;
                return findByTaskId(id, page, size);
            }
            else
                throw new ServiceAccessException("CompletedTask service unavailable.");
        }
    }

    @Override
    public PageCustom<CompletedTask> findByTestId(UUID id, Integer page, Integer size) {
        try {
            logger.info("findByTestId() method called:");
            PageCustom<CompletedTask> results = taskExecutorClient.findByTestId(id, page, size, taskExecutorToken);
            if (results == null)
                throw new ServiceAccessException("CompletedTask service unavailable.");

            logger.info("\t" + results.getContent());
            return results;
        } catch (ClientAuthenticationExceptionWrapper exc) {
            taskExecutorToken = taskExecutorClient.getToken(String.format("Basic base64(%s:%s)", WebConfig.getAppKey(), WebConfig.getAppSecret()))
                    .get("access_token");
            if (taskExecutorToken != null) {
                taskExecutorToken = "Bearer " + taskExecutorToken;
                return findByTestId(id, page, size);
            }
            else
                throw new ServiceAccessException("CompletedTask service unavailable.");
        }
    }

    @Override
    public PageCustom<CompletedTask> findByUserIdAndTaskId(UUID idUser, UUID idTask, Integer page, Integer size) {
        try {
            logger.info("findByUserIdAndTaskId() method called:");
            PageCustom<CompletedTask> results = taskExecutorClient.findByUserIdAndTaskId(idUser, idTask, page, size, taskExecutorToken);
            if (results == null)
                throw new ServiceAccessException("CompletedTask service unavailable.");

            logger.info("\t" + results.getContent());
            return results;
        } catch (ClientAuthenticationExceptionWrapper exc) {
            taskExecutorToken = taskExecutorClient.getToken(String.format("Basic base64(%s:%s)", WebConfig.getAppKey(), WebConfig.getAppSecret()))
                    .get("access_token");
            if (taskExecutorToken != null) {
                taskExecutorToken = "Bearer " + taskExecutorToken;
                return findByUserIdAndTaskId(idUser, idTask, page, size);
            }
            else
                throw new ServiceAccessException("CompletedTask service unavailable.");
        }
    }

    @Override
    public ResultTest execute(ExecuteTaskRequest request) {
        try {
            logger.info("execute() method called:");
            Test test = testClient.findByTaskId(request.getIdTask(), testToken)
                    .orElseThrow(() -> new HttpNotFoundException("Test could not be found with idTask: " + request.getIdTask()));

            UUID zeroUUID = new UUID(0, 0);
            if (test.getId().equals(zeroUUID))
                throw new ServiceAccessException("Test service unavailable.");

            ExecuteTask executeTask = new ExecuteTask();
            executeTask.setIdTask(request.getIdTask());
            executeTask.setIdTest(test.getId());
            executeTask.setIdUser(request.getIdUser());
            executeTask.setSourceTest(test.getSourceCode());
            executeTask.setSourceTask(request.getSourceTask());
            logger.info("\tTask for execute: " + executeTask);
            ResultWrapper resultWrapper = taskExecutorClient.execute(executeTask, taskExecutorToken)
                    .orElseThrow(() -> new HttpCanNotCreateException("Task could not be executed."));

            if (resultWrapper.getIdCompletedTask().equals(zeroUUID))
                throw new ServiceAccessException("CompletedTask service unavailable.");

            logger.info("\tWrapper Result: " + resultWrapper);
            ResultTest resultTest = resultWrapper.getResultTest();
            prResultTestFail(resultTest);
            logger.info("\tResult Test: " + resultTest);

            double mark = resultTest.getCountSuccessfulTests() * 100. / resultTest.getCountAllTests()
                    - resultTest.getCountFailedTests() * 100. / resultTest.getCountAllTests();
            mark = (mark < 0) ? 0 : mark;
            try {
                Result result = updateResult(executeTask.getIdUser(), executeTask.getIdTask(), mark);
                logger.info("\tupdated " + result);

            } catch (ClientNotFoundExceptionWrapper exc) {
                Result result = createResult(executeTask.getIdUser(), executeTask.getIdTask(), mark);
                logger.info("\tcreated " + result);
            }
            catch (ServiceAccessException exc) {
                taskExecutorClient.delete(resultWrapper.getIdCompletedTask(), taskExecutorToken);
                throw new ServiceAccessException(exc.getMessage());
            }
            return resultTest;
        } catch (ClientAuthenticationExceptionWrapper exc) {
            logger.info("get test token");
            testToken = testClient.getToken(String.format("Basic base64(%s:%s)", WebConfig.getAppKey(), WebConfig.getAppSecret()))
                    .get("access_token");
            logger.info("get task token");
            taskExecutorToken = taskExecutorClient.getToken(String.format("Basic base64(%s:%s)", WebConfig.getAppKey(), WebConfig.getAppSecret()))
                    .get("access_token");
            if (taskExecutorToken != null && testToken != null) {
                taskExecutorToken = "Bearer " + taskExecutorToken;
                testToken = "Bearer " + testToken;
                return execute(request);
            }
            throw new ServiceAccessException("Test or TaskExecutor service unavailable.");
        }
    }

    private Result updateResult(UUID idUser, UUID idTask, Double mark) {
        try {
            Result result = resultClient.findByUserIdAndTaskId(idUser, idTask, resultToken).orElse(null);
            if (result == null)
                return null;

            UUID zeroUUID = new UUID(0, 0);
            if (result.getIdUser().equals(zeroUUID) || result.getIdTask().equals(zeroUUID))
                throw new ServiceAccessException("Result service unavailable.");

            result.setCountAttempt(result.getCountAttempt() + 1);
            result.setMark(mark);
            resultClient.update(idUser, idTask, result, resultToken);
            return result;
        } catch (ClientAuthenticationExceptionWrapper exc) {
            resultToken = resultClient.getToken(String.format("Basic base64(%s:%s)", WebConfig.getAppKey(), WebConfig.getAppSecret()))
                    .get("access_token");
            if (resultToken != null) {
                resultToken = "Bearer " + resultToken;
                return updateResult(idUser, idTask, mark);
            }
            else
                throw new ServiceAccessException("Result service unavailable.");
        }
    }

    private Result createResult(UUID idUser, UUID idTask, Double mark) {
        try {
            Result result = new Result();
            result.setIdTask(idTask);
            result.setIdUser(idUser);
            result.setCountAttempt(1);
            result.setMark(mark);
            result = resultClient.create(result, resultToken)
                    .orElseThrow(() -> new HttpCanNotCreateException("Result could not be created"));

            UUID zeroUUID = new UUID(0, 0);
            if (result.getIdUser().equals(zeroUUID) || result.getIdTask().equals(zeroUUID))
                throw new ServiceAccessException("Result service unavailable.");

            return result;
        } catch (ClientAuthenticationExceptionWrapper exc) {
            resultToken = resultClient.getToken(String.format("Basic base64(%s:%s)", WebConfig.getAppKey(), WebConfig.getAppSecret()))
                    .get("access_token");
            if (resultToken != null) {
                resultToken = "Bearer " + resultToken;
                return createResult(idUser, idTask, mark);
            }
            else
                throw new ServiceAccessException("Result service unavailable.");
        }
    }

    private void prResultTestFail(ResultTest resultTest) {
        for (int i = 0; i < resultTest.getFails().size(); i++)
            resultTest.getFails().set(i, resultTest.getFails().get(i)
                    .replaceAll("<", "").replaceAll(">", ""));
    }
}
