package rsoi.lab2.gservice.service;

import rsoi.lab2.gservice.entity.test.Test;
import rsoi.lab2.gservice.model.PageCustom;

import java.util.UUID;

public interface TestService {
    Test findById(UUID id);
    Test findByUserIdAndTestId(UUID idUser, UUID idTest);
    PageCustom<Test> findAll(Integer page, Integer size);
    PageCustom<Test> findByUserId(UUID idUser, Integer page, Integer size);
    Test findByTaskId(UUID idTask);
    Test findByUserIdAndTaskId(UUID idUser, UUID idTask);
    Test create(UUID idUser, UUID idTask, Test test);
    void update(UUID idUser, UUID idTask, UUID idTest, Test test);
    void delete(UUID id);
    void deleteByTaskId(UUID id);
}
