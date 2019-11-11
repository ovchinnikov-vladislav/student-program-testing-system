package rsoi.lab2.taskservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rsoi.lab2.taskservice.entity.Task;
import rsoi.lab2.taskservice.exception.HttpNotFoundException;
import rsoi.lab2.taskservice.model.SomeTasksModel;
import rsoi.lab2.taskservice.repository.TaskRepository;
import rsoi.lab2.taskservice.service.TaskService;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task getById(Long id) {
        logger.info("deleteByTaskId() method called:");
        Task result = taskRepository.findById(id).orElseThrow(() -> new HttpNotFoundException("Not found Task by id = " + id));
        logger.info("\t" + result);
        return result;
    }

    @Override
    public List<SomeTasksModel> getByUserId(Long id) {
        logger.info("getByUserId() method called:");
        List<SomeTasksModel> result = taskRepository.findByIdUser(id);
        logger.info("\t" + result);
        return result;
    }

    @Override
    public List<SomeTasksModel> getByUserId(Long id, Pageable pageable) {
        logger.info("getByUserId() method called:");
        List<SomeTasksModel> result = taskRepository.findByIdUser(id, pageable);
        logger.info("\t" + result);
        return result;
    }

    @Override
    public Task getByUserIdAndTaskId(Long idUser, Long idTask) {
        logger.info("getByUserIdAndTaskId() method called:");
        Task result = taskRepository.findByIdUserAndIdTask(idUser, idTask).orElseThrow(() -> new HttpNotFoundException(String.format("Not found Task by idTask = %d and idUser = %d", idTask, idUser)));
        logger.info("\t" + result);
        return result;
    }

    @Override
    public List<SomeTasksModel> getAll() {
        logger.info("getAll() method called:");
        List<SomeTasksModel> result = taskRepository.findAllTasks();
        logger.info("\t" + result);
        return result;
    }

    @Override
    public List<SomeTasksModel> getAll(Pageable pageable) {
        logger.info("getAll() method called:");
        List<SomeTasksModel> result = taskRepository.findAllTasks(pageable);
        logger.info("\t" + result);
        return result;
    }

    @Override
    public Task create(Task task) {
        logger.info("create() method called:");
        Task result = taskRepository.saveAndFlush(task);
        logger.info("\t" + result);
        return result;
    }


    @Override
    public void update(Task task) {
        logger.info("update() method called:");
        Task result = taskRepository.saveAndFlush(task);
        logger.info("\t" + result);
    }

    @Override
    public void delete(Long id) {
        logger.info("delete() method called.");
        taskRepository.deleteById(id);
    }
}
