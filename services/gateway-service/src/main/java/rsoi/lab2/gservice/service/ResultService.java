package rsoi.lab2.gservice.service;

import rsoi.lab2.gservice.entity.Result;
import rsoi.lab2.gservice.model.PageCustom;

import java.util.HashMap;
import java.util.List;

public interface ResultService {
    Result findByUserIdAndTaskId(Long idUser, Long idTask);
    PageCustom<Result> findAll(Integer page, Integer size);
    PageCustom<Result> findByTaskId(Long id, Integer page, Integer size);
    PageCustom<Result> findByUserId(Long id, Integer page, Integer size);
    Result create(Result result);
    void update(Result result);
    void delete(Long idUser, Long idTask);
}
