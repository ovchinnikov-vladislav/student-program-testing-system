package rsoi.lab2.taskservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rsoi.lab2.taskservice.entity.Task;
import rsoi.lab2.taskservice.model.SomeTasksModel;

import java.util.List;
import java.util.UUID;

public interface TaskService  {
    Task findById(UUID id);
    Task findByUserIdAndTaskId(UUID idUser, UUID idTask);
    Page<SomeTasksModel> findAll(Pageable pageable);
    Page<SomeTasksModel> findByUserId(UUID id, Pageable pageable);
    Task create(Task task);
    void update(Task task);
    void delete(UUID id);
}
