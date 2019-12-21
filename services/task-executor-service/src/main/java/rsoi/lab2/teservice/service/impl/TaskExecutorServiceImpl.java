package rsoi.lab2.teservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rsoi.lab2.teservice.entity.CompletedTask;
import rsoi.lab2.teservice.exception.HttpNotFoundException;
import rsoi.lab2.teservice.exception.NotRunTestException;
import rsoi.lab2.teservice.model.ExecuteTaskRequest;
import rsoi.lab2.teservice.model.ResultTest;
import rsoi.lab2.teservice.model.ResultWrapper;
import rsoi.lab2.teservice.model.SomeCompletedTaskModel;
import rsoi.lab2.teservice.repository.CompletedTaskRepository;
import rsoi.lab2.teservice.service.TaskExecutorService;
import rsoi.lab2.teservice.util.TaskExecutor;

import java.io.IOException;
import java.util.UUID;

@Service
public class TaskExecutorServiceImpl implements TaskExecutorService {

    private static final Logger logger = LoggerFactory.getLogger(TaskExecutorService.class);

    @Autowired
    private CompletedTaskRepository completedTaskRepository;

    @Override
    public Page<SomeCompletedTaskModel> findAll(Pageable pageable) {
        logger.info("findAll() method called:");
        Page<SomeCompletedTaskModel> results = completedTaskRepository.findAllCompletedTasks(pageable);
        logger.info("\t" + results.getContent());
        return results;
    }

    @Override
    public Page<SomeCompletedTaskModel> findByTaskId(UUID id, Pageable pageable) {
        logger.info("findByTaskId() method called:");
        Page<SomeCompletedTaskModel> results = completedTaskRepository.findByIdTask(id, pageable);
        logger.info("\t" + results.getContent());
        return results;
    }

    @Override
    public Page<SomeCompletedTaskModel> findByUserId(UUID id, Pageable pageable) {
        logger.info("findByUserId() method called:");
        Page<SomeCompletedTaskModel> results = completedTaskRepository.findByIdUser(id, pageable);
        logger.info("\t" + results.getContent());
        return results;
    }

    @Override
    public Page<SomeCompletedTaskModel> findByTestId(UUID id, Pageable pageable) {
        logger.info("findByTestId() method called:");
        Page<SomeCompletedTaskModel> results = completedTaskRepository.findByIdTest(id, pageable);
        logger.info("\t" + results.getContent());
        return results;
    }

    @Override
    public Page<SomeCompletedTaskModel> findByIdUserAndIdTask(UUID idUser, UUID idTask, Pageable pageable) {
        logger.info("findByIdUserAndIdTask() method called:");
        Page<SomeCompletedTaskModel> results = completedTaskRepository.findByIdUserAndIdTask(idUser, idTask, pageable);
        logger.info("\t" + results.getContent());
        return results;
    }

    @Override
    public CompletedTask findById(UUID id) {
        logger.info("findById() method called:");
        CompletedTask result = completedTaskRepository
                .findById(id).orElseThrow(() -> new HttpNotFoundException("CompletedTask could not be found with id: " + id));
        logger.info("\t" + result);
        return result;
    }

    @Override
    public CompletedTask findByUserIdAndCompletedTaskId(UUID idUser, UUID idCompletedTask) {
        logger.info("findByUserIdAndCompletedTaskId() method called:");
        CompletedTask result = completedTaskRepository
                .findByIdUserAndIdCompletedTask(idUser, idCompletedTask)
                .orElseThrow(() -> new HttpNotFoundException("CompletedTask could not be found with idUser: " + idUser +
                        " and idCompletedTask: " + idCompletedTask));
        logger.info("\t" + result);
        return result;
    }

    @Override
    public CompletedTask findByTaskIdAndCompletedTaskId(UUID idTask, UUID idCompletedTask) {
        logger.info("findByTaskIdAndCompletedTaskId() method called:");
        CompletedTask result = completedTaskRepository
                .findByIdTaskAndIdCompletedTask(idTask, idCompletedTask)
                .orElseThrow(() -> new HttpNotFoundException("CompletedTask could not be found with idTask: " + idTask +
                        " and idCompletedTask: " + idCompletedTask));
        logger.info("\t" + result);
        return result;
    }

    @Override
    public CompletedTask findByTestIdAndCompletedTaskId(UUID idTest, UUID idCompletedTask) {
        logger.info("findByTestIdAndCompletedTaskId() method called:");
        CompletedTask result = completedTaskRepository
                .findByIdTestAndIdCompletedTask(idTest, idCompletedTask)
                .orElseThrow(() -> new HttpNotFoundException("CompletedTask could not be found with idTest: " + idTest +
                        " and idCompletedTask: " + idCompletedTask));
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
    public void update(UUID id, CompletedTask completedTask) {
        logger.info("update() method called:");
        completedTask.setIdCompletedTask(id);
        CompletedTask result = completedTaskRepository.saveAndFlush(completedTask);
        logger.info("\t" + result);
    }

    @Override
    public void delete(UUID id) {
        logger.info("delete() method called.");
        completedTaskRepository.deleteById(id);
    }

    @Override
    public ResultWrapper execute(ExecuteTaskRequest executeTaskRequest) throws IOException, ClassNotFoundException {
        logger.info("execute() method called:");
        logger.info("\t" + executeTaskRequest);
        ResultTest resultTest = TaskExecutor.execute(executeTaskRequest);
        logger.info("\t" + resultTest);

        if (resultTest == null)
            throw new NotRunTestException("ResultTest == null");

        CompletedTask task = new CompletedTask();
        task.setIdUser(executeTaskRequest.getIdUser());
        task.setIdTask(executeTaskRequest.getIdTask());
        task.setSourceCode(executeTaskRequest.getSourceTask());
        task.setIdTest(executeTaskRequest.getIdTest());
        task.setCountSuccessfulTests(resultTest.getCountSuccessfulTests());
        task.setCountFailedTests(resultTest.getCountFailedTests());
        task.setCountAllTests(resultTest.getCountAllTests());
        task.setWasSuccessful((byte) resultTest.getWasSuccessful());

        CompletedTask completedTask = this.create(task);

        if (completedTask == null)
            throw new NotRunTestException("CompletedTask == null");

        ResultWrapper resultWrapper = new ResultWrapper();
        resultWrapper.setIdCompletedTask(completedTask.getIdCompletedTask());
        resultWrapper.setResultTest(resultTest);

        return resultWrapper;
    }
}
