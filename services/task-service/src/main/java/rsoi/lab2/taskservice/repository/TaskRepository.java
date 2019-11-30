package rsoi.lab2.taskservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rsoi.lab2.taskservice.model.SomeTasksModel;
import rsoi.lab2.taskservice.entity.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    Optional<Task> findByIdUserAndIdTask(UUID idUser, UUID idTask);
    Page<SomeTasksModel> findByIdUser(UUID idUser, Pageable pageable);
    @Query("select a from Task a")
    Page<SomeTasksModel> findAllTasks(Pageable pageable);
}
