package rsoi.lab2.rservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rsoi.lab2.rservice.entity.Result;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ResultRepository extends JpaRepository<Result, UUID> {
    Optional<Result> findByIdUserAndIdTask(UUID idUser, UUID idTask);
    Page<Result> findByIdTask(UUID idTask, Pageable pageable);
    Page<Result> findByIdUser(UUID idUser, Pageable pageable);
    @Transactional
    @Modifying
    void deleteByIdUserAndIdTask(UUID idUser, UUID idTask);
}
