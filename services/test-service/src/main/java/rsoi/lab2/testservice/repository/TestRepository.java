package rsoi.lab2.testservice.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rsoi.lab2.testservice.entity.Test;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface TestRepository extends JpaRepository<Test, Long> {

    Optional<Test> findByIdUserAndIdTest(Long idUser, Long idTest);
    List<Test> findByIdUser(Long idUser);
    List<Test> findByIdUser(Long idUser, Pageable pageable);
    List<Test> findByIdTask(Long idTask);
    List<Test> findByIdTask(Long idTask, Pageable pageable);
    List<Test> findByIdUserAndIdTask(Long idUser, Long idTask);
    List<Test> findByIdUserAndIdTask(Long idUser, Long idTask, Pageable pageable);

    @Transactional
    @Modifying
    @Query("delete from Test t where t.idTask = :idTask")
    void deleteByTaskId(@Param("idTask") Long idTask);
}
