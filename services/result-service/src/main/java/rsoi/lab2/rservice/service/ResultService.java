package rsoi.lab2.rservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rsoi.lab2.rservice.entity.Result;

import java.util.List;
import java.util.UUID;

public interface ResultService {
    Result findByUserIdAndTaskId(UUID idUser, UUID idTask);
    Page<Result> findAll(Pageable pageable);
    Page<Result> findByTaskId(UUID id, Pageable pageable);
    Page<Result> findByUserId(UUID id, Pageable pageable);
    Result create(Result result);
    void update(UUID idUser, UUID idTask, Result result);
    void delete(UUID idUser, UUID idTask);
}
