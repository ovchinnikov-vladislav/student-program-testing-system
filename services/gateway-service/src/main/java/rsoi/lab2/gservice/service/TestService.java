package rsoi.lab2.gservice.service;

import rsoi.lab2.gservice.entity.Test;
import rsoi.lab2.gservice.model.PageCustom;

public interface TestService {
    Test findById(Long id);
    Test findByUserIdAndTestId(Long idUser, Long idTest);
    PageCustom<Test> findAll(Integer page, Integer size);
    PageCustom<Test> findByUserId(Long idUser, Integer page, Integer size);
    Test findByTaskId(Long idTask);
    Test findByUserIdAndTaskId(Long idUser, Long idTask);
    Test create(Test test);
    void update(Test test);
    void delete(Long id);
    void deleteByTaskId(Long id);
}
