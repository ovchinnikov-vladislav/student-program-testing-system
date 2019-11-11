package rsoi.lab2.rservice.service;

import org.springframework.data.domain.Pageable;
import rsoi.lab2.rservice.entity.Result;

import java.util.List;

public interface ResultService {
    Result getByUserIdAndTaskId(Long idUser, Long idTask);
    List<Result> getAll();
    List<Result> getAll(Pageable pageable);
    List<Result> getByTaskId(Long id);
    List<Result> getByTaskId(Long id, Pageable pageable);
    List<Result> getByUserId(Long id);
    List<Result> getByUserId(Long id, Pageable pageable);
    Result add(Result result);
    void update(Result result);
    void delete(Long idUser, Long idTask);
}
