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

public interface CompletedTaskRepository extends JpaRepository<CompletedTask, Long> {
    Optional<CompletedTask> findByIdUserAndIdCompletedTask(Long idUser, Long idCompletedTask);
    Optional<CompletedTask> findByIdTaskAndIdCompletedTask(Long idTask, Long idCompletedTask);
    Optional<CompletedTask> findByIdTestAndIdCompletedTask(Long idTest, Long idCompletedTask);
    Page<SomeCompletedTaskModel> findByIdTask(Long idTask, Pageable pageable);
    Page<SomeCompletedTaskModel> findByIdUser(Long idUser, Pageable pageable);
    Page<SomeCompletedTaskModel> findByIdTest(Long idTest, Pageable pageable);
    Page<SomeCompletedTaskModel> findByIdUserAndIdTask(Long idUser, Long idTask, Pageable pageable);
    @Query("select ct from CompletedTask ct")
    Page<SomeCompletedTaskModel> findAllCompletedTasks(Pageable pageable);
}
