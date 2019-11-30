package rsoi.lab2.taskservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rsoi.lab2.taskservice.entity.Task;
import rsoi.lab2.taskservice.exception.HttpNotFoundException;
import rsoi.lab2.taskservice.model.SomeTasksModel;
import rsoi.lab2.taskservice.repository.TaskRepository;
import rsoi.lab2.taskservice.service.TaskService;

import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task findById(UUID id) {
        logger.info("findById() method called:");
        Task result = taskRepository.findById(id)
                .orElseThrow(() -> new HttpNotFoundException("Task could not be found with id: " + id));
        logger.info("\t" + result);
        return result;
    }

    @Override
    public Page<SomeTasksModel> findByUserId(UUID id, Pageable pageable) {
        logger.info("findByUserId() method called:");
        Page<SomeTasksModel> result = taskRepository.findByIdUser(id, pageable);
        logger.info("\t" + result.getContent());
        return result;
    }

    @Override
    public Task findByUserIdAndTaskId(UUID idUser, UUID idTask) {
        logger.info("findByUserIdAndTaskId() method called:");
        Task result = taskRepository.findByIdUserAndIdTask(idUser, idTask)
                .orElseThrow(() -> new HttpNotFoundException(
                                String.format("Task could not be found with idUser: %s and idTask: %s", idUser, idTask)
                ));
        logger.info("\t" + result);
        return result;
    }

    @Override
    public Page<SomeTasksModel> findAll(Pageable pageable) {
        logger.info("findAll() method called:");
        Page<SomeTasksModel> result = taskRepository.findAllTasks(pageable);
        logger.info("\t" + result.getContent());
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
    public void delete(UUID id) {
        logger.info("delete() method called.");
        taskRepository.deleteById(id);
    }
}
