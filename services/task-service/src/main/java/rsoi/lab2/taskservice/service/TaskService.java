package rsoi.lab2.taskservice.service;

import org.springframework.data.domain.Pageable;
import rsoi.lab2.taskservice.entity.Task;
import rsoi.lab2.taskservice.model.SomeTasksModel;

import java.util.List;

public interface TaskService  {
    Task getById(Long id);
    Task getByUserIdAndTaskId(Long idUser, Long idTask);
    List<SomeTasksModel> getAll();
    List<SomeTasksModel> getAll(Pageable pageable);
    List<SomeTasksModel> getByUserId(Long id);
    List<SomeTasksModel> getByUserId(Long id, Pageable pageable);
    Task create(Task task);
    void update(Task task);
    void delete(Long id);
}
