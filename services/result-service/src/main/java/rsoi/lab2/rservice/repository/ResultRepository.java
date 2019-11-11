package rsoi.lab2.rservice.repository;

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

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {

    Optional<Result> findByIdUserAndIdTask(Long idUser, Long idTask);
    List<Result> findByIdTask(Long idTask);
    List<Result> findByIdTask(Long idTask, Pageable pageable);
    List<Result> findByIdUser(Long idUser);
    List<Result> findByIdUser(Long idUser, Pageable pageable);

    @Transactional
    @Modifying
    @Query("delete from Result a where a.idUser = :idUser and a.idTask = :idTask")
    void deleteByUserIdAndTaskId(@Param("idUser") Long idUser, @Param("idTask") Long idTask);
}
