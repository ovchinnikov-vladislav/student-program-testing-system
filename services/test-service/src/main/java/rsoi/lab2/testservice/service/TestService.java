package rsoi.lab2.testservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rsoi.lab2.testservice.entity.Test;
import rsoi.lab2.testservice.model.SomeTestsModel;

import java.util.List;

public interface TestService {
    Test findById(Long id);
    Test findByUserIdAndTestId(Long idUser, Long idTest);
    Page<SomeTestsModel> findAll(Pageable pageable);
    Page<SomeTestsModel> findByUserId(Long id, Pageable pageable);
    Test findByTaskId(Long id);
    Test findByUserIdAndTaskId(Long idUser, Long idTask);
    Test create(Test test);
    void update(Test test);
    void delete(Long id);
    void deleteByTaskId(Long id);
}
