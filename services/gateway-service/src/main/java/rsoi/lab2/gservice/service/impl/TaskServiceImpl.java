package rsoi.lab2.gservice.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rsoi.lab2.gservice.client.TaskClient;
import rsoi.lab2.gservice.client.TestClient;
import rsoi.lab2.gservice.entity.Task;
import rsoi.lab2.gservice.entity.Test;
import rsoi.lab2.gservice.exception.HttpCanNotCreateException;
import rsoi.lab2.gservice.exception.HttpNotFoundException;
import rsoi.lab2.gservice.service.TaskService;
import rsoi.lab2.gservice.service.TestService;

import java.util.Arrays;

@Service
public class TaskServiceImpl implements TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    private TaskClient taskClient;
    @Autowired
    private TestService testService;

    @Override
    public Task findById(Long id) {
        logger.info("findById() method called:");
        Task result = taskClient.findById(id)
                .orElseThrow(() -> new HttpNotFoundException("Not found Task by id = " + id));
        Test[] tests = testService.findByTaskId(id, null, null);
        result.setTests(tests);
        logger.info("\t" + result);
        return result;
    }

    @Override
    public Task findByUserIdAndTaskId(Long idUser, Long idTask) {
        logger.info("findByUserIdAndTaskId() method called:");
        Task result = taskClient.findByUserIdAndTaskId(idUser, idTask)
                .orElseThrow(() -> new HttpNotFoundException("Not found Task by idUser = " + idUser + " and idTask = " + idTask));
        Test[] tests = testService.findByUserIdAndTaskId(idUser, idTask, null, null);
        result.setTests(tests);
        logger.info("\t" + result);
        return result;
    }

    @Override
    public Task[] findAll(Integer page, Integer size) {
        logger.info("findAll() method called:");
        Task[] results = taskClient.findAll(page, size);
        logger.info("\t" + Arrays.toString(results));
        return results;
    }

    @Override
    public Task[] findByUserId(Long id, Integer page, Integer size) {
        logger.info("findByUserId() method called:");
        Task[] results = taskClient.findByUserId(id, page, size);
        logger.info("\t" + Arrays.toString(results));
        return results;
    }

    @Override
    public Task create(Task task) {
        logger.info("create() method called:");
        Task result = taskClient.create(task)
                .orElseThrow(() -> new HttpCanNotCreateException("Task cannot create"));
        Test[] tests = result.getTests();
        for (Test test : tests) {
            test.setIdTask(task.getIdTask());
            test.setIdUser(task.getIdUser());
            testService.create(test);
        }
        logger.info("\t" + result);
        return result;
    }

    @Override
    public void update(Long id, Task task) {
        logger.info("update() method called:");
        taskClient.update(id, task);
        Test[] tests = task.getTests();
        for (Test test : tests) {
            test.setIdTask(task.getIdTask());
            test.setIdUser(task.getIdUser());
            testService.update(id, test);
        }
        logger.info("\t" + task);
    }

    @Override
    public void delete(Long id) {
        logger.info("delete() method called: " + id);
        taskClient.delete(id);
        testService.deleteByTaskId(id);
    }
}
