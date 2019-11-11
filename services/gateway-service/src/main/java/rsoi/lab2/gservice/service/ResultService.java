package rsoi.lab2.gservice.service;

import rsoi.lab2.gservice.entity.Result;

import java.util.HashMap;
import java.util.List;

public interface ResultService {
    Result findByUserIdAndTaskId(Long idUser, Long idTask);
    Result[] findAll(Integer page, Integer size);
    Result[] findByTaskId(Long id, Integer page, Integer size);
    Result[] findByUserId(Long id, Integer page, Integer size);
    Result create(Result result);
    void update(Long idUser, Long idTask, Result result);
    void delete(Long idUser, Long idTask);
}
