package rsoi.lab2.gservice.service;

import rsoi.lab2.gservice.entity.Task;
import rsoi.lab2.gservice.model.PageCustom;

import java.util.UUID;

public interface TaskService {
    Task findById(UUID id);
    Task findByUserIdAndTaskId(UUID idUser, UUID idTask);
    PageCustom<Task> findAll(Integer page, Integer size);
    PageCustom<Task> findByUserId(UUID id, Integer page, Integer size);
    Task create(Task task);
    void update(Task task);
    void delete(UUID id);
}
