package rsoi.lab2.gservice.service;

import rsoi.lab2.gservice.entity.Task;

public interface TaskService {
    Task findById(Long id);
    Task findByUserIdAndTaskId(Long idUser, Long idTask);
    Task[] findAll(Integer page, Integer size);
    Task[] findByUserId(Long id, Integer page, Integer size);
    Task create(Task task);
    void update(Long id, Task task);
    void delete(Long id);
}
