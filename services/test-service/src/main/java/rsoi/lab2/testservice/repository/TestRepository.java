package rsoi.lab2.testservice.repository;

import org.springframework.data.domain.Page;
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
import java.util.UUID;

public interface TestRepository extends JpaRepository<Test, UUID> {
    Optional<Test> findByIdUserAndId(UUID idUser, UUID idTest);
    Page<SomeTestsModel> findByIdUser(UUID idUser, Pageable pageable);
    Optional<Test> findByIdTask(UUID idTask);
    Optional<Test> findByIdUserAndIdTask(UUID idUser, UUID idTask);
    @Query("select t from Test t")
    Page<SomeTestsModel> findAllTests(Pageable pageable);
    @Transactional
    @Modifying
    void deleteByIdTask(UUID idTask);
}
