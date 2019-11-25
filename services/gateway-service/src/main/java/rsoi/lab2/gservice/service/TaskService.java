package rsoi.lab2.gservice.service;

import rsoi.lab2.gservice.entity.Task;
import rsoi.lab2.gservice.model.PageCustom;

public interface TaskService {
    Task findById(Long id);
    Task findByUserIdAndTaskId(Long idUser, Long idTask);
    PageCustom<Task> findAll(Integer page, Integer size);
    PageCustom<Task> findByUserId(Long id, Integer page, Integer size);
    Task create(Task task);
    void update(Task task);
    void delete(Long id);
}
