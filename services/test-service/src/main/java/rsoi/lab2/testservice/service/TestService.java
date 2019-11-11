package rsoi.lab2.testservice.service;

import org.springframework.data.domain.Pageable;
import rsoi.lab2.testservice.entity.Test;

import java.util.List;

public interface TestService {
    Test getById(Long id);
    Test getByUserIdAndTestId(Long idUser, Long idTest);
    List<Test> getAll();
    List<Test> getAll(Pageable pageable);
    List<Test> getByUserId(Long id);
    List<Test> getByUserId(Long id, Pageable pageable);
    List<Test> getByTaskId(Long id);
    List<Test> getByTaskId(Long id, Pageable pageable);
    List<Test> getByUserIdAndTaskId(Long idUser, Long idTask);
    List<Test> getByUserIdAndTaskId(Long idUser, Long idTask, Pageable pageable);
    Test create(Test test);
    void update(Test test);
    void delete(Long id);
    void deleteByTaskId(Long id);
}
