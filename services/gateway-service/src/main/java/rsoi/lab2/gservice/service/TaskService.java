package rsoi.lab2.gservice.service;

import rsoi.lab2.gservice.entity.task.Task;
import rsoi.lab2.gservice.model.PageCustom;

import java.io.IOException;
import java.util.UUID;

public interface TaskService {
    Task findById(UUID id);
    Task findByUserIdAndTaskId(UUID idUser, UUID idTask);
    PageCustom<Task> findAll(Integer page, Integer size);
    PageCustom<Task> findByUserId(UUID id, Integer page, Integer size);
    Task create(UUID idUser, Task task) throws IOException;
    void update(UUID idUser, UUID idTask, Task task) throws IOException;
    void delete(UUID id) throws IOException;
}
