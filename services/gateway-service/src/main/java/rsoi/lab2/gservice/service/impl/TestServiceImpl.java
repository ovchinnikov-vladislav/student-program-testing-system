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
import rsoi.lab2.gservice.model.ErrorResponse;
import rsoi.lab2.gservice.model.PageCustom;
import rsoi.lab2.gservice.service.TestService;

import java.util.Arrays;

@Service
public class TestServiceImpl implements TestService {

    private static final Logger logger = LoggerFactory.getLogger(TestService.class);

    @Autowired
    private TestClient testClient;

    @Override
    public Test findById(Long id) {
        logger.info("findById() method called:");
        Test result = testClient.findById(id)
                .orElseThrow(() -> new HttpNotFoundException("Test could not be found with id: " + id));
        logger.info("\t" + result);
        return result;
    }

    @Override
    public Test findByUserIdAndTestId(Long idUser, Long idTest) {
        logger.info("findByUserIdAndTestId() method called:");
        Test result = testClient.findByUserIdAndTestId(idUser, idTest)
                .orElseThrow(() -> new HttpNotFoundException("Test could not be found with idUser = " + idUser + " and idTest: " + idTest));
        logger.info("\t" + result);
        return result;
    }

    @Override
    public PageCustom<Test> findAll(Integer page, Integer size) {
        logger.info("findAll() method called:");
        PageCustom<Test> results = testClient.findAll(page, size);
        logger.info("\t" + results.getContent());
        return results;
    }

    @Override
    public PageCustom<Test> findByUserId(Long idUser, Integer page, Integer size) {
        logger.info("findByUserId() method called:");
        PageCustom<Test> results = testClient.findByUserId(idUser, page, size);
        logger.info("\t" + results.getContent());
        return results;
    }

    @Override
    public Test findByTaskId(Long idTask) {
        logger.info("findByTaskId() method called:");
        Test result = testClient.findByTaskId(idTask)
                .orElseThrow(() -> new HttpNotFoundException("Test could not be found with idTask = " + idTask));
        logger.info("\t" + result);
        return result;
    }

    @Override
    @HystrixCommand(fallbackMethod = "findByUserIdAndTaskIdFallback")
    public Test findByUserIdAndTaskId(Long idUser, Long idTask) {
        logger.info("findByUserIdAndTaskId() method called:");
        Test result = testClient.findByUserIdAndTaskId(idUser, idTask)
                .orElseThrow(() -> new HttpNotFoundException("Test could not be found with idUser: " + idUser + " and idTask: " + idTask));
        logger.info("\t" + result);
        return result;
    }

    private Test findByUserIdAndTaskIdFallback(Long idUser, Long idTask) {
        logger.info("findByUserIdAndTaskIdFallback() method called:");
        return null;
    }

    @Override
    public Test create(Test test) {
        logger.info("create() method called:");
        Test result = testClient.create(test)
                .orElseThrow(() -> new HttpCanNotCreateException("Test could not be created"));
        logger.info("\t" + result);
        return result;
    }

    @Override
    public void update(Test test) {
        logger.info("update() method called:");
        testClient.update(test.getIdTest(), test);
        logger.info("\t" + test);
    }

    @Override
    public void delete(Long id) {
        logger.info("delete() method called: " + id);
        testClient.delete(id);
    }

    @Override
    public void deleteByTaskId(Long id) {
        logger.info("deleteByTaskId() method called: " + id);
        testClient.deleteByTaskId(id);
    }
}
