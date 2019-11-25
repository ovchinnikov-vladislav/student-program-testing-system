package rsoi.lab2.taskservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rsoi.lab2.taskservice.entity.Task;
import rsoi.lab2.taskservice.model.SomeTasksModel;

import java.util.List;

public interface TaskService  {
    Task findById(Long id);
    Task findByUserIdAndTaskId(Long idUser, Long idTask);
    Page<SomeTasksModel> findAll(Pageable pageable);
    Page<SomeTasksModel> findByUserId(Long id, Pageable pageable);
    Task create(Task task);
    void update(Task task);
    void delete(Long id);
}
