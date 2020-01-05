package rsoi.lab2.teservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rsoi.lab2.teservice.entity.CompletedTask;
import rsoi.lab2.teservice.model.SomeCompletedTaskModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompletedTaskRepository extends JpaRepository<CompletedTask, UUID> {
    Optional<CompletedTask> findByIdUserAndId(UUID idUser, UUID idCompletedTask);
    Optional<CompletedTask> findByIdTaskAndId(UUID idTask, UUID idCompletedTask);
    Optional<CompletedTask> findByIdTestAndId(UUID idTest, UUID idCompletedTask);
    Page<SomeCompletedTaskModel> findByIdTask(UUID idTask, Pageable pageable);
    Page<SomeCompletedTaskModel> findByIdUser(UUID idUser, Pageable pageable);
    Page<SomeCompletedTaskModel> findByIdTest(UUID idTest, Pageable pageable);
    Page<SomeCompletedTaskModel> findByIdUserAndIdTask(UUID idUser, UUID idTask, Pageable pageable);
    @Query("select ct from CompletedTask ct")
    Page<SomeCompletedTaskModel> findAllCompletedTasks(Pageable pageable);
}
