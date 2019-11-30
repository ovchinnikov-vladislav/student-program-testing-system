package rsoi.lab2.gservice.service;

import rsoi.lab2.gservice.entity.Result;
import rsoi.lab2.gservice.model.PageCustom;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface ResultService {
    Result findByUserIdAndTaskId(UUID idUser, UUID idTask);
    PageCustom<Result> findAll(Integer page, Integer size);
    PageCustom<Result> findByTaskId(UUID id, Integer page, Integer size);
    PageCustom<Result> findByUserId(UUID id, Integer page, Integer size);
    Result create(Result result);
    void update(Result result);
    void delete(UUID idUser, UUID idTask);
}
