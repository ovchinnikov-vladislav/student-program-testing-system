package rsoi.lab2.gservice.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rsoi.lab2.gservice.client.TaskClient;
import rsoi.lab2.gservice.client.TaskClient;
import rsoi.lab2.gservice.client.ResultClient;
import rsoi.lab2.gservice.client.TestClient;
import rsoi.lab2.gservice.entity.Task;
import rsoi.lab2.gservice.entity.Task;
import rsoi.lab2.gservice.entity.Result;
import rsoi.lab2.gservice.entity.Test;
import rsoi.lab2.gservice.exception.HttpCanNotCreateException;
import rsoi.lab2.gservice.exception.HttpNotFoundException;
import rsoi.lab2.gservice.exception.ServiceAccessException;
import rsoi.lab2.gservice.exception.TestNotFoundException;
import rsoi.lab2.gservice.model.ErrorResponse;
import rsoi.lab2.gservice.model.PageCustom;
import rsoi.lab2.gservice.service.TestService;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Service
public class TestServiceImpl implements TestService {

    private static final Logger logger = LoggerFactory.getLogger(TestService.class);

    @Autowired
    private TestClient testClient;

    @Override
    public Test findById(UUID id) {
        logger.info("findById() method called:");
        Test result = testClient.findById(id)
                .orElseThrow(() -> new HttpNotFoundException("Test could not be found with id: " + id));

        UUID zeroUUID = new UUID(0, 0);
        if (result.getIdTest().equals(zeroUUID))
            throw new ServiceAccessException("Test service unavailable.");

        logger.info("\t" + result);
        return result;
    }

    @Override
    public Test findByUserIdAndTestId(UUID idUser, UUID idTest) {
        logger.info("findByUserIdAndTestId() method called:");
        Test result = testClient.findByUserIdAndTestId(idUser, idTest)
                .orElseThrow(() -> new HttpNotFoundException("Test could not be found with idUser = " + idUser + " and idTest: " + idTest));

        UUID zeroUUID = new UUID(0, 0);
        if (result.getIdTest().equals(zeroUUID))
            throw new ServiceAccessException("Test service unavailable.");

        logger.info("\t" + result);
        return result;
    }

    @Override
    public PageCustom<Test> findAll(Integer page, Integer size) {
        logger.info("findAll() method called:");
        PageCustom<Test> results = testClient.findAll(page, size);
        if (results == null)
            throw new ServiceAccessException("Test service unavailable.");

        logger.info("\t" + results.getContent());
        return results;
    }

    @Override
    public PageCustom<Test> findByUserId(UUID idUser, Integer page, Integer size) {
        logger.info("findByUserId() method called:");
        PageCustom<Test> results = testClient.findByUserId(idUser, page, size);
        if (results == null)
            throw new ServiceAccessException("Test service unavailable.");

        logger.info("\t" + results.getContent());
        return results;
    }

    @Override
    public Test findByTaskId(UUID idTask) {
        logger.info("findByTaskId() method called:");
        Test result = testClient.findByTaskId(idTask)
                .orElseThrow(() -> new HttpNotFoundException("Test could not be found with idTask = " + idTask));

        UUID zeroUUID = new UUID(0, 0);
        if (result.getIdTest().equals(zeroUUID))
            throw new ServiceAccessException("Test service unavailable.");

        logger.info("\t" + result);
        return result;
    }

    @Override
    public Test findByUserIdAndTaskId(UUID idUser, UUID idTask) {
        logger.info("findByUserIdAndTaskId() method called:");
        Test result = testClient.findByUserIdAndTaskId(idUser, idTask)
                .orElseThrow(() -> new HttpNotFoundException("Test could not be found with idUser: " + idUser + " and idTask: " + idTask));

        UUID zeroUUID = new UUID(0, 0);
        if (result.getIdTest().equals(zeroUUID))
            throw new ServiceAccessException("Test service unavailable.");

        logger.info("\t" + result);
        return result;
    }

    @Override
    public Test create(UUID idUser, UUID idTask, Test test) {
        logger.info("create() method called:");
        test.setIdUser(idUser);
        test.setIdTask(idTask);

        Test result = testClient.create(test)
                .orElseThrow(() -> new HttpCanNotCreateException("Test could not be created"));

        UUID zeroUUID = new UUID(0, 0);
        if (result.getIdTest().equals(zeroUUID))
            throw new ServiceAccessException("Test service unavailable.");

        logger.info("\t" + result);
        return result;
    }

    @Override
    public void update(UUID idUser, UUID idTask, UUID idTest, Test test) {
        logger.info("update() method called:");
        Test checkTest = testClient.findById(idTest)
                .orElseThrow(() -> new HttpNotFoundException("Test could not be found with id: " + idTest));

        UUID zeroUUID = new UUID(0, 0);
        if (checkTest.getIdTest().equals(zeroUUID))
            throw new ServiceAccessException("Test service unavailable.");

        test.setIdUser(idUser);
        test.setIdTask(idTask);
        test.setIdTest(idTest);

        testClient.update(idTest, test);
        logger.info("\t" + test);
    }

    @Override
    public void delete(UUID id) {
        logger.info("delete() method called: " + id);
        Test checkTest = testClient.findById(id)
                .orElseThrow(() -> new HttpNotFoundException("Test could not be found with id: " + id));

        UUID zeroUUID = new UUID(0, 0);
        if (checkTest.getIdTest().equals(zeroUUID))
            throw new ServiceAccessException("Test service unavailable.");

        testClient.delete(id);
    }

    @Override
    public void deleteByTaskId(UUID id) {
        logger.info("deleteByTaskId() method called: " + id);
        Test checkTest = testClient.findByTaskId(id)
                .orElseThrow(() -> new HttpNotFoundException("Test could not be found with idTask: " + id));

        UUID zeroUUID = new UUID(0, 0);
        if (checkTest.getIdTest().equals(zeroUUID))
            throw new ServiceAccessException("Test service unavailable.");

        testClient.deleteByTaskId(id);
    }
}
