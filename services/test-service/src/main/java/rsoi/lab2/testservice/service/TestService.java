package rsoi.lab2.testservice.service;

import org.springframework.data.domain.Pageable;
import rsoi.lab2.testservice.entity.Test;
import rsoi.lab2.testservice.model.SomeTestsModel;

import java.util.List;

public interface TestService {
    Test getById(Long id);
    Test getByUserIdAndTestId(Long idUser, Long idTest);
    List<SomeTestsModel> getAll();
    List<SomeTestsModel> getAll(Pageable pageable);
    List<SomeTestsModel> getByUserId(Long id);
    List<SomeTestsModel> getByUserId(Long id, Pageable pageable);
    Test getByTaskId(Long id);
    Test getByUserIdAndTaskId(Long idUser, Long idTask);
    Test create(Test test);
    void update(Test test);
    void delete(Long id);
    void deleteByTaskId(Long id);
}
