package rsoi.lab2.gservice.service;

import rsoi.lab2.gservice.entity.Test;

public interface TestService {
    Test findById(Long id);
    Test findByUserIdAndTestId(Long idUser, Long idTest);
    Test[] findAll(Integer page, Integer size);
    Test[] findByUserId(Long idUser, Integer page, Integer size);
    Test[] findByTaskId(Long idTask, Integer page, Integer size);
    Test[] findByUserIdAndTaskId(Long idUser, Long idTask, Integer page, Integer size);
    Test create(Test test);
    void update(Long id, Test test);
    void delete(Long id);
    void deleteByTaskId(Long id);
}
