package rsoi.lab2.teservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rsoi.lab2.teservice.entity.CompletedTask;
import rsoi.lab2.teservice.model.ExecuteTaskRequest;
import rsoi.lab2.teservice.model.ResultTest;
import rsoi.lab2.teservice.model.SomeCompletedTaskModel;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface TaskExecutorService {
    Page<SomeCompletedTaskModel> findAll(Pageable pageable);
    Page<SomeCompletedTaskModel> findByTaskId(Long id, Pageable pageable);
    Page<SomeCompletedTaskModel> findByUserId(Long id, Pageable pageable);
    Page<SomeCompletedTaskModel> findByTestId(Long id, Pageable pageable);
    Page<SomeCompletedTaskModel> findByIdUserAndIdTask(Long idUser, Long idTask, Pageable pageable);
    CompletedTask findById(Long id);
    CompletedTask findByUserIdAndCompletedTaskId(Long idUser, Long idCompletedTask);
    CompletedTask findByTaskIdAndCompletedTaskId(Long idTask, Long idCompletedTask);
    CompletedTask findByTestIdAndCompletedTaskId(Long idTest, Long idCompletedTask);
    CompletedTask create(CompletedTask completedTask);
    void update(CompletedTask completedTask);
    void delete(Long id);
    ResultTest execute(ExecuteTaskRequest executeTaskRequest) throws IOException, NoSuchAlgorithmException,
            URISyntaxException, ClassNotFoundException;
}
