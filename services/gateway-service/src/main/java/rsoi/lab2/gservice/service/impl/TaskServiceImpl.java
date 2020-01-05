package rsoi.lab2.gservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rsoi.lab2.gservice.client.TaskClient;
import rsoi.lab2.gservice.client.TestClient;
import rsoi.lab2.gservice.conf.WebConfig;
import rsoi.lab2.gservice.entity.task.Task;
import rsoi.lab2.gservice.entity.test.Test;
import rsoi.lab2.gservice.exception.HttpCanNotCreateException;
import rsoi.lab2.gservice.exception.HttpNotFoundException;
import rsoi.lab2.gservice.exception.ServiceAccessException;
import rsoi.lab2.gservice.exception.feign.ClientAuthenticationExceptionWrapper;
import rsoi.lab2.gservice.exception.feign.ClientNotFoundExceptionWrapper;
import rsoi.lab2.gservice.exception.jwt.JwtAuthenticationException;
import rsoi.lab2.gservice.model.operation.OperationTask;
import rsoi.lab2.gservice.model.operation.OperationTest;
import rsoi.lab2.gservice.model.PageCustom;
import rsoi.lab2.gservice.repository.OperationTaskRepository;
import rsoi.lab2.gservice.repository.OperationTestRepository;
import rsoi.lab2.gservice.service.TaskService;

import java.io.IOException;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static String taskToken = null;
    private static String testToken = null;

    private OperationTaskRepository operationTaskRepository;
    private OperationTestRepository operationTestRepository;
    @Autowired
    private TaskClient taskClient;
    @Autowired
    private TestClient testClient;

    @Autowired
    public TaskServiceImpl(OperationTaskRepository operationTaskRepository, OperationTestRepository operationTestRepository) {
        this.operationTaskRepository = operationTaskRepository;
        this.operationTestRepository = operationTestRepository;
        new ThreadTask().start();
        new ThreadTest().start();
    }

    @Override
    public Task findById(UUID id) {
        try {
            logger.info("findById() method called:");
            Task result = taskClient.findById(id, taskToken)
                    .orElseThrow(() -> new HttpNotFoundException("Test could not be found with id = " + id));

            UUID zeroUUID = new UUID(0, 0);
            if (result.getId().equals(zeroUUID))
                throw new ServiceAccessException("Task service unavailable.");

            logger.info("\t" + result);
            return result;
        } catch (ClientAuthenticationExceptionWrapper exc) {
            taskToken = taskClient.getToken(String.format("Basic base64(%s:%s)", WebConfig.getAppKey(), WebConfig.getAppSecret()))
                    .get("access_token");
            if (taskToken != null) {
                taskToken = "Bearer " + taskToken;
                return findById(id);
            } else
                throw new ServiceAccessException("Task service unavailable.");
        }
    }

    @Override
    public Task findByUserIdAndTaskId(UUID idUser, UUID idTask) {
        try {
            logger.info("findByUserIdAndTaskId() method called:");
            Task result = taskClient.findByUserIdAndTaskId(idUser, idTask, taskToken)
                    .orElseThrow(() -> new HttpNotFoundException("Task could not be found with idUser: "
                            + idUser + " and idTask: " + idTask));

            UUID zeroUUID = new UUID(0, 0);
            if (result.getId().equals(zeroUUID))
                throw new ServiceAccessException("Task service unavailable.");

            result.setWithoutTest((byte) 0);
            try {
                Test test = testClient.findByUserIdAndTaskId(idUser, idTask, testToken).orElse(null);
                result.setTest(test);
                if (test != null && test.getId().equals(zeroUUID))
                    result.setWithoutTest((byte) 1);
            } catch (ClientNotFoundExceptionWrapper ignored) {
            }

            logger.info("\t" + result);
            return result;
        } catch (ClientAuthenticationExceptionWrapper exc) {
            taskToken = taskClient.getToken(String.format("Basic base64(%s:%s)", WebConfig.getAppKey(), WebConfig.getAppSecret()))
                    .get("access_token");
            testToken = testClient.getToken(String.format("Basic base64(%s:%s)", WebConfig.getAppKey(), WebConfig.getAppSecret()))
                    .get("access_token");
            if (taskToken != null && testToken != null) {
                taskToken = "Bearer " + taskToken;
                testToken = "Bearer " + testToken;
                return findByUserIdAndTaskId(idUser, idTask);
            } else if (taskToken == null)
                throw new ServiceAccessException("Task service unavailable.");
            else
                throw new ServiceAccessException("Test service unavailable.");
        }
    }

    @Override
    public PageCustom<Task> findAll(Integer page, Integer size) {
        try {
            logger.info("findAll() method called:");
            PageCustom<Task> results = taskClient.findAll(page, size, taskToken);

            if (results == null)
                throw new ServiceAccessException("Task service unavailable.");

            logger.info("\t" + results.getContent());
            return results;
        } catch (ClientAuthenticationExceptionWrapper exc) {
            taskToken = taskClient.getToken(String.format("Basic base64(%s:%s)", WebConfig.getAppKey(), WebConfig.getAppSecret()))
                    .get("access_token");
            if (taskToken != null) {
                taskToken = "Bearer " + taskToken;
                return findAll(page, size);
            } else
                throw new ServiceAccessException("Task service unavailable.");
        }
    }

    @Override
    public PageCustom<Task> findByUserId(UUID id, Integer page, Integer size) {
        try {
            logger.info("findByUserId() method called:");
            PageCustom<Task> results = taskClient.findByUserId(id, page, size, taskToken);

            if (results == null)
                throw new ServiceAccessException("Task service unavailable.");

            logger.info("\t" + results.getContent());
            return results;
        } catch (ClientAuthenticationExceptionWrapper exc) {
            taskToken = taskClient.getToken(String.format("Basic base64(%s:%s)", WebConfig.getAppKey(), WebConfig.getAppSecret()))
                    .get("access_token");
            if (taskToken != null) {
                taskToken = "Bearer " + taskToken;
                return findByUserId(id, page, size);
            } else
                throw new ServiceAccessException("Task service unavailable.");
        }
    }

    @Override
    public Task create(UUID idUser, Task task) throws IOException {
        logger.info("create() method called:");
        task.setIdUser(idUser);
        Test test = task.getTest();
        task.setTest(null);
        try {
            Task resultTask = taskClient.create(task, taskToken)
                    .orElseThrow(() -> new HttpCanNotCreateException("Task could not be created"));
            resultTask.setTest(test);
            if (isUnavailableServiceTask()) {
                test.setId(null);
                Integer id = operationTaskRepository.findMaxId();
                id = (id == null) ? 1 : ++id;
                operationTaskRepository.save(new OperationTask(id, OperationTask.TypeOperation.CREATE,
                        objectMapper.writeValueAsString(resultTask)));
                return resultTask;
            }

            logger.info("Task was created.");

            if (test != null) {
                test.setIdTask(resultTask.getId());
                test.setIdUser(idUser);
                task.setTest(test);
                Test resultTest = createTest(test);
                if (isUnavailableServiceTest()) {
                    resultTest.setId(null);
                    Integer id = operationTestRepository.findMaxId();
                    id = (id == null) ? 1 : ++id;
                    operationTestRepository.save(new OperationTest(id, OperationTest.TypeOperation.CREATE,
                            objectMapper.writeValueAsString(test)));
                    resultTask.setTest(test);
                    return resultTask;
                }
                resultTask.setTest(resultTest);
                logger.info("Test was created.");
            }
            logger.info("Task and Test successful created.");
            logger.info("\t" + resultTask);
            return resultTask;
        } catch (ClientAuthenticationExceptionWrapper exc) {
            taskToken = taskClient.getToken(String.format("Basic base64(%s:%s)", WebConfig.getAppKey(), WebConfig.getAppSecret()))
                    .get("access_token");
            if (taskToken != null) {
                task.setTest(test);
                taskToken = "Bearer " + taskToken;
                return create(idUser, task);
            }
            else
                throw new ServiceAccessException("Task service unavailable.");
        }
    }

    private Test createTest(Test test) {
        try {
            try {
                test = testClient.create(test, testToken).orElseThrow(() -> new HttpCanNotCreateException("Test could not be created"));
                return test;
            } catch (HttpCanNotCreateException exc) {
                logger.error("Test was not created.");
                logger.info("Task was deleted.");
                taskClient.delete(test.getIdTask(), taskToken);
                throw exc;
            }
        } catch (ClientAuthenticationExceptionWrapper exc) {
            testToken = testClient.getToken(String.format("Basic base64(%s:%s)", WebConfig.getAppKey(), WebConfig.getAppSecret()))
                    .get("access_token");
            if (testToken != null) {
                testToken = "Bearer " + testToken;
                return createTest(test);
            }
            else
                throw new ServiceAccessException("Task service unavailable.");
        }
    }

    @Override
    public void update(UUID idUser, UUID idTask, Task task) throws JsonProcessingException {
        logger.info("update() method called:");

        Task checkTask = taskClient.findByUserIdAndTaskId(idUser, idTask, taskToken)
                .orElseThrow(() -> new HttpNotFoundException("Task could not be found with idUser: "
                        + task.getIdUser() + " and idTask: " + task.getId()));

        task.setId(idTask);
        task.setIdUser(idUser);
        Test test = task.getTest();
        if (test != null) {
            test.setIdTask(idTask);
            test.setIdUser(idUser);
            task.setTest(test);
        }
        if (isUnavailableServiceTask()) {
            logger.error("Task service unavailable.");
            Integer id = operationTaskRepository.findMaxId();
            id = (id == null) ? 1 : ++id;
            operationTaskRepository.save(new OperationTask(id, OperationTask.TypeOperation.UPDATE, objectMapper.writeValueAsString(task)));
            return;
        }

        task.setTest(null);
        taskClient.update(idTask, task, taskToken);
        logger.info("Task was updated.");

        if (test != null && (task.getWithoutTest() == null || task.getWithoutTest() != 1)) {
            Test afterTest = null;
            task.setTest(test);
            try {
                afterTest = testClient.findByUserIdAndTaskId(test.getIdUser(), test.getIdTask(), testToken)
                        .orElseThrow(() -> new HttpNotFoundException("Test could not be found with idUser: " +
                                test.getIdUser() + " and idTask: " + test.getIdTask()));
                test.setId(afterTest.getId());

                testClient.update(test.getId(), test, testToken);
            } catch (ClientNotFoundExceptionWrapper | HttpNotFoundException exc) {
                logger.error("Test was not updated.");

                afterTest = testClient.create(test, testToken)
                        .orElseThrow(() -> new HttpCanNotCreateException("Task could not be created"));

            }
            if (isUnavailableServiceTest()) {
                logger.error("Test service unavailable.");
                test.setId(null);
                Integer id = operationTestRepository.findMaxId();
                id = (id == null) ? 1 : ++id;
                operationTestRepository.save(new OperationTest(id, OperationTest.TypeOperation.UPDATE,
                        objectMapper.writeValueAsString(test)));
                return;
            }
            logger.info("Test was updated.");
        }
        logger.info("Task and Test was updated.");
        logger.info("\t" + task);
    }

    @Override
    public void delete(UUID id) throws JsonProcessingException {
        logger.info("delete() method called: " + id);
        if (isUnavailableServiceTask()) {
            logger.error("Task service unavailable.");
            Integer idOp = operationTaskRepository.findMaxId();
            idOp = (idOp == null) ? 1 : ++idOp;
            Task task = new Task();
            task.setId(id);
            operationTaskRepository.save(new OperationTask(idOp, OperationTask.TypeOperation.DELETE,
                    objectMapper.writeValueAsString(task)));
            return;
        }

        taskClient.delete(id, taskToken);
        logger.info("Task was deleted.");

        if (isUnavailableServiceTest()) {
            logger.error("Test service unavailable.");
            Integer idOp = operationTestRepository.findMaxId();
            idOp = (idOp == null) ? 1 : ++idOp;
            Test test = new Test();
            test.setIdTask(id);
            operationTestRepository.save(new OperationTest(idOp, OperationTest.TypeOperation.DELETE,
                    objectMapper.writeValueAsString(test)));
            return;
        }
        testClient.deleteByTaskId(id, testToken);
        logger.info("Test was deleted.");
        logger.info("Task and Test successfully deleted.");
    }

    private boolean isUnavailableServiceTask() {
        try {
            PageCustom<Task> checkTask = taskClient.findAll(0, 1, taskToken);
            if (checkTask == null) {
                logger.error("Task service unavailable.");
                return true;
            }
            return false;
        } catch (ClientAuthenticationExceptionWrapper exc) {
            taskToken = taskClient.getToken(String.format("Basic base64(%s:%s)", WebConfig.getAppKey(), WebConfig.getAppSecret()))
                    .get("access_token");
            if (taskToken != null) {
                taskToken = "Bearer " + taskToken;
                return isUnavailableServiceTask();
            }
            else
                return true;
        }
    }

    private boolean isUnavailableServiceTest() {
        try {
            PageCustom<Test> checkTest = testClient.findAll(0, 1, testToken);
            if (checkTest == null) {
                logger.error("Test service unavailable.");
                return true;
            }
            return false;
        } catch (ClientAuthenticationExceptionWrapper exc) {
            testToken = testClient.getToken(String.format("Basic base64(%s:%s)", WebConfig.getAppKey(), WebConfig.getAppSecret()))
                    .get("access_token");
            if (testToken != null) {
                testToken = "Bearer " + testToken;
                return isUnavailableServiceTest();
            }
            else
                return true;
        }
    }

    private class ThreadTask extends Thread {
        public void run() {
            while (!isInterrupted()) {
                OperationTask op = operationTaskRepository.findOpByMinId();
                if (op != null) {
                    try {
                        Thread.sleep(5000);
                        logger.info("Queue poll: type - " + op.getTypeOperation());
                        logger.info("Queue poll: task - " + op.getJson());
                        Task task = objectMapper.readValue(op.getJson(), Task.class);
                        if (op.getTypeOperation() == OperationTask.TypeOperation.CREATE)
                            this.create(op);
                        else if (op.getTypeOperation() == OperationTask.TypeOperation.UPDATE)
                            this.update(op);
                        else if (op.getTypeOperation() == OperationTask.TypeOperation.DELETE)
                            this.delete(op);
                    } catch (Exception exc) {
                        logger.error(exc.getMessage());
                    }
                }
            }
        }

        private void create(OperationTask op) throws IOException {
            Task task = objectMapper.readValue(op.getJson(), Task.class);
            Test test = task.getTest();
            task.setTest(null);

            Task resultTask = taskClient.create(task, taskToken)
                    .orElseThrow(() -> new HttpCanNotCreateException("Task could not be created"));

            resultTask.setTest(test);
            if (!isUnavailableServiceTask()) {
                logger.info("Task was created.");
                operationTaskRepository.delete(op);
            } else {
                return;
            }

            if (test != null) {
                test.setIdTask(resultTask.getId());
                test.setIdUser(resultTask.getIdUser());
                task.setTest(test);
                Test resultTest = createTest(test);
                if (isUnavailableServiceTest()) {
                    resultTest.setId(null);
                    Integer id = operationTestRepository.findMaxId();
                    id = (id == null) ? 1 : ++id;
                    operationTestRepository.save(new OperationTest(id, OperationTest.TypeOperation.CREATE,
                            objectMapper.writeValueAsString(test)));
                    resultTask.setTest(test);
                    return;
                }
                resultTask.setTest(resultTest);
                logger.info("Test was created.");
            }
            logger.info("Task and Test successful created.");
            logger.info("\t" + resultTask);
        }

        private void update(OperationTask op) throws IOException {
            Task task = objectMapper.readValue(op.getJson(), Task.class);
            Task checkTask = taskClient.findByUserIdAndTaskId(task.getIdUser(), task.getId(), taskToken)
                    .orElseThrow(() -> new HttpNotFoundException("Task could not be found with idUser: "
                            + task.getIdUser() + " and idTask: " + task.getId()));

            Test test = task.getTest();
            if (test != null) {
                test.setIdTask(task.getId());
                test.setIdUser(task.getIdUser());
                task.setTest(test);
            }

            if (!isUnavailableServiceTask()) {
                task.setTest(null);
                taskClient.update(task.getId(), task, taskToken);
                logger.info("Task was updated.");
                operationTaskRepository.delete(op);
            } else {
                return;
            }

            if (test != null && (task.getWithoutTest() == null || task.getWithoutTest() != 1)) {
                Test afterTest = null;
                task.setTest(test);
                try {
                    afterTest = testClient.findByUserIdAndTaskId(test.getIdUser(), test.getIdTask(), testToken)
                            .orElseThrow(() -> new HttpNotFoundException("Test could not be found with idUser: " +
                                    test.getIdUser() + " and idTask: " + test.getIdTask()));
                    test.setId(afterTest.getId());

                    testClient.update(test.getId(), test, testToken);
                } catch (ClientNotFoundExceptionWrapper | HttpNotFoundException exc) {
                    logger.error("Test was not updated.");

                    afterTest = testClient.create(test, testToken)
                            .orElseThrow(() -> new HttpCanNotCreateException("Task could not be created"));

                }
                if (isUnavailableServiceTest()) {
                    logger.error("Test service unavailable.");
                    test.setId(null);
                    Integer id = operationTestRepository.findMaxId();
                    id = (id == null) ? 1 : ++id;
                    operationTestRepository.save(new OperationTest(id, OperationTest.TypeOperation.UPDATE,
                            objectMapper.writeValueAsString(test)));
                    return;
                }
                logger.info("Test was updated.");
            }
            logger.info("Task and Test was updated.");
            logger.info("\t" + task);
        }

        private void delete(OperationTask op) throws IOException {
            Task task = objectMapper.readValue(op.getJson(), Task.class);
            if (!isUnavailableServiceTask()) {
                taskClient.delete(task.getId(), taskToken);
                logger.info("Task was deleted.");
                operationTaskRepository.delete(op);
            } else {
                return;
            }

            if (isUnavailableServiceTest()) {
                logger.error("Test service unavailable.");
                Integer idOp = operationTestRepository.findMaxId();
                idOp = (idOp == null) ? 1 : ++idOp;
                Test test = new Test();
                test.setIdTask(task.getId());
                operationTestRepository.save(new OperationTest(idOp, OperationTest.TypeOperation.DELETE,
                        objectMapper.writeValueAsString(test)));
                return;
            }
            testClient.deleteByTaskId(task.getId(), testToken);
            logger.info("Test was deleted.");
            logger.info("Task and Test successfully deleted.");
        }
    }

    private class ThreadTest extends Thread {
        public void run() {
            while (!isInterrupted()) {
                OperationTest op = operationTestRepository.findOpByMinId();
                if (op != null) {
                    try {
                        Thread.sleep(5000);
                        logger.info("Queue poll: type - " + op.getTypeOperation());
                        logger.info("Queue poll: task - " + op.getJson());
                        if (op.getTypeOperation() == OperationTest.TypeOperation.CREATE)
                            this.create(op);
                        else if (op.getTypeOperation() == OperationTest.TypeOperation.UPDATE)
                            this.update(op);
                        else if (op.getTypeOperation() == OperationTest.TypeOperation.DELETE)
                            this.delete(op);
                    } catch (Exception exc) {
                        logger.error(exc.getMessage());
                    }
                }
            }
        }

        private void create(OperationTest op) throws IOException {
            Test test = objectMapper.readValue(op.getJson(), Test.class);
            try {
                test = testClient.create(test, testToken).orElseThrow(() -> new HttpCanNotCreateException("Test could not be created"));
                if (!isUnavailableServiceTest())
                    operationTestRepository.delete(op);
            } catch (HttpCanNotCreateException exc) {
                logger.error("Test was not created.");
                logger.info("Task was deleted.");
                taskClient.delete(test.getIdTask(), taskToken);
                throw exc;
            }
        }

        private void update(OperationTest op) throws IOException {
            Test test = objectMapper.readValue(op.getJson(), Test.class);
            Test afterTest = null;
            try {
                afterTest = testClient.findByUserIdAndTaskId(test.getIdUser(), test.getIdTask(), testToken)
                        .orElseThrow(() -> new HttpNotFoundException("Test could not be found with idUser: " +
                                test.getIdUser() + " and idTask: " + test.getIdTask()));
                test.setId(afterTest.getId());
                testClient.update(test.getId(), test, testToken);
            } catch (ClientNotFoundExceptionWrapper | HttpNotFoundException exc) {
                logger.error("Test was not updated.");
                afterTest = testClient.create(test, testToken)
                        .orElseThrow(() -> new HttpCanNotCreateException("Task could not be created"));
            } finally {
                if (!isUnavailableServiceTest()) {
                    logger.info("Test was updated.");
                    operationTestRepository.delete(op);
                }
            }
        }

        private void delete(OperationTest op) throws IOException {
            Test test = objectMapper.readValue(op.getJson(), Test.class);
            if (!isUnavailableServiceTest()) {
                testClient.deleteByTaskId(test.getIdTask(), testToken);
                operationTestRepository.delete(op);
            }
        }
    }
}
