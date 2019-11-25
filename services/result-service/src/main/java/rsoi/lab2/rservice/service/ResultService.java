package rsoi.lab2.rservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rsoi.lab2.rservice.entity.Result;

import java.util.List;

public interface ResultService {
    Result findByUserIdAndTaskId(Long idUser, Long idTask);
    Page<Result> findAll(Pageable pageable);
    Page<Result> findByTaskId(Long id, Pageable pageable);
    Page<Result> findByUserId(Long id, Pageable pageable);
    Result create(Result result);
    void update(Result result);
    void delete(Long idUser, Long idTask);
}
