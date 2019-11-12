package rsoi.lab2.testservice.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rsoi.lab2.testservice.entity.Test;
import rsoi.lab2.testservice.model.SomeTestsModel;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface TestRepository extends JpaRepository<Test, Long> {

    Optional<Test> findByIdUserAndIdTest(Long idUser, Long idTest);
    List<SomeTestsModel> findByIdUser(Long idUser);
    List<SomeTestsModel> findByIdUser(Long idUser, Pageable pageable);
    Optional<Test> findByIdTask(Long idTask);
    Optional<Test> findByIdUserAndIdTask(Long idUser, Long idTask);
    @Query("select t from Test t")
    List<SomeTestsModel> findAllTests();
    @Query("select t from Test t")
    List<SomeTestsModel> findAllTests(Pageable pageable);

    @Transactional
    @Modifying
    @Query("delete from Test t where t.idTask = :idTask")
    void deleteByTaskId(@Param("idTask") Long idTask);
}
