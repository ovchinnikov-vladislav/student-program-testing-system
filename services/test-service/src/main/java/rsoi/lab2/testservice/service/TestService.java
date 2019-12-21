package rsoi.lab2.testservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rsoi.lab2.testservice.entity.Test;
import rsoi.lab2.testservice.model.SomeTestsModel;

import java.util.List;
import java.util.UUID;

public interface TestService {
    Test findById(UUID id);
    Test findByUserIdAndTestId(UUID idUser, UUID idTest);
    Page<SomeTestsModel> findAll(Pageable pageable);
    Page<SomeTestsModel> findByUserId(UUID id, Pageable pageable);
    Test findByTaskId(UUID id);
    Test findByUserIdAndTaskId(UUID idUser, UUID idTask);
    Test create(Test test);
    void update(UUID id, Test test);
    void delete(UUID id);
    void deleteByTaskId(UUID id);
}
